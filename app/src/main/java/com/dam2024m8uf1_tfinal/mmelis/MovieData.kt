package com.dam2024m8uf1_tfinal.mmelis

object MovieData {
    var titulo: String = ""
    var director: String = ""
    var añoLanzamiento: Int = 0
    var genero: String = ""
    var duracion: Int = 0
    var paisOrigen: String = ""
    var clasificacionEdad: String = ""
    var rating: Float = 0.0f // Cambié a Float para que coincida con tu clase
    var actorPrincipal: String = ""
    var sinopsis: String = ""
    var fechaVisualizacion: String = ""
    var esFavorita: Boolean = false
    var tieneSubtitulos: Boolean = false
    var esOriginal: Boolean = false
    val peliculas: MutableList<Pelicula> = mutableListOf()
    // Puedes agregar métodos para facilitar la manipulación de los datos si es necesario
    fun clear() {
        titulo = ""
        director = ""
        añoLanzamiento = 0
        genero = ""
        duracion = 0
        paisOrigen = ""
        clasificacionEdad = ""
        rating = 0.0f
        actorPrincipal = ""
        sinopsis = ""
        fechaVisualizacion = ""
        esFavorita = false
        tieneSubtitulos = false
        esOriginal = false
    }
}
