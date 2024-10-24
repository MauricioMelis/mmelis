package com.dam2024m8uf1_tfinal.mmelis

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditarPeliculaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_pelicula)

        val pelicula = PeliculaManager.obtenerPelicula()

        // Cargar los datos de la película en los campos
        if (pelicula != null) {
            findViewById<EditText>(R.id.etTitulo).setText(pelicula.titulo)
            findViewById<EditText>(R.id.etDirector).setText(pelicula.director)
            findViewById<EditText>(R.id.etAño).setText(pelicula.añoLanzamiento.toString())
            // Configurar los demás campos de manera similar...
        }

        val btnGuardarCambios = findViewById<Button>(R.id.btnGuardarCambios)
        btnGuardarCambios.setOnClickListener {
            // Similar a CrearPeliculaActivity pero para editar la película
            val titulo = findViewById<EditText>(R.id.etTitulo).text.toString()
            val director = findViewById<EditText>(R.id.etDirector).text.toString()
            val añoLanzamiento = findViewById<EditText>(R.id.etAño).text.toString().toInt()
            // Actualizar el resto de atributos...

            val nuevaPelicula = Pelicula(titulo, director, añoLanzamiento, genero, duracion, paisOrigen, calificacionEdad,
                rating, actoresPrincipales, sinopsis, fechaVisualizacion, esFavorita, tieneSubtitulos, esOriginal)

            PeliculaManager.editarPelicula(nuevaPelicula)

            // Volver a la pantalla principal
            finish()
        }
    }
}
