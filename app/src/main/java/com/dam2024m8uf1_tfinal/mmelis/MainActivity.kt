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
import com.dam2024m8uf1_tfinal.mmelis.manager.MovieManager
import com.dam2024m8uf1_tfinal.mmelis.singleton.MovieRepository

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewPeliculas: RecyclerView
    private lateinit var buttonAdd: Button
    private lateinit var buttonEdit: Button
    private lateinit var buttonDelete: Button

    private lateinit var peliculaAdapter: PeliculaAdapter
    private lateinit var movieSelectionManager: MovieManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewPeliculas = findViewById(R.id.recyclerViewPeliculas)
        recyclerViewPeliculas.layoutManager = LinearLayoutManager(this)
        peliculaAdapter = PeliculaAdapter(MovieRepository.getInstance().getMovies().toMutableList()) { position ->
            movieSelectionManager.selectMovie(position) // Manejo del clic en la película
        }
        recyclerViewPeliculas.adapter = peliculaAdapter

        buttonAdd = findViewById(R.id.buttonAdd)
        buttonEdit = findViewById(R.id.buttonEdit)
        buttonDelete = findViewById(R.id.buttonDelete)

        // Inicializa MovieSelectionManager
        movieSelectionManager = MovieManager(this, recyclerViewPeliculas, peliculaAdapter)

        buttonAdd.setOnClickListener {
            val intent = Intent(this, PeliculaActivity::class.java)
            intent.putExtra("isEdit", false) // Modo creación
            startActivity(intent)
        }

        buttonEdit.setOnClickListener {
            val selectedPosition = movieSelectionManager.getSelectedPosition()
            movieSelectionManager.editMovie(selectedPosition)
        }

        buttonDelete.setOnClickListener {
            val selectedPosition = movieSelectionManager.getSelectedPosition()
            movieSelectionManager.deleteMovie(selectedPosition)
        }
    }

    override fun onResume() {
        super.onResume()
        val peliculas = MovieRepository.getInstance().getMovies()
        Log.d("MainActivity", "Lista de películas: $peliculas")
        peliculaAdapter.updateMovies(peliculas.toMutableList())
    }
}
