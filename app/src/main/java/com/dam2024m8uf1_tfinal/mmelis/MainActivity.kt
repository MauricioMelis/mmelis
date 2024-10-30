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

    private lateinit var peliculaAdapter: PeliculaAdapter // Asegúrate de tener un adaptador para el RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa el RecyclerView y el adaptador
        recyclerViewPeliculas = findViewById(R.id.recyclerViewPeliculas)
        recyclerViewPeliculas.layoutManager = LinearLayoutManager(this)
        peliculaAdapter = PeliculaAdapter(MovieRepository.getInstance().getMovies().toMutableList()) // Usa la lista del repositorio
        recyclerViewPeliculas.adapter = peliculaAdapter

        // Inicializa los botones
        buttonAdd = findViewById(R.id.buttonAdd)
        buttonEdit = findViewById(R.id.buttonEdit)
        buttonDelete = findViewById(R.id.buttonDelete)

        // Configura los listeners para los botones
        buttonAdd.setOnClickListener {
            // Abre la actividad para añadir una nueva película
            val intent = Intent(this, PeliculaActivity::class.java) // Asegúrate de usar la actividad correcta
            startActivity(intent)
        }

        buttonEdit.setOnClickListener {
            val selectedPosition = peliculaAdapter.getSelectedPosition()
            if (selectedPosition != -1) {
                val selectedMovie = MovieRepository.getInstance().getMovies()[selectedPosition]

                val intent = Intent(this, PeliculaActivity::class.java)
                intent.putExtra("position", selectedPosition) // Posición en la lista
                intent.putExtra("movie", selectedMovie) // Pasa la película seleccionada
                startActivity(intent)
            } else {
                Toast.makeText(this, "Por favor, selecciona una película para editar.", Toast.LENGTH_SHORT).show()
            }
        }



        buttonDelete.setOnClickListener {
            // Obtiene la película seleccionada
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
        peliculaAdapter.updateMovies(peliculas.toMutableList()) // Asegúrate de convertirla en MutableList si es necesario
    }
}
