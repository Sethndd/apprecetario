package com.example.recetario.recyclerviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recetario.R
import com.example.recetario.poko.Ingrediente
import kotlinx.android.synthetic.main.card_ingrediente.view.*

class IngredienteAdapter(var listaIngredientes: MutableList<Ingrediente>): RecyclerView.Adapter<IngredienteAdapter.IngredienteHolder>(){

    class IngredienteHolder(val view: View): RecyclerView.ViewHolder(view){
        fun render(ingrediente: Ingrediente){
            view.tbNombre.text = ingrediente.nombre
            view.tbCantidad.text = ingrediente.cantidad
            view.tbUnidad.text = ingrediente.unidad
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int ): IngredienteHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return IngredienteHolder(layoutInflater.inflate(R.layout.card_ingrediente, parent, false))
    }

    override fun onBindViewHolder(holder: IngredienteHolder, position: Int) {
        holder.render(listaIngredientes[position])
    }

    override fun getItemCount(): Int {
       return listaIngredientes.size
    }
}