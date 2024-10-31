package com.dam2024m8uf1_tfinal.mmelis.`object`

import java.io.Serializable

data class Pelicula(
    var titulo: String = "",
    var director: String = "",
    var añoLanzamiento: Int = 0,
    var genero: String = "",
    var duracion: Int = 0,
    var paisOrigen: String = "",
    var clasificacionEdad: String = "",
    var rating: Float = 0.0f,
    var actorPrincipal: String = "",
    var sinopsis: String = "",
    var fechaVisualizacion: String = "",
    var esFavorita: Boolean = false,
    var tieneSubtitulos: Boolean = false,
    var esOriginal: Boolean = false
) : Serializable
