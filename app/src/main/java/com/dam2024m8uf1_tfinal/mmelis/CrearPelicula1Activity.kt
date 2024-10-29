package com.dam2024m8uf1_tfinal.mmelis

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Switch
import android.widget.Toast
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

        DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            etDate.text = "$selectedDay/${selectedMonth + 1}/$selectedYear"
        }, year, month, day).show()
    }

    private fun crearPelicula() {
        // Obtener datos de las vistas
        val actorPrincipal = etActorPrincipal.selectedItem.toString()
        val sinopsis = etSinopsis.text.toString()
        val favorita = etFavorita.isChecked
        val subtitulosSeleccion = findViewById<RadioButton>(etSubtitulos.checkedRadioButtonId)
        val subtitulos = subtitulosSeleccion?.text.toString()
        val original = etOriginal.isChecked

        // Validar datos
        if (sinopsis.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa una sinopsis.", Toast.LENGTH_SHORT).show()
            return
        }

        // Convertir subtitulos a Boolean
        val tieneSubtitulos = when (subtitulos) {
            "Sí" -> true
            "No" -> false
            else -> false
        }

        val currentMovie = MovieRepository.getInstance().currentMovie
        currentMovie.actorPrincipal = actorPrincipal
        currentMovie.sinopsis = sinopsis
        currentMovie.esFavorita = favorita
        currentMovie.tieneSubtitulos = tieneSubtitulos
        currentMovie.esOriginal = original


        Toast.makeText(this, "Película añadida exitosamente", Toast.LENGTH_SHORT).show()


        volverAlMenuPrincipal()
    }


    private fun volverAlMenuPrincipal() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Opcional
    }

    override fun onBackPressed() {
        super.onBackPressed() // Esto devolverá a la actividad anterior
    }
}
