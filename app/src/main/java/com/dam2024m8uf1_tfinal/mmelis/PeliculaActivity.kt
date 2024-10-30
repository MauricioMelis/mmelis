package com.dam2024m8uf1_tfinal.mmelis

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.dam2024m8uf1_tfinal.mmelis.singleton.MovieRepository

class PeliculaActivity : AppCompatActivity() {

    private val listaDirectores = listOf("Director 1", "Director 2", "Director 3") // Rellena con tus datos
    private val listaPaises = listOf("España", "Francia", "Estados Unidos") // Rellena con tus datos

    private var isEditMode = false
    private var moviePosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_pelicula)

        // Inicializar vistas
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
        setupSpinner(etDirector, listaDirectores)
        setupSpinner(etPais, listaPaises)

        // Obtener datos de la intención
        moviePosition = intent.getIntExtra("position", -1)
        if (moviePosition != -1) {
            isEditMode = true
            val selectedMovie = MovieRepository.getInstance().getMovieAt(moviePosition)
            selectedMovie?.let {
                populateFields(it) // Rellena los campos con los datos de la película
            }
        }

        // Listener para el SeekBar
        etDuracion.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textViewDuracionValor.text = "Duración: $progress min"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Listener para guardar datos y navegar a la siguiente actividad
        findViewById<ImageButton>(R.id.imageButtonNext).setOnClickListener {
            val pelicula = createMovieFromInput(etTitulo, etDirector, etAnioLanzamiento, etGenero, etDuracion, etPais, etClasificacionEdad, etRating)
            MovieRepository.getInstance().currentMovie = pelicula

            if (isEditMode) {
                MovieRepository.getInstance().updateMovie(moviePosition, pelicula)
            } else {
                MovieRepository.getInstance().addMovie(pelicula)
                Log.d("PeliculaActivity", "Current Movie: $pelicula")

            }

            // Navegar a CrearPelicula1Activity
            startActivity(Intent(this, CrearPelicula1Activity::class.java))
        }

        // Listener para el botón de cancelación que regresa a MainActivity
        findViewById<ImageButton>(R.id.imageButtonCancel).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun setupSpinner(spinner: Spinner, data: List<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, data)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun createMovieFromInput(
        etTitulo: EditText,
        etDirector: Spinner,
        etAnioLanzamiento: EditText,
        etGenero: LinearLayout,
        etDuracion: SeekBar,
        etPais: Spinner,
        etClasificacionEdad: ToggleButton,
        etRating: RatingBar
    ): Pelicula {
        return Pelicula(
            titulo = etTitulo.text.toString(),
            director = etDirector.selectedItem.toString(),
            añoLanzamiento = etAnioLanzamiento.text.toString().toIntOrNull() ?: 0,
            genero = getSelectedGenero(etGenero),
            duracion = etDuracion.progress,
            paisOrigen = etPais.selectedItem.toString(),
            clasificacionEdad = if (etClasificacionEdad.isChecked) "Apto para todo público" else "No apto para todo público",
            rating = etRating.rating
        )
    }

    private fun populateFields(movie: Pelicula) {
        val etTitulo: EditText = findViewById(R.id.etTitulo)
        val etDirector: Spinner = findViewById(R.id.etDirector)
        val etAnioLanzamiento: EditText = findViewById(R.id.etAñoLanzamiento)
        val etDuracion: SeekBar = findViewById(R.id.etDuracion)
        val etPais: Spinner = findViewById(R.id.etPais)
        val etClasificacionEdad: ToggleButton = findViewById(R.id.etClasificacionEdad)
        val etRating: RatingBar = findViewById(R.id.etRating)

        etTitulo.setText(movie.titulo)
        etDirector.setSelection(listaDirectores.indexOf(movie.director))
        etAnioLanzamiento.setText(movie.añoLanzamiento.toString())
        etDuracion.progress = movie.duracion
        etPais.setSelection(listaPaises.indexOf(movie.paisOrigen))
        etClasificacionEdad.isChecked = movie.clasificacionEdad == "Apto para todo público"
        etRating.rating = movie.rating
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
            else -> "Sin género"
        }
    }
}
