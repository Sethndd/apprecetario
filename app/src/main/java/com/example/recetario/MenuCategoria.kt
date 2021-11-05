package com.example.recetario

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recetario.dataaccess.RecetaDAO
import com.example.recetario.recyclerviews.RecetaAdapter
import com.example.recetario.poko.Receta
import kotlinx.android.synthetic.main.activity_menu_categoria.*
import java.util.*

class MenuCategoria : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = intent.extras
        val tipo = bundle?.getString("tipo")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_categoria)

        val listaRecetas: List<Receta> = Collections.unmodifiableList(RecetaDAO.obtenerRecetas(tipo.toString(), this))
        iniciarRecyclerview(listaRecetas)

        btnRegresar.setOnClickListener {
            finish()
        }
    }

    fun iniciarRecyclerview(listaRecetas: List<Receta>){
        rvMenu.layoutManager = LinearLayoutManager(this)
        rvMenu.adapter = RecetaAdapter(listaRecetas, this)
    }
}