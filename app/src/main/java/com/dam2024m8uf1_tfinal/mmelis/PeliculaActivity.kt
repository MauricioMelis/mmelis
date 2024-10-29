package com.dam2024m8uf1_tfinal.mmelis

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.dam2024m8uf1_tfinal.mmelis.singleton.MovieRepository

class PeliculaActivity : AppCompatActivity() {

    // Listas de directores y países
    private val listaDirectores =
        listOf("Director 1", "Director 2", "Director 3") // Rellena con tus datos
    private val listaPaises = listOf("España", "Francia", "Estados Unidos") // Rellena con tus datos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_pelicula)

        val etTitulo: EditText = findViewById(R.id.etTitulo)
        val etDirector: Spinner = findViewById(R.id.etDirector)
        val etAnioLanzamiento: EditText = findViewById(R.id.etAñoLanzamiento)
        val etGenero: LinearLayout = findViewById(R.id.etGenero)
        val etDuracion: SeekBar = findViewById(R.id.etDuracion)
        val textViewDuracionValor: TextView = findViewById(R.id.textViewDuracionValor)
        val etPais: Spinner = findViewById(R.id.etPais)
        val etClasificacionEdad: ToggleButton = findViewById(R.id.etClasificacionEdad)
        val etRating: RatingBar = findViewById(R.id.etRating)

        // Configurar los Spinners
        val directorAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, listaDirectores)
        directorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        etDirector.adapter = directorAdapter

        val paisAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaPaises)
        paisAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        etPais.adapter = paisAdapter

        // Listener para el SeekBar
        etDuracion.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textViewDuracionValor.text =
                    "Duración: $progress min" // Actualiza el TextView al cambiar el SeekBar
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Listener para guardar datos y navegar a la siguiente actividad
        findViewById<ImageButton>(R.id.imageButtonNext).setOnClickListener {
            // Crea una nueva película con los datos ingresados
            val pelicula = Pelicula(
                titulo = etTitulo.text.toString(),
                director = etDirector.selectedItem.toString(),
                añoLanzamiento = etAnioLanzamiento.text.toString().toIntOrNull() ?: 0,
                genero = getSelectedGenero(etGenero),
                duracion = etDuracion.progress,
                paisOrigen = etPais.selectedItem.toString(),
                clasificacionEdad = if (etClasificacionEdad.isChecked) "Apto para todo público" else "No apto para todo público",
                rating = etRating.rating
            )

// Asignar la película al repositorio
            MovieRepository.getInstance().currentMovie = pelicula
            MovieRepository.getInstance().addMovie(pelicula)
// Navegar a activity_crear_pelicula1
            val intent = Intent(this, CrearPelicula1Activity::class.java)
            startActivity(intent)
        }

        // Listener para el botón de cancelación que regresa a MainActivity
        findViewById<ImageButton>(R.id.imageButtonCancel).setOnClickListener {
            // Regresar a la actividad principal
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Opcional: cerrar la actividad actual
        }
    }

    private fun getSelectedGenero(linearLayout: LinearLayout): String {
        val radioButtonAccion: RadioButton = findViewById(R.id.radioButtonAccion)
        val radioButtonComedia: RadioButton = findViewById(R.id.radioButtonComedia)
        val radioButtonDrama: RadioButton = findViewById(R.id.radioButtonDrama)
        val radioButtonTerror: RadioButton = findViewById(R.id.radioButtonTerror)

        return when {
            radioButtonAccion.isChecked -> "Acción"
            radioButtonComedia.isChecked -> "Comedia"
            radioButtonDrama.isChecked -> "Drama"
            radioButtonTerror.isChecked -> "Terror"
            else -> "Sin género" // O manejarlo como desees
        }
    }
}
