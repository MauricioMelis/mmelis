package com.dam2024m8uf1_tfinal.mmelis.singleton

import android.util.Log
import com.dam2024m8uf1_tfinal.mmelis.Pelicula

class MovieRepository {
    private val movieList = mutableListOf<Pelicula>()
    lateinit var currentMovie: Pelicula

    private constructor()

    companion object {
        private var instance: MovieRepository? = null

        fun getInstance(): MovieRepository {
            return instance ?: synchronized(this) {
                instance ?: MovieRepository().also { instance = it }
            }
        }
    }

    // Method to add or update a movie based on currentMovie
    fun addMovie(movie: Pelicula) {
        // Verificamos si currentMovie está inicializado y coincide con el título de la película editada
        if (::currentMovie.isInitialized) {
            val index = movieList.indexOfFirst { it == currentMovie }
            if (index != -1) {
                movieList[index] = movie // Actualizamos la película existente
                Log.i("MovieRepository", "Movie updated: ${movie.titulo}")
                return // Salimos del método después de actualizar
            }
        }
        // Si no se encontró currentMovie o no está en la lista, agregamos una nueva película
        movieList.add(movie) // Agregar nueva película
        Log.i("MovieRepository", "Movie added: ${movie.titulo}")
    }
    fun updateMovie(position: Int, updatedMovie: Pelicula) {
        if (position in 0 until movieList.size) {
            movieList[position] = updatedMovie // Asumiendo que 'movies' es tu lista de películas
        }
    }

    // Method to get all movies
    fun getMovies(): List<Pelicula> = movieList.toList()

    // Optional: Method to get a movie by position, if needed elsewhere
    fun getMovieAt(position: Int): Pelicula? = movieList.getOrNull(position)

    // Method to delete a movie by instance
    fun deleteMovie(movie: Pelicula) {
        if (movieList.remove(movie)) {
            Log.i("MovieRepository", "Movie deleted: ${movie.titulo}")
        } else {
            Log.i("MovieRepository", "Movie not found for deletion: ${movie.titulo}")
        }
    }
}
