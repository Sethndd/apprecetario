package com.example.recetario.dataaccess

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import android.widget.Toast


class ProcedimientoDAO {
    companion object{
        fun obtenerProcedimiento(idReceta: Int, context: Context): String{
            var res = ""

            try{
                val admin = AdminSQLite (context, "recetario", null, 1)
                val bd = admin.writableDatabase
                val fila = bd.rawQuery("SELECT descripcion FROM procedimiento WHERE idReceta = ${idReceta}", null)

                fila.moveToFirst()
                res = fila.getString(0)
            }
            catch (ex: SQLiteException){
                Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show()
            }

            return res
        }

        fun guardarProcedimiento(idReceta: Int, descripcion: String, context: Context){
            try{
                val admin = AdminSQLite (context, "recetario", null, 1)
                val bd = admin.writableDatabase
                val registro = ContentValues()

                registro.put("idReceta", idReceta)
                registro.put("descripcion", descripcion)
                bd.insert("procedimiento", null, registro)

                bd.close()
            }
            catch (ex: SQLiteException){
                Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        fun borrarProcedimiento(idProcedimiento: Int , context: Context){
            try {
                val admin = AdminSQLite (context, "recetario", null, 1)
                val bd = admin.writableDatabase
                bd.rawQuery("DELETE FROM procedimiento WHERE idReceta = ${idProcedimiento}", null)

                bd.close()
            }
            catch (ex: SQLiteException){
                Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}