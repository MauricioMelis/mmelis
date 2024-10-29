package com.dam2024m8uf1_tfinal.mmelis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PeliculaViewModel : ViewModel() {

    private val _titulo = MutableLiveData<String>()
    val titulo: LiveData<String> get() = _titulo

    private val _director = MutableLiveData<String>()
    val director: LiveData<String> get() = _director

    private val _anioLanzamiento = MutableLiveData<String>()
    val anioLanzamiento: LiveData<String> get() = _anioLanzamiento

    private val _genero = MutableLiveData<String>()
    val genero: LiveData<String> get() = _genero

    private val _duracion = MutableLiveData<Int>()
    val duracion: LiveData<Int> get() = _duracion

    private val _pais = MutableLiveData<String>()
    val pais: LiveData<String> get() = _pais

    private val _clasificacionEdad = MutableLiveData<Boolean>()
    val clasificacionEdad: LiveData<Boolean> get() = _clasificacionEdad

    private val _rating = MutableLiveData<Float>()
    val rating: LiveData<Float> get() = _rating

    // Método para guardar los datos de la película
    fun guardarPelicula() {
        // Aquí puedes implementar la lógica para guardar la película,
        // como guardar en una base de datos o enviar a un API.
    }

    // Métodos para actualizar cada campo
    fun setTitulo(titulo: String) {
        _titulo.value = titulo
    }

    fun setDirector(director: String) {
        _director.value = director
    }

    fun setAnioLanzamiento(anio: String) {
        _anioLanzamiento.value = anio
    }

    fun setGenero(genero: String) {
        _genero.value = genero
    }

    fun setDuracion(duracion: Int) {
        _duracion.value = duracion
    }

    fun setPais(pais: String) {
        _pais.value = pais
    }

    fun setClasificacionEdad(clasificacion: Boolean) {
        _clasificacionEdad.value = clasificacion
    }

    fun setRating(rating: Float) {
        _rating.value = rating
    }
}
