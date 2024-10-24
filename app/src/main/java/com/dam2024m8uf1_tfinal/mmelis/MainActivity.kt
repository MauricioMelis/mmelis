package com.dam2024m8uf1_tfinal.mmelis

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCrear = findViewById<Button>(R.id.btnCrear)
        val btnEditar = findViewById<Button>(R.id.btnEditar)

        btnCrear.setOnClickListener {
            // Navega a la pantalla de creación
            val intent = Intent(this, CrearPeliculaActivity::class.java)
            startActivity(intent)
        }

        btnEditar.setOnClickListener {
            // Navega a la pantalla de edición si ya existe una película
            if (PeliculaManager.obtenerPelicula() != null) {
                val intent = Intent(this, EditarPeliculaActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "No hay película para editar", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
