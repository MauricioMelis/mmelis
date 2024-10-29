package com.dam2024m8uf1_tfinal.mmelis.manager

import com.dam2024m8uf1_tfinal.mmelis.Pelicula
import com.dam2024m8uf1_tfinal.mmelis.singleton.MovieRepository

class MovieManager {

    private val movieRepository = MovieRepository.getInstance()

    // Método para agregar una nueva película
    fun addMovie(movie: Pelicula) {
        movieRepository.addMovie(movie)
    }

    // Método para obtener todas las películas
    fun getMovies(): List<Pelicula> {
        return movieRepository.getMovies()
    }

    // Método para actualizar una película existente usando su instancia
    fun updateMovie(oldMovie: Pelicula, newMovie: Pelicula) {
        // Llama al método updateMovie del repositorio
        movieRepository.updateMovie(oldMovie, newMovie)
    }

    // Método para eliminar una película por su instancia
    fun deleteMovie(movie: Pelicula) {
        movieRepository.deleteMovie(movie)
    }
}
