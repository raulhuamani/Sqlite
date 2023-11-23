package com.rhuamani.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import com.rhuamani.sqlite.entidades.Persona

class PersonaDAO(context: Context) {
    private var sqliteDB:SqliteDB = SqliteDB(context)

    fun cargarPersona():ArrayList<Persona>{
        val listaPersonas: ArrayList<Persona> = ArrayList()
        val query = "SELECT * FROM personas"
        val db = sqliteDB.readableDatabase
        val cursor:Cursor
        try {
            cursor = db.rawQuery(query, null)
            if (cursor.count > 0){
                cursor.moveToFirst()
                do {
                    val id:Int = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                    val dni:String = cursor.getString(cursor.getColumnIndexOrThrow("dni"))
                    val nombres:String = cursor.getString(cursor.getColumnIndexOrThrow("nombres"))
                    val correo:String = cursor.getString(cursor.getColumnIndexOrThrow("correo"))
                    val edad:Int = cursor.getInt(cursor.getColumnIndexOrThrow("edad"))
                    val persona = Persona()
                    persona.id = id
                    persona.dni = dni
                    persona.nombres = nombres
                    persona.correo = correo
                    persona.edad = edad

                    listaPersonas.add(persona)
                }while (cursor.moveToNext())
            }

        }catch (e:Exception){

        }finally {
            db.close()
        }
        return listaPersonas
    }

    fun registrarPersona(persona: Persona): String {
        var respuesta = ""
        val db = sqliteDB.writableDatabase
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