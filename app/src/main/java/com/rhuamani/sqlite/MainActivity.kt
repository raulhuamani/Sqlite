package com.rhuamani.sqlite

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.rhuamani.sqlite.entidades.Persona

class MainActivity : AppCompatActivity() {

    private lateinit var txtDNI: EditText
    private lateinit var txtNombres: EditText
    private lateinit var txtCorreo: EditText
    private lateinit var txtEdad: EditText
    private lateinit var txtTituloForm: TextView
    private lateinit var btnGuardar: Button

    private var id:Int = 0
    private var modificar: Boolean = false
    private lateinit var personaDAO: PersonaDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        asignarReferencias()
        verificarSiModifica()
    }

    private fun verificarSiModifica(){
        if (intent.hasExtra("p_id")){
            modificar = true
            id = intent.getIntExtra("p_id", 0)
//            Toast.makeText(this, "Valor: "+id, Toast.LENGTH_SHORT).show()

            txtTituloForm.setText("Editar Persona")
            btnGuardar.setText("Guardar Cambios")

            val objPersona = personaDAO.buscarPersona(id)
            txtDNI.setText(objPersona.dni)
            txtNombres.setText(objPersona.nombres)
            txtCorreo.setText(objPersona.correo)
            txtEdad.setText(objPersona.edad.toString())
        }
    }

    private fun asignarReferencias(){
        txtDNI = findViewById(R.id.txtDNI)
        txtNombres = findViewById(R.id.TxtNombres)
        txtCorreo = findViewById(R.id.txtCorreo)
        txtEdad = findViewById(R.id.txtEdad)
        btnGuardar = findViewById(R.id.btnGuardar)
        txtTituloForm = findViewById(R.id.txtTituloForm)

        personaDAO = PersonaDAO(this)

        btnGuardar.setOnClickListener({ registrarPersona() })
    }

    private fun registrarPersona() {
        //Toast.makeText(this, "Holaaa", Toast.LENGTH_SHORT).show()
        val dni = txtDNI.text.toString()
        val nombres = txtNombres.text.toString()
        val correo = txtCorreo.text.toString()
        val edad = txtEdad.text.toString()

        if (dni.isEmpty() || nombres.isEmpty() || correo.isEmpty() || edad.isEmpty() ) {
            Toast.makeText(this, "Completar todos los campos", Toast.LENGTH_SHORT).show()
        } else {
            val objPersona = Persona()
            if (modificar == true){
                objPersona.id = id
            }
            objPersona.dni = dni
            objPersona.nombres = nombres
            objPersona.correo = correo
            objPersona.edad = edad.toInt()

            val personaDAO= PersonaDAO(this)
            var mensaje = ""
            if (modificar == false){
                mensaje = personaDAO.registrarPersona(objPersona)
            }else{
                mensaje = personaDAO.modificarPersona(objPersona)
            }
            mostrarMensaje(mensaje)
            limpiarCajas()
        }
    }

    private fun mostrarMensaje(mensaje: String){
        val ventana = AlertDialog.Builder(this)
        ventana.setTitle("Informacion")
        ventana.setMessage(mensaje)
        ventana.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialogInterface, i ->
            val intent = Intent(this, ListarActivity::class.java)
            startActivity(intent)
        })
        ventana.create().show()
    }

    private fun limpiarCajas() {
        txtDNI.setText("")
        txtNombres.setText("")
        txtCorreo.setText("")
        txtEdad.setText("")


    }
}