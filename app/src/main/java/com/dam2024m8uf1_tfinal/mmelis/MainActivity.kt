package com.dam2024m8uf1_tfinal.mmelis

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Asegúrate de que el layout sea correcto

        // Inicializa los botones
        val btnCrear = findViewById<Button>(R.id.btnCrear)
        val btnEditar = findViewById<Button>(R.id.btnEditar)
        val btnEliminar = findViewById<Button>(R.id.btnEliminar)

        // Lógica para el botón Crear Película
        btnCrear.setOnClickListener {
            val intent = Intent(this, CrearPeliculaActivity::class.java)
            startActivity(intent)
        }

        // Lógica para el botón Editar Película
        btnEditar.setOnClickListener {
            val intent = Intent(this, EditarPeliculaActivity::class.java)
            startActivity(intent)
        }

        // Lógica para el botón Eliminar Película
        btnEliminar.setOnClickListener {
            val intent = Intent(this, EliminarPeliculaActivity::class.java)
            startActivity(intent)
        }
    }
}

