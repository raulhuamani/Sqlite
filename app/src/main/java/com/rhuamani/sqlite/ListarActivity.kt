package com.rhuamani.sqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListarActivity : AppCompatActivity() {
    private lateinit var  rvPersonas: RecyclerView
    private lateinit var btnAgregar:FloatingActionButton
    private lateinit var personaDAO: PersonaDAO
    private var adaptador:Adaptador? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar)
        AsignarReferencias()
        mostrarPersonas()
    }
    private fun AsignarReferencias(){
        personaDAO = PersonaDAO(this)
        btnAgregar = findViewById(R.id.btnNuevo)
        btnAgregar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        rvPersonas = findViewById(R.id.rvPersonas)
        rvPersonas.layoutManager= LinearLayoutManager(this)
        adaptador = Adaptador()
        rvPersonas.adapter=adaptador
    }
    private fun mostrarPersonas(){
        val listaPersonas = personaDAO.cargarPersona()
        adaptador?.contexto(this)
        adaptador?.agregarItems(listaPersonas)
        Log.d("===", "${listaPersonas.size}")

    }
}