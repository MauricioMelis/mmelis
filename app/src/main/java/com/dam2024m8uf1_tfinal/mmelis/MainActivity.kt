package com.dam2024m8uf1_tfinal.mmelis

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dam2024m8uf1_tfinal.mmelis.singleton.MovieRepository

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewPeliculas: RecyclerView
    private lateinit var buttonAdd: Button
    private lateinit var buttonEdit: Button
    private lateinit var buttonDelete: Button

    private lateinit var peliculaAdapter: PeliculaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa el RecyclerView y el adaptador
        recyclerViewPeliculas = findViewById(R.id.recyclerViewPeliculas)
        recyclerViewPeliculas.layoutManager = LinearLayoutManager(this)
        peliculaAdapter = PeliculaAdapter(MovieRepository.getInstance().getMovies().toMutableList()) { position ->
            // Manejo del clic en la película
            onMovieSelected(position)
        }
        recyclerViewPeliculas.adapter = peliculaAdapter

        // Inicializa los botones
        buttonAdd = findViewById(R.id.buttonAdd)
        buttonEdit = findViewById(R.id.buttonEdit)
        buttonDelete = findViewById(R.id.buttonDelete)

        // Configura los listeners para los botones
        buttonAdd.setOnClickListener {
            // Abre PeliculaActivity para crear una nueva película
            val intent = Intent(this, PeliculaActivity::class.java)
            intent.putExtra("isEdit", false) // Modo creación
            startActivity(intent)
        }

        buttonEdit.setOnClickListener {
            val selectedPosition = peliculaAdapter.getSelectedPosition()
            if (selectedPosition != -1) {
                // Abre PeliculaActivity para ver y editar la película
                val selectedMovie = MovieRepository.getInstance().getMovies()[selectedPosition]
                val intent = Intent(this, PeliculaActivity::class.java).apply {
                    putExtra("isEdit", true) // Modo edición
                    putExtra("position", selectedPosition) // Pasa la posición de la película
                    putExtra("titulo", selectedMovie.titulo)
                    putExtra("anioLanzamiento", selectedMovie.añoLanzamiento)
                    putExtra("sinopsis", selectedMovie.sinopsis)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Selecciona una película para editar.", Toast.LENGTH_SHORT).show()
            }
        }

        buttonDelete.setOnClickListener {
            val selectedPosition = peliculaAdapter.getSelectedPosition()
            if (selectedPosition != -1) {
                // Elimina la película de la lista
                val movieToDelete = MovieRepository.getInstance().getMovies()[selectedPosition]
                MovieRepository.getInstance().deleteMovie(movieToDelete)
                peliculaAdapter.notifyItemRemoved(selectedPosition)
            } else {
                // Muestra un mensaje de error si no hay ninguna película seleccionada
                Toast.makeText(this, "Por favor, selecciona una película para eliminar.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val peliculas = MovieRepository.getInstance().getMovies()
        Log.d("MainActivity", "Lista de películas: $peliculas")
        peliculaAdapter.updateMovies(peliculas.toMutableList())
    }

    // Manejo de la selección de la película
    private fun onMovieSelected(position: Int) {
        // Aquí puedes manejar lo que desees hacer cuando se selecciona una película
    }
}
