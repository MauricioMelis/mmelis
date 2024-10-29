package com.dam2024m8uf1_tfinal.mmelis.singleton

import android.util.Log
import com.dam2024m8uf1_tfinal.mmelis.Pelicula

class MovieRepository {
    // List to store movies
    private val movieList = mutableListOf<Pelicula>()
    lateinit var currentMovie: Pelicula

    // Private constructor to prevent instantiation from outside
    private constructor()

    companion object {
        // Singleton instance
        private var instance: MovieRepository? = null

        // Public method to get the singleton instance
        fun getInstance(): MovieRepository {
            return instance ?: synchronized(this) {
                instance ?: MovieRepository().also { instance = it }
            }
        }
    }

    // Method to add a new movie
    fun addMovie(movie: Pelicula) {
        movieList.add(movie)
        Log.i("MovieRepository", "Movie added: ${movie.titulo}")
    }

    // Method to get all movies
    fun getMovies(): List<Pelicula> {
        Log.i("MovieRepository", "Getting all movies. Total count: ${movieList.size}")
        return movieList.toList() // Returning a copy for safety
    }

    // Method to update an existing movie by title
    fun updateMovie(oldMovie: Pelicula, newMovie: Pelicula) {
        val index = movieList.indexOf(oldMovie)
        if (index != -1) {
            movieList[index] = newMovie
            Log.i("MovieRepository", "Movie updated: ${newMovie.titulo}")
        } else {
            Log.i("MovieRepository", "Movie not found for update: ${oldMovie.titulo}")
        }
    }

    // Method to delete a movie by its instance
    fun deleteMovie(movie: Pelicula) {
        if (movieList.remove(movie)) {
            Log.i("MovieRepository", "Movie deleted: ${movie.titulo}")
        } else {
            Log.i("MovieRepository", "Movie not found for deletion: ${movie.titulo}")
        }
    }
}
