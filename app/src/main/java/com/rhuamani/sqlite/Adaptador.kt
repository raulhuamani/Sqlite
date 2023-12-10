package com.rhuamani.sqlite

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.rhuamani.sqlite.entidades.Persona


class Adaptador: RecyclerView.Adapter<Adaptador.MiViewHolder>() {

    private var listaPersonas:ArrayList<Persona> =ArrayList()
    private lateinit var context: Context

    fun contexto(context: Context){
        this.context = context
    }

    fun agregarItems (items: ArrayList<Persona>){
        this.listaPersonas=items
    }

    class MiViewHolder (var view: View): RecyclerView.ViewHolder(view) {
        private var filaNombre= view.findViewById<TextView>(R.id.filaNombre)
        private var filaDNI= view.findViewById<TextView>(R.id.filaDNI)
        private var filaCorreo= view.findViewById<TextView>(R.id.filaCorreo)
        private var filaEdad= view.findViewById<TextView>(R.id.filaEdad)
        var filaEditar = view.findViewById<ImageButton>(R.id.filaEditar)
        var filaElimnar = view.findViewById<ImageButton>(R.id.filaElimnar)

        fun bindView(persona: Persona){
            filaNombre.text= persona.nombres
            filaDNI.text=persona.dni
            filaCorreo.text=persona.correo
            filaEdad.text=persona.edad.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= MiViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.fila,parent,false)
    )

    override fun onBindViewHolder(holder: MiViewHolder, position: Int) {
        val personaItem = listaPersonas[position]
        holder.bindView(personaItem)

        holder.filaEditar.setOnClickListener {
//            Toast.makeText(context, "Hiciste click en editar", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("p_id", listaPersonas[position].id)
            context.startActivity(intent)
        }
        
        holder.filaElimnar.setOnClickListener { 
            mostrarConfirmacion(listaPersonas[position].nombres, listaPersonas[position].id)
        }
    }
    
    private fun mostrarConfirmacion(nombre: String, id: Int){
        val ventana = AlertDialog.Builder(context)
        ventana.setTitle("Confirmación")
        ventana.setMessage("Desea eliminar a " + nombre)
        ventana.setNegativeButton("NO", null)
        ventana.setPositiveButton("SI", DialogInterface.OnClickListener { dialogInterface, i ->
            var personaDAO = PersonaDAO(context)
            var mensaje = personaDAO.eliminarPersona(id)
            mostrarMensaje(mensaje)
        })
        ventana.create().show()
    }

    private fun mostrarMensaje(mensaje: String){
        val ventana = AlertDialog.Builder(context)
        ventana.setTitle("Información")
        ventana.setMessage(mensaje)
        ventana.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialogInterface, i ->
            val intent = Intent(context, ListarActivity::class.java)
            context.startActivity(intent)
        })
        ventana.create().show()
    }

    override fun getItemCount(): Int {
        return listaPersonas.size
    }

}