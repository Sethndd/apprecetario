package com.example.recetario

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recetario.dataaccess.IngredienteDAO
import com.example.recetario.dataaccess.ProcedimientoDAO
import com.example.recetario.dataaccess.RecetaDAO
import com.example.recetario.poko.Ingrediente
import com.example.recetario.poko.Receta
import com.example.recetario.recyclerviews.IngredienteAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_agregar_receta.*
import kotlinx.android.synthetic.main.activity_agregar_receta.btnRegresar
import kotlinx.android.synthetic.main.activity_agregar_receta.imageView
import kotlinx.android.synthetic.main.activity_agregar_receta.spPersonas
import kotlinx.android.synthetic.main.activity_receta.*

class AgregarReceta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_receta)

        val categorias = arrayOf("Entradas", "Platillos principales", "Complementos", "Postres")
        spCategoria.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categorias)

        val ingredientes = mutableListOf<Ingrediente>()
        rvIngredientes.layoutManager = LinearLayoutManager(this)
        val adapter = IngredienteAdapter(ingredientes)
        rvIngredientes.adapter = adapter

        val personas = arrayOf("1 persona", "2 personas", "3 personas", "4 personas", "5 personas", "6 personas", "7 personas","8 personas", "9 personas", "10 personas")
        spPersonas.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, personas)

        btnAgregarIngrediente.setOnClickListener {
            if((tbNombre.text.toString().trim() == "") or (tbCantidad.text.toString().trim() == "")){
                Toast.makeText(this, "No se pueden agregar ingredientes con campos vacíos", Toast.LENGTH_SHORT).show()
            }
            else{
                ingredientes.add(Ingrediente(0, tbNombre.text.toString(), tbCantidad.text.toString(), spUnidad.selectedItem.toString()))
                adapter.notifyItemInserted(adapter.listaIngredientes.size)
                tbNombre.setText("")
                tbCantidad.setText("")
            }
        }

        btnImg.setOnClickListener {
            if(tbImgUrl.text.toString() == ""){
                Toast.makeText(this, "Introduzca un link, por favor", Toast.LENGTH_SHORT).show()
            }
            else{
                Picasso.get().load(tbImgUrl.text.toString()).into(imageView)
            }
        }

        btnBuscarImg.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse("https://www.google.com.mx/imghp"))
            startActivity(intent)
        }

        val unidad = arrayOf("cucharadas", "gramos", "mililitros", "pizcas", "tazas", "unidades")
        spUnidad.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, unidad)

        btnGuardar.setOnClickListener {
            val receta = Receta(0,
                tbTitulo.text.toString(),
                spCategoria.selectedItem.toString(),
                tbImgUrl.text.toString(),
                "activo",
                spPersonas.selectedItemPosition + 1)

            if((tbTitulo.text.trim() == "") and (tbImgUrl.text.trim() == "") and (tbDescripcion.text.trim() == "")){
                Toast.makeText(this, "Algún campo está vacio, revisar", Toast.LENGTH_SHORT).show()
            }
            else {
                val idReceta = RecetaDAO.guardarReceta(receta, this)
                IngredienteDAO.guardarIngredientes(idReceta.toInt(), ingredientes, this)
                ProcedimientoDAO.guardarProcedimiento(idReceta.toInt(), tbDescripcion.text.toString(), this)

                Toast.makeText(this, "Receta guardada!", Toast.LENGTH_LONG).show()
                finish()
            }
        }

        btnRegresar.setOnClickListener {
            finish()
        }
    }
}