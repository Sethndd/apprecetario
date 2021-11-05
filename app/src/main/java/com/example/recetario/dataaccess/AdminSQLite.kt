package com.example.recetario.dataaccess

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase.CursorFactory

class AdminSQLite(context: Context, name:String, factory: SQLiteDatabase.CursorFactory?, version: Int):  SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE receta (id INTEGER PRIMARY KEY, nombre text, tipo text, urlimg text, estado text, personas real)")
        db.execSQL("CREATE TABLE ingrediente (idReceta real, nombre text, cantidad text, unidad text)")
        db.execSQL("CREATE TABLE procedimiento (idReceta real, descripcion text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}