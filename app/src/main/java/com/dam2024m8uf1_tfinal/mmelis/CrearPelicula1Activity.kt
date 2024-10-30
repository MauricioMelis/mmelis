package com.dam2024m8uf1_tfinal.mmelis

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.dam2024m8uf1_tfinal.mmelis.singleton.MovieRepository
import java.util.*

class CrearPelicula1Activity : AppCompatActivity() {

    private lateinit var etActorPrincipal: Spinner
    private lateinit var etSinopsis: EditText
    private lateinit var etDate: Button
    private lateinit var etFavorita: Switch
    private lateinit var etSubtitulos: RadioGroup
    private lateinit var etOriginal: Switch
    private lateinit var etAñadir: ImageButton
    private lateinit var etCancel: ImageButton

    private val listaActores = listOf("Actor 1", "Actor 2", "Actor 3")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_pelicula1)

        Log.d("CrearPelicula1Activity", "onCreate: Activity started")

        initViews()
        setupActorSpinner()
        setupListeners()

    }

    private fun initViews() {
        etActorPrincipal = findViewById(R.id.etActorPrincipal)
        etSinopsis = findViewById(R.id.etSinopsis)
        etDate = findViewById(R.id.etDate)
        etFavorita = findViewById(R.id.etFavorita)
        etSubtitulos = findViewById(R.id.etSubtitulos)
        etOriginal = findViewById(R.id.etOriginal)
        etAñadir = findViewById(R.id.etAñadir)
        etCancel = findViewById(R.id.etCancel)
    }

    private fun setupActorSpinner() {
        val actorAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaActores)
        actorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        etActorPrincipal.adapter = actorAdapter
    }

    private fun setupListeners() {
        etAñadir.setOnClickListener {
            Log.d("CrearPelicula1Activity", "Añadir button clicked")
            crearPelicula()
        }

        etCancel.setOnClickListener {
            Log.d("CrearPelicula1Activity", "Cancel button clicked")
            volverAlMenuPrincipal()
        }

        etDate.setOnClickListener {
            mostrarDatePickerDialog()
        }
    }

    private fun mostrarDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog =
            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val fechaSeleccionada = "${selectedDay}/${selectedMonth + 1}/$selectedYear"
                etDate.text = fechaSeleccionada
            }, year, month, day)

        datePickerDialog.show()
    }

    private fun crearPelicula() {
        val pelicula = MovieRepository.getInstance().currentMovie ?: return
        Log.d("CrearPelicula1Activity", "Pelicula en CrearPelicula1Activity: $pelicula")
        if (pelicula == null) {
            Log.e("CrearPelicula1Activity", "currentMovie es null")
        }
        pelicula.actorPrincipal = etActorPrincipal.selectedItem.toString()
        pelicula.sinopsis = etSinopsis.text.toString()
        pelicula.fechaVisualizacion = etDate.text.toString()
        pelicula.esFavorita = etFavorita.isChecked

        // Cambiar la lógica para devolver un Boolean
        pelicula.tieneSubtitulos = when (etSubtitulos.checkedRadioButtonId) {
            R.id.radioSiSubtitulos -> true  // Asignar true si se selecciona "Sí"
            R.id.radioNoSubtitulos -> false  // Asignar false si se selecciona "No"
            else -> false // Por defecto a false
        }

        pelicula.esOriginal = etOriginal.isChecked

        // Guardar en el repositorio o en la base de datos
        MovieRepository.getInstance().addMovie(pelicula)

        // Ir a la actividad principal después de añadir
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


    private fun volverAlMenuPrincipal() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}