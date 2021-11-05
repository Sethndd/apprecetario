package com.example.recetario.dataaccess

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import android.widget.Toast
import com.example.recetario.poko.Ingrediente


class IngredienteDAO {
    companion object{
        fun obtenerIngredientes(idReceta: Int, context: Context): MutableList<Ingrediente>{
            val ingredientes: MutableList<Ingrediente> = mutableListOf();

            try {
                val admin = AdminSQLite (context, "recetario", null, 1)
                val bd = admin.writableDatabase
                val fila = bd.rawQuery("SELECT * FROM ingrediente WHERE idReceta = ${idReceta}", null)

                while (fila.moveToNext()){
                    val resAux = Ingrediente(fila.getInt(0),
                        fila.getString(1),
                        fila.getString(2),
                        fila.getString(3))
                    ingredientes.add(resAux)
                }

                bd.close()
            }
            catch (ex: SQLiteException){
                Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show()
            }
            return ingredientes
        }

        fun guardarIngredientes(idReceta: Int ,ingredientes: MutableList<Ingrediente>, context: Context){
            try{
                val admin = AdminSQLite (context, "recetario", null, 1)
                val bd = admin.writableDatabase
                val registro = ContentValues()

                for (ingrediente in ingredientes){
                    registro.put("idReceta", idReceta)
                    registro.put("nombre", ingrediente.nombre)
                    registro.put("cantidad", ingrediente.cantidad)
                    registro.put("unidad", ingrediente.unidad)

                    bd.insert("ingrediente", null, registro)
                }

                bd.close()
            }
            catch (ex: SQLiteException){
                Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        fun borrarIngredientes(idReceta: Int , context: Context){
            try {
                val admin = AdminSQLite (context, "recetario", null, 1)
                val bd = admin.writableDatabase
                bd.rawQuery("DELETE FROM ingrediente WHERE idReceta = ${idReceta}", null)

                bd.close()
            }
            catch (ex: SQLiteException){
                Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}