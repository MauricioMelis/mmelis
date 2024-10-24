package com.dam2024m8uf1_tfinal.mmelis

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Spinner
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity

class CrearPeliculaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_pelicula)

        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        btnGuardar.setOnClickListener {
            val titulo = findViewById<EditText>(R.id.etTitulo).text.toString()
            val director = findViewById<EditText>(R.id.etDirector).text.toString()
            val añoLanzamiento = findViewById<EditText>(R.id.etAño).text.toString().toInt()
            val genero = findViewById<Spinner>(R.id.spGenero).selectedItem.toString()
            val duracion = findViewById<EditText>(R.id.etDuracion).text.toString().toInt()
            val paisOrigen = findViewById<EditText>(R.id.etPais).text.toString()
            val calificacionEdad = findViewById<Spinner>(R.id.spCalificacion).selectedItem.toString()
            val rating = findViewById<RatingBar>(R.id.ratingBar).rating
            val actoresPrincipales = findViewById<EditText>(R.id.etActores).text.toString()
            val sinopsis = findViewById<EditText>(R.id.etSinopsis).text.toString()
            val fechaVisualizacion = findViewById<EditText>(R.id.etFechaVisualizacion).text.toString()
            val esFavorita = findViewById<CheckBox>(R.id.cbFavorita).isChecked
            val tieneSubtitulos = findViewById<CheckBox>(R.id.cbSubtitulos).isChecked
            val esOriginal = findViewById<ToggleButton>(R.id.tbOriginal).isChecked

            val pelicula = Pelicula(titulo, director, añoLanzamiento, genero, duracion, paisOrigen, calificacionEdad,
                rating, actoresPrincipales, sinopsis, fechaVisualizacion, esFavorita, tieneSubtitulos, esOriginal)

            PeliculaManager.crearPelicula(pelicula)

            // Volver a la pantalla principal
            finish()
        }
    }
}
