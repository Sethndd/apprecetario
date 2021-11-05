package com.example.recetario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageButton1.setOnClickListener{abrirReceta("Entradas")}
        imageButton2.setOnClickListener{abrirReceta("Platillos principales")}
        imageButton3.setOnClickListener{abrirReceta("Complementos")}
        imageButton4.setOnClickListener{abrirReceta("Postres")}

        btnAgregarReceta.setOnClickListener {
            val intent = Intent(this, AgregarReceta::class.java)
            startActivity(intent)
        }
    }

    private fun abrirReceta(tipo: String) {
        val intent = Intent(this, MenuCategoria::class.java)
        startActivity(intent.putExtra("tipo", tipo))
    }
}