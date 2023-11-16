package com.rhuamani.sqlite

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.rhuamani.sqlite.entidades.Persona

class MainActivity : AppCompatActivity() {

    private lateinit var txtDNI: EditText
    private lateinit var txtNombres: EditText
    private lateinit var txtCorreo: EditText
    private lateinit var txtEdad: EditText
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        asignarReferencias()
    }

    private fun asignarReferencias(){
        txtDNI = findViewById(R.id.txtDNI)
        txtNombres = findViewById(R.id.TxtNombres)
        txtCorreo = findViewById(R.id.txtCorreo)
        txtEdad = findViewById(R.id.txtEdad)
        btnGuardar = findViewById(R.id.btnGuardar)

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
            objPersona.dni = dni
            objPersona.nombres = nombres
            objPersona.correo = correo
            objPersona.edad = edad.toInt()

            val personaDAO= PersonaDAO(this)
            val mensaje = personaDAO.registrarPersona(objPersona)
            mostrarMensaje(mensaje)
            limpiarCajas()
        }
    }

    private fun mostrarMensaje(mensaje: String){
        val ventana = AlertDialog.Builder(this)
        ventana.setTitle("Informacion")
        ventana.setMessage(mensaje)
        ventana.setPositiveButton("Aceptar", null)
        ventana.create().show()
    }

    private fun limpiarCajas() {
        txtDNI.setText("")
        txtNombres.setText("")
        txtCorreo.setText("")
        txtEdad.setText("")


    }
}