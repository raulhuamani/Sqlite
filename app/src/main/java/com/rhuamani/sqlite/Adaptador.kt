package com.rhuamani.sqlite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rhuamani.sqlite.entidades.Persona


class Adaptador: RecyclerView.Adapter<Adaptador.MiViewHolder>() {

    private var listaPersonas:ArrayList<Persona> =ArrayList()
    fun agregarItems (items: ArrayList<Persona>){
        this.listaPersonas=items
    }

    class MiViewHolder (var view: View): RecyclerView.ViewHolder(view) {
        private var filaNombre= view.findViewById<TextView>(R.id.filaNombre)
        private var filaDNI= view.findViewById<TextView>(R.id.filaDNI)
        private var filaCorreo= view.findViewById<TextView>(R.id.filaCorreo)
        private var filaEdad= view.findViewById<TextView>(R.id.filaEdad)

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
    }

    override fun getItemCount(): Int {
        return listaPersonas.size
    }

}