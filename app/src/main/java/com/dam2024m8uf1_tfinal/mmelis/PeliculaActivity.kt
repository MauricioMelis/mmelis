package com.dam2024m8uf1_tfinal.mmelis

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class PeliculaActivity : AppCompatActivity() {

    private val peliculaViewModel: PeliculaViewModel by viewModels()

    // Listas de directores y países
    private val listaDirectores = listOf("Director 1", "Director 2", "Director 3") // Rellena con tus datos
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
        val directorAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaDirectores)
        directorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        etDirector.adapter = directorAdapter

        val paisAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaPaises)
        paisAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        etPais.adapter = paisAdapter

        // Observadores para LiveData
        peliculaViewModel.titulo.observe(this, Observer {
            etTitulo.setText(it)
        })

        peliculaViewModel.director.observe(this, Observer {
            // Aquí puedes establecer el director en el Spinner si lo necesitas
        })

        peliculaViewModel.anioLanzamiento.observe(this, Observer {
            etAnioLanzamiento.setText(it)
        })

        peliculaViewModel.genero.observe(this, Observer {
            // Aquí puedes manejar el género si lo necesitas
        })

        peliculaViewModel.duracion.observe(this, Observer {
            etDuracion.progress = it ?: 0
            textViewDuracionValor.text = "Duración: ${it ?: 0} min" // Actualiza el TextView
        })

        peliculaViewModel.pais.observe(this, Observer {
            // Aquí puedes establecer el país en el Spinner si lo necesitas
        })

        peliculaViewModel.clasificacionEdad.observe(this, Observer {
            etClasificacionEdad.isChecked = it
        })

        peliculaViewModel.rating.observe(this, Observer {
            etRating.rating = it
        })

        // Listener para el SeekBar
        etDuracion.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textViewDuracionValor.text = "Duración: $progress min" // Actualiza el TextView al cambiar el SeekBar
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Puedes dejarlo vacío si no necesitas hacer nada al iniciar el seguimiento
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Puedes dejarlo vacío si no necesitas hacer nada al detener el seguimiento
            }
        })

        // Listener para guardar datos y navegar a la siguiente actividad
        findViewById<ImageButton>(R.id.imageButtonNext).setOnClickListener {
            peliculaViewModel.setTitulo(etTitulo.text.toString())
            peliculaViewModel.setDirector(etDirector.selectedItem.toString())
            peliculaViewModel.setAnioLanzamiento(etAnioLanzamiento.text.toString())
            peliculaViewModel.setGenero(getSelectedGenero(etGenero))
            peliculaViewModel.setDuracion(etDuracion.progress)
            peliculaViewModel.setPais(etPais.selectedItem.toString())
            peliculaViewModel.setClasificacionEdad(etClasificacionEdad.isChecked)
            peliculaViewModel.setRating(etRating.rating)

            peliculaViewModel.guardarPelicula()

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
