package com.rhuamani.sqlite

import android.content.ContentValues
import android.content.Context
import com.rhuamani.sqlite.entidades.Persona


class PersonaDAO(context: Context) {
    private var slqiteDB:SqliteDB = SqliteDB(context)

    fun registrarPersona(persona: Persona): String {
        var respuesta = ""
        val db = slqiteDB.writableDatabase
        try {
            val valores = ContentValues()
            valores.put("dni", persona.dni)
            valores.put("nombres", persona.nombres)
            valores.put("correo", persona.correo)
            valores.put("edad", persona.edad)

            var resultado = db.insert("personas", null, valores)
            if (resultado == -1L) {
                respuesta = "Error al registrar"
            } else {
                respuesta = "Se registro correctamente"
            }
        } catch (e:Exception) {
            respuesta = e.message.toString()
        } finally {
            db.close()
        }
        return respuesta
    }
}