package com.dam2024m8uf1_tfinal.mmelis

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewPeliculas: RecyclerView
    private lateinit var buttonAdd: Button
    private lateinit var buttonEdit: Button
    private lateinit var buttonDelete: Button

    private lateinit var peliculaAdapter: PeliculaAdapter // Asegúrate de tener un adaptador para el RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa el RecyclerView y el adaptador
        recyclerViewPeliculas = findViewById(R.id.recyclerViewPeliculas)
        recyclerViewPeliculas.layoutManager = LinearLayoutManager(this)
        peliculaAdapter = PeliculaAdapter(MovieData.peliculas) // Asegúrate de tener la lista de películas en MovieData
        recyclerViewPeliculas.adapter = peliculaAdapter

        // Inicializa los botones
        buttonAdd = findViewById(R.id.buttonAdd)
        buttonEdit = findViewById(R.id.buttonEdit)
        buttonDelete = findViewById(R.id.buttonDelete)

        // Configura los listeners para los botones
        buttonAdd.setOnClickListener {
            // Abre la actividad para añadir una nueva película
            val intent = Intent(this, PeliculaActivity::class.java)
            startActivity(intent)
        }

        buttonEdit.setOnClickListener {
            // Obtiene la película seleccionada
            val selectedPosition = peliculaAdapter.getSelectedPosition()
            if (selectedPosition != -1) {
                // Abre la actividad para editar la película seleccionada
                val intent = Intent(this, PeliculaActivity::class.java)
                intent.putExtra("position", selectedPosition) // Pasa la posición de la película seleccionada
                startActivity(intent)
            } else {
                // Muestra un mensaje de error si no hay ninguna película seleccionada
                // Puedes usar un Toast o un AlertDialog para esto
            }
        }

        buttonDelete.setOnClickListener {
            // Obtiene la película seleccionada
            val selectedPosition = peliculaAdapter.getSelectedPosition()
            if (selectedPosition != -1) {
                // Elimina la película de la lista
                MovieData.peliculas.removeAt(selectedPosition)
                peliculaAdapter.notifyItemRemoved(selectedPosition)
            } else {
                // Muestra un mensaje de error si no hay ninguna película seleccionada
            }
        }
    }
}
