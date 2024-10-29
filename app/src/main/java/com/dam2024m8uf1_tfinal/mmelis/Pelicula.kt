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
) {
    override fun toString(): String {
        return "Pelicula(titulo='$titulo', director='$director', añoLanzamiento=$añoLanzamiento, genero='$genero', duracion=$duracion, paisOrigen='$paisOrigen', clasificacionEdad='$clasificacionEdad', rating=$rating, actorPrincipal='$actorPrincipal', sinopsis='$sinopsis', fechaVisualizacion='$fechaVisualizacion', esFavorita=$esFavorita, tieneSubtitulos=$tieneSubtitulos, esOriginal=$esOriginal)"
    }

    // Sobrescribir equals y hashCode para permitir la comparación por contenido
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Pelicula) return false
        return titulo == other.titulo // Comparar por título u otro atributo único
    }

    override fun hashCode(): Int {
        return titulo.hashCode() // Asegúrate de que hashCode coincida con equals
    }
}

