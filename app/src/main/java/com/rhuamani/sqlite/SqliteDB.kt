package com.rhuamani.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteDB(context: Context):SQLiteOpenHelper(context, NOMBRE_BD, null, VERSION) {
    companion object{
        private const val NOMBRE_BD = "registro.db"
        private const val VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE IF NOT EXISTS personas " +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " dni TEXT NOT NULL, "+
                " nombres TEXT NOT NULL, "+
                " correo TEXT NOT NULL, "+
                " edad INTEGER NOT NULL "+
                ");"
        db?.execSQL(sql)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS personas")
        onCreate(db)
    }

}