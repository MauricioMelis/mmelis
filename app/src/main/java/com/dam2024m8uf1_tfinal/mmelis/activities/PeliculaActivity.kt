package com.dam2024m8uf1_tfinal.mmelis.activities

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.dam2024m8uf1_tfinal.mmelis.`object`.Pelicula
import com.dam2024m8uf1_tfinal.mmelis.R
import com.dam2024m8uf1_tfinal.mmelis.singleton.MovieRepository

class PeliculaActivity : AppCompatActivity() {

    private val listaDirectores = listOf("Director 1", "Director 2", "Director 3")
    private val listaPaises = listOf("España", "Francia", "Estados Unidos")

    private var isEditMode = false
    private var moviePosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_pelicula)

        // Inicializar vistas
        val etTitulo: EditText = findViewById(R.id.etTitulo)
        val etDirector: Spinner = findViewById(R.id.etDirector)
        val etAnioLanzamiento: EditText = findViewById(R.id.etAñoLanzamiento)
        val radioGroupGenero: RadioGroup = findViewById(R.id.radioGroupGenero)
        val etDuracion: SeekBar = findViewById(R.id.etDuracion)
        val textViewDuracionValor: TextView = findViewById(R.id.textViewDuracionValor)
        val etPais: Spinner = findViewById(R.id.etPais)
        val etClasificacionEdad: ToggleButton = findViewById(R.id.etClasificacionEdad)
        val etRating: RatingBar = findViewById(R.id.etRating)

        setupSpinner(etDirector, listaDirectores)
        setupSpinner(etPais, listaPaises)

        isEditMode = intent.getBooleanExtra("isEdit", false)
        moviePosition = intent.getIntExtra("position", -1)

        // Si estamos en modo edición, asegurarnos de que currentMovie esté establecido
        if (isEditMode && moviePosition != -1) {
            val selectedMovie = MovieRepository.getInstance().getMovieAt(moviePosition)
            selectedMovie?.let {
                MovieRepository.getInstance().currentMovie = it
                populateFields(it)
            }
        }

        etDuracion.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textViewDuracionValor.text = "Duración: $progress min"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        findViewById<ImageButton>(R.id.imageButtonNext).setOnClickListener {
            if (!validateInputs(etTitulo, etAnioLanzamiento, etDuracion, etRating, radioGroupGenero)) {
                Toast.makeText(this, "Por favor, completa todos los campos obligatorios.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val pelicula = createMovieFromInput(etTitulo, etDirector, etAnioLanzamiento,
                radioGroupGenero, etDuracion, etPais, etClasificacionEdad, etRating)

            // Preservar los datos adicionales si estamos en modo edición
            if (isEditMode) {
                val currentMovie = MovieRepository.getInstance().currentMovie
                pelicula.actorPrincipal = currentMovie.actorPrincipal
                pelicula.sinopsis = currentMovie.sinopsis
                pelicula.fechaVisualizacion = currentMovie.fechaVisualizacion
                pelicula.esFavorita = currentMovie.esFavorita
                pelicula.tieneSubtitulos = currentMovie.tieneSubtitulos
                pelicula.esOriginal = currentMovie.esOriginal
            }

            MovieRepository.getInstance().currentMovie = pelicula

            val intent = Intent(this, CrearPelicula1Activity::class.java)
            intent.putExtra("isEdit", isEditMode)
            intent.putExtra("position", moviePosition)
            startActivity(intent)
        }

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

    private fun populateFields(movie: Pelicula) {
        val etTitulo: EditText = findViewById(R.id.etTitulo)
        val etDirector: Spinner = findViewById(R.id.etDirector)
        val etAnioLanzamiento: EditText = findViewById(R.id.etAñoLanzamiento)
        val etDuracion: SeekBar = findViewById(R.id.etDuracion)
        val textViewDuracionValor: TextView = findViewById(R.id.textViewDuracionValor)
        val etPais: Spinner = findViewById(R.id.etPais)
        val etClasificacionEdad: ToggleButton = findViewById(R.id.etClasificacionEdad)
        val etRating: RatingBar = findViewById(R.id.etRating)
        val radioGroupGenero: RadioGroup = findViewById(R.id.radioGroupGenero)

        etTitulo.setText(movie.titulo)
        etDirector.setSelection(listaDirectores.indexOf(movie.director))
        etAnioLanzamiento.setText(movie.añoLanzamiento.toString())
        etDuracion.progress = movie.duracion
        textViewDuracionValor.text = "Duración: ${movie.duracion} min"
        etPais.setSelection(listaPaises.indexOf(movie.paisOrigen))
        etClasificacionEdad.isChecked = movie.clasificacionEdad == "Apto para todo público"
        etRating.rating = movie.rating

        when (movie.genero) {
            "Acción" -> radioGroupGenero.check(R.id.radioButtonAccion)
            "Comedia" -> radioGroupGenero.check(R.id.radioButtonComedia)
            "Drama" -> radioGroupGenero.check(R.id.radioButtonDrama)
            "Terror" -> radioGroupGenero.check(R.id.radioButtonTerror)
            else -> radioGroupGenero.clearCheck()
        }
    }

    private fun createMovieFromInput(
        etTitulo: EditText,
        etDirector: Spinner,
        etAnioLanzamiento: EditText,
        radioGroupGenero: RadioGroup,
        etDuracion: SeekBar,
        etPais: Spinner,
        etClasificacionEdad: ToggleButton,
        etRating: RatingBar
    ): Pelicula {
        return Pelicula(
            titulo = etTitulo.text.toString(),
            director = etDirector.selectedItem.toString(),
            añoLanzamiento = etAnioLanzamiento.text.toString().toIntOrNull() ?: 0,
            genero = getSelectedGenero(radioGroupGenero),
            duracion = etDuracion.progress,
            paisOrigen = etPais.selectedItem.toString(),
            clasificacionEdad = if (etClasificacionEdad.isChecked) "Apto para todo público" else "No apto para todo público",
            rating = etRating.rating
        )
    }

    private fun getSelectedGenero(radioGroupGenero: RadioGroup): String {
        return when (radioGroupGenero.checkedRadioButtonId) {
            R.id.radioButtonAccion -> "Acción"
            R.id.radioButtonComedia -> "Comedia"
            R.id.radioButtonDrama -> "Drama"
            R.id.radioButtonTerror -> "Terror"
            else -> "Sin género"
        }
    }

    private fun validateInputs(
        etTitulo: EditText,
        etAnioLanzamiento: EditText,
        etDuracion: SeekBar,
        etRating: RatingBar,
        radioGroupGenero: RadioGroup
    ): Boolean {
        if (etTitulo.text.isBlank()) {
            return false
        }

        val anioLanzamiento = etAnioLanzamiento.text.toString().toIntOrNull()
        if (anioLanzamiento == null || anioLanzamiento <= 0) {
            return false
        }

        if (etDuracion.progress <= 0) {
            return false
        }

        if (etRating.rating <= 0) {
            return false
        }

        if (radioGroupGenero.checkedRadioButtonId == -1) {
            return false
        }

        return true
    }
}