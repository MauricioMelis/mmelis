package com.dam2024m8uf1_tfinal.mmelis

data class Pelicula(
    val titulo: String,
    val director: String,
    val añoLanzamiento: Int,
    val genero: String,
    val duracion: Int,
    val paisOrigen: String,
    val clasificacionEdad: String,
    val rating: Float,
    val actorPrincipal: String,
    val sinopsis: String,
    val fechaVisualizacion: String,
    val esFavorita: Boolean,
    val tieneSubtitulos: Boolean,
    val esOriginal: Boolean
)

class PeliculaRepository {
    private val peliculas = mutableListOf<Pelicula>()

    fun agregarPelicula(pelicula: Pelicula) {
        peliculas.add(pelicula)
    }

    fun obtenerPeliculas(): List<Pelicula> {
        return peliculas
    }

    fun eliminarPelicula(pelicula: Pelicula) {
        peliculas.remove(pelicula)
    }
}