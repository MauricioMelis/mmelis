package com.dam2024m8uf1_tfinal.mmelis.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dam2024m8uf1_tfinal.mmelis.`object`.Pelicula
import com.dam2024m8uf1_tfinal.mmelis.R
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
    private var isEditMode = false
    private var moviePosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_pelicula1)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                volverAlMenuPrincipal()
            }
        })

        Log.d("CrearPelicula1Activity", "onCreate: Activity started")

        initViews()
        setupActorSpinner()

        isEditMode = intent.getBooleanExtra("isEdit", false)
        moviePosition = intent.getIntExtra("position", -1)

        Log.d("CrearPelicula1Activity", "isEditMode: $isEditMode, position: $moviePosition")

        // Importante: Recuperar currentMovie aquí
        if (isEditMode) {
            val currentMovie = MovieRepository.getInstance().currentMovie
            Log.d("CrearPelicula1Activity", "Current movie: ${currentMovie?.titulo}")
            currentMovie?.let {
                populateFields(it)
            }
        }

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

    private fun populateFields(pelicula: Pelicula) {
        try {
            // Establecer el actor principal en el Spinner
            val actorPosition = listaActores.indexOf(pelicula.actorPrincipal)
            Log.d("CrearPelicula1Activity", "Actor position: $actorPosition for ${pelicula.actorPrincipal}")
            if (actorPosition != -1) {
                etActorPrincipal.setSelection(actorPosition)
            }

            // Establecer la sinopsis
            etSinopsis.setText(pelicula.sinopsis)
            Log.d("CrearPelicula1Activity", "Sinopsis establecida: ${pelicula.sinopsis}")

            // Establecer la fecha
            if (pelicula.fechaVisualizacion.isNotEmpty()) {
                etDate.text = pelicula.fechaVisualizacion
                Log.d("CrearPelicula1Activity", "Fecha establecida: ${pelicula.fechaVisualizacion}")
            }

            // Establecer favorita
            etFavorita.isChecked = pelicula.esFavorita

            // Establecer subtítulos
            val radioButtonId = if (pelicula.tieneSubtitulos) {
                R.id.radioSiSubtitulos
            } else {
                R.id.radioNoSubtitulos
            }
            etSubtitulos.check(radioButtonId)

            // Establecer original
            etOriginal.isChecked = pelicula.esOriginal

        } catch (e: Exception) {
            Log.e("CrearPelicula1Activity", "Error al poblar los campos", e)
            e.printStackTrace()
        }
    }


    private fun setupListeners() {
        etAñadir.setOnClickListener {
            Log.d("CrearPelicula1Activity", "Añadir button clicked")
            agregarDetallesPelicula()
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

        // Si hay una fecha existente, intentar parsearla
        if (etDate.text.isNotEmpty() && etDate.text != "Seleccionar fecha") {
            try {
                val parts = etDate.text.toString().split("/")
                if (parts.size == 3) {
                    calendar.set(parts[2].toInt(), parts[1].toInt() - 1, parts[0].toInt())
                }
            } catch (e: Exception) {
                Log.e("CrearPelicula1Activity", "Error al parsear la fecha existente", e)
            }
        }

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val fechaSeleccionada = "${selectedDay}/${selectedMonth + 1}/$selectedYear"
            etDate.text = fechaSeleccionada
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun validateFields(): Boolean {
        var isValid = true
        val errorMessage = StringBuilder("Por favor, completa los siguientes campos:\n")

        // Validar actor principal
        if (etActorPrincipal.selectedItem.toString().isEmpty()) {
            errorMessage.append("- Actor Principal\n")
            isValid = false
        }

        // Validar sinopsis
        if (etSinopsis.text.toString().trim().isEmpty()) {
            errorMessage.append("- Sinopsis\n")
            isValid = false
        }

        // Validar fecha
        if (etDate.text.toString() == "Seleccionar fecha" || etDate.text.toString().isEmpty()) {
            errorMessage.append("- Fecha de visualización\n")
            isValid = false
        }

        // Validar subtítulos
        if (etSubtitulos.checkedRadioButtonId == -1) {
            errorMessage.append("- Selección de subtítulos\n")
            isValid = false
        }

        if (!isValid) {
            Toast.makeText(this, errorMessage.toString(), Toast.LENGTH_LONG).show()
        }

        return isValid
    }

    private fun agregarDetallesPelicula() {
        // Primero validar los campos
        if (!validateFields()) {
            return
        }

        val pelicula = MovieRepository.getInstance().currentMovie
        if (pelicula == null) {
            Log.e("CrearPelicula1Activity", "Error: currentMovie es null")
            Toast.makeText(this, "Error al guardar la película", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("CrearPelicula1Activity", "Actualizando película: ${pelicula.titulo}")

        try {
            // Actualizar los campos de la película
            pelicula.actorPrincipal = etActorPrincipal.selectedItem.toString()
            pelicula.sinopsis = etSinopsis.text.toString().trim()
            pelicula.fechaVisualizacion = etDate.text.toString()
            pelicula.esFavorita = etFavorita.isChecked
            pelicula.tieneSubtitulos = when (etSubtitulos.checkedRadioButtonId) {
                R.id.radioSiSubtitulos -> true
                R.id.radioNoSubtitulos -> false
                else -> false
            }
            pelicula.esOriginal = etOriginal.isChecked

            if (isEditMode) {
                Log.d("CrearPelicula1Activity", "Actualizando película existente en posición $moviePosition")
                MovieRepository.getInstance().updateMovie(moviePosition, pelicula)
            } else {
                Log.d("CrearPelicula1Activity", "Agregando nueva película")
                MovieRepository.getInstance().addMovie(pelicula)
            }

            // Navegar de vuelta a MainActivity
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()

        } catch (e: Exception) {
            Log.e("CrearPelicula1Activity", "Error al guardar la película", e)
            Toast.makeText(this, "Error al guardar la película", Toast.LENGTH_SHORT).show()
        }
    }

    private fun volverAlMenuPrincipal() {
        // Confirmar si el usuario quiere descartar los cambios
        AlertDialog.Builder(this)
            .setTitle("¿Descartar cambios?")
            .setMessage("¿Estás seguro de que quieres salir? Los cambios no guardados se perderán.")
            .setPositiveButton("Sí") { _, _ ->
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            .setNegativeButton("No", null)
            .show()
    }
}