package com.example.recetario.dataaccess

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import android.widget.Toast
import com.example.recetario.poko.Receta

class RecetaDAO {
    companion object{
        fun obtenerRecetas(tipo: String, context: Context): MutableList<Receta>{
            val recetas: MutableList<Receta> = mutableListOf();

            try{
                val admin = AdminSQLite (context, "recetario", null, 1)
                val bd = admin.writableDatabase
                val fila = bd.rawQuery("SELECT * FROM receta WHERE tipo = '${tipo}' AND estado = 'activo' ", null)

                while (fila.moveToNext()){
                    val resAux = Receta(fila.getInt(0),
                        fila.getString(1),
                        fila.getString(2),
                        fila.getString(3),
                        fila.getString(4),
                        fila.getString(5).toInt())
                    recetas.add(resAux)
                }

                bd.close()
            }
            catch (ex: SQLiteException){
                Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show()
            }

            return recetas
        }

        fun obtenerReceta(id: String, context: Context): Receta {
            var receta = Receta(0, "", "", "", "", 0)
            try{
                val admin = AdminSQLite(context, "recetario", null, 1)
                val bd = admin.writableDatabase
                val fila = bd.rawQuery("SELECT * FROM receta WHERE id = '${id}'", null)

                fila.moveToFirst()
                receta = Receta(fila.getInt(0),
                    fila.getString(1),
                    fila.getString(2),
                    fila.getString(3),
                    fila.getString(4),
                    fila.getString(5).toInt())
            }
            catch (ex: SQLiteException){
                Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show()
            }
            return receta
        }

        fun guardarReceta(receta: Receta, context: Context): String{
            var pos = "0"
            try{
                val admin = AdminSQLite (context, "recetario", null, 1)
                val bd = admin.writableDatabase
                val registro = ContentValues()

                registro.put("nombre", receta.nombre)
                registro.put("tipo", receta.tipo)
                registro.put("urlimg", receta.urlImg)
                registro.put("estado", "activo")
                registro.put("personas", receta.personas)

                bd.insert("receta", null, registro)
                val fila = bd.rawQuery("SELECT last_insert_rowid()", null)
                fila.moveToFirst()
                pos = fila.getString(0)
                bd.close()
            }
            catch (ex: SQLiteException){
                Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show()
            }
            return pos
        }

        fun borrarReceta(receta: Receta, context: Context){
            try {
                val admin = AdminSQLite(context, "recetario", null, 1)
                val bd = admin.writableDatabase
                val registro = ContentValues()

                registro.put("nombre", receta.nombre)
                registro.put("tipo", receta.tipo)
                registro.put("urlimg", receta.urlImg)
                registro.put("estado", "inactivo")
                registro.put("personas", receta.personas)

                bd.update("receta", registro, "id = '${receta.id}'", null)
                bd.close()
            }
            catch (ex: SQLiteException){
                Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        fun actualizarReceta(receta: Receta, context: Context){
            try {
                val admin = AdminSQLite(context, "recetario", null, 1)
                val bd = admin.writableDatabase
                val registro = ContentValues()

                registro.put("nombre", receta.nombre)
                registro.put("tipo", receta.tipo)
                registro.put("urlimg", receta.urlImg)
                registro.put("estado", receta.estado)
                registro.put("personas", receta.personas)

                bd.update("receta", registro, "id = '${receta.id}'", null)
                bd.close()
            }
            catch (ex: SQLiteException){
                Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}