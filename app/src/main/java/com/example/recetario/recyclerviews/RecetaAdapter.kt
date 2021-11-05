package com.example.recetario.recyclerviews

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.recetario.EditarReceta
import com.example.recetario.R
import com.example.recetario.RecetaView
import com.example.recetario.dataaccess.RecetaDAO
import com.example.recetario.poko.Receta
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_receta.view.*

class RecetaAdapter (val listaRecetas: List<Receta>, val mainActivity: Activity): RecyclerView.Adapter<RecetaAdapter.RecetaHolder>(){
    class RecetaHolder(val view: View): RecyclerView.ViewHolder(view), PopupMenu.OnMenuItemClickListener, DialogInterface.OnClickListener {
        private var mReceta = Receta(0, "", "", "", "", 0)
        private var mActivity: Activity = Activity()
        fun render(receta: Receta, mainActivity: Activity){
            mReceta = receta
            mActivity = mainActivity
            val context = view.context
            Picasso.get().load(receta.urlImg).into(view.imgElement)
            view.txtElement.text = receta.nombre

            view.setOnClickListener {
                val intent = Intent(context.applicationContext, RecetaView::class.java)
                intent.putExtra("id", receta.id.toString())
                context.startActivity(intent)
            }

            view.setOnLongClickListener {
                val popup = PopupMenu(context, view)
                popup.inflate(R.menu.popup_menu)

                popup.setOnMenuItemClickListener(this)

                popup.show();
                return@setOnLongClickListener true
            }
        }

        override fun onMenuItemClick(item: MenuItem?): Boolean {
            when (item!!.itemId){
                R.id.itemEditar -> {
//                    Toast.makeText(view.context, "Le diste a editar a la receta ".plus(mReceta.nombre), Toast.LENGTH_SHORT).show()

                    val intent = Intent(view.context.applicationContext, EditarReceta::class.java)
                    intent.putExtra("id", mReceta.id.toString())
                    view.context.startActivity(intent)

                    return true
                }
                R.id.itemEliminar -> {
                    val builder = AlertDialog.Builder(view.context)
                    builder.setTitle("Eliminar ".plus(mReceta.nombre))
                    builder.setMessage("¿Quieres eliminar esta receta?")

                    builder.setPositiveButton("Si, eliminar", this)

                    builder.setNegativeButton("Cancelar", null)
                    val dialog = builder.create()
                    dialog.show()

                    return true
                }
            }
            return false
        }

        override fun onClick(dialog: DialogInterface, which: Int) {
            RecetaDAO.borrarReceta(mReceta, view.context)
            Toast.makeText(view.context, "Receta ${mReceta.nombre} eliminada.", Toast.LENGTH_SHORT).show()

            mActivity.finish();
            mActivity.overridePendingTransition(0, 0);
            mActivity.startActivity(mActivity.intent);
            mActivity.overridePendingTransition(0, 0);
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecetaHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RecetaHolder(layoutInflater.inflate(R.layout.card_receta, parent, false))
    }

    override fun onBindViewHolder(holder: RecetaHolder, position: Int) {
        holder.render(listaRecetas[position], mainActivity)
    }

    override fun getItemCount(): Int {
        return listaRecetas.size
    }
}

