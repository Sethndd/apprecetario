package com.example.recetario

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.recetario.dataaccess.IngredienteDAO
import com.example.recetario.dataaccess.ProcedimientoDAO
import com.example.recetario.dataaccess.RecetaDAO
import kotlinx.android.synthetic.main.activity_receta.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_receta.btnRegresar
import kotlinx.android.synthetic.main.activity_receta.imageView
import kotlinx.android.synthetic.main.activity_receta.txtIngredientes
import kotlinx.android.synthetic.main.activity_receta.txtTitulo

class RecetaView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = intent.extras
        val id = bundle?.getString("id")

        val receta = RecetaDAO.obtenerReceta(id.toString(), this)
        val ingredientes = IngredienteDAO.obtenerIngredientes(id.toString().toInt(), this)
        val procedimiento = ProcedimientoDAO.obtenerProcedimiento(id.toString().toInt(), this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receta)

        val personas = arrayOf("1 persona", "2 personas", "3 personas", "4 personas", "5 personas", "6 personas", "7 personas","8 personas", "9 personas", "10 personas")
        spPersonas.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, personas)

        Picasso.get().load(receta.urlImg).into(imageView)
        txtTitulo.text = receta.nombre
        txtDescrpcion.text = procedimiento

        spPersonas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var string = ""
                for(ing in ingredientes){
                    val ingCant = ing.cantidad.toFloat()
                    val numPersonas = position.plus(1)
                    val recePersonas = receta.personas.toFloat()

                    string = string.plus("- ${ ingCant.times(numPersonas.div(recePersonas)) } ${ing.unidad} de ${ing.nombre}.\n")
                }
                txtIngredientes.text = string
            }

        }

        spPersonas.setSelection(receta.personas - 1)

        btnRegresar.setOnClickListener {
            finish()
        }
    }
}