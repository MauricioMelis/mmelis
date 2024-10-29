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

    // Lista de actores (puedes agregar tus propios actores)
    private val listaActores = listOf("Actor 1", "Actor 2", "Actor 3")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_pelicula1)

        Log.d("CrearPelicula1Activity", "onCreate: Activity started")

        // Inicializar vistas
        etActorPrincipal = findViewById(R.id.etActorPrincipal)
        etSinopsis = findViewById(R.id.etSinopsis)
        etDate = findViewById(R.id.etDate)
        etFavorita = findViewById(R.id.etFavorita)
        etSubtitulos = findViewById(R.id.etSubtitulos)
        etOriginal = findViewById(R.id.etOriginal)
        etAñadir = findViewById(R.id.etAñadir)
        etCancel = findViewById(R.id.etCancel)

        // Configurar el Spinner con la lista de actores
        val actorAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaActores)
        actorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        etActorPrincipal.adapter = actorAdapter

        // Manejar el clic en el botón para agregar la película
        etAñadir.setOnClickListener {
            Log.d("CrearPelicula1Activity", "Añadir button clicked")
            crearPelicula()
        }

        // Manejar el clic en el botón para cancelar y volver al menú principal
        etCancel.setOnClickListener {
            Log.d("CrearPelicula1Activity", "Cancel button clicked")
            volverAlMenuPrincipal()
        }

        // Manejar el clic en el botón para elegir la fecha
        etDate.setOnClickListener {
            mostrarDatePickerDialog()
        }
    }

    private fun mostrarDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            // Actualiza el botón con la fecha seleccionada
            etDate.text = "$selectedDay/${selectedMonth + 1}/$selectedYear"
        }, year, month, day)

        datePickerDialog.show()
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
            "Sí" -> true // Si el texto es "Sí", asignar true
            "No" -> false // Si el texto es "No", asignar false
            else -> false // Por defecto, asignar false si no hay coincidencia
        }

        // Crear la nueva película (implementa la lógica según tu modelo de datos)
        val nuevaPelicula = Pelicula(
            titulo = MovieData.titulo,
            director = MovieData.director,
            añoLanzamiento = MovieData.añoLanzamiento,
            genero = MovieData.genero,
            duracion = MovieData.duracion,
            paisOrigen = MovieData.paisOrigen,
            clasificacionEdad = MovieData.clasificacionEdad,
            rating = MovieData.rating,
            actorPrincipal = actorPrincipal,
            sinopsis = sinopsis,
            fechaVisualizacion = MovieData.fechaVisualizacion,
            esFavorita = favorita,
            tieneSubtitulos = tieneSubtitulos,
            esOriginal = original
        )

        // Agregar la película a tu base de datos o lista de películas

        // Mostrar mensaje de éxito
        Toast.makeText(this, "Película creada con éxito.", Toast.LENGTH_SHORT).show()

        // Volver a la actividad principal (opcional)
        volverAlMenuPrincipal()
    }

    private fun volverAlMenuPrincipal() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Opcional, cierra la actividad actual
    }

    override fun onBackPressed() {
        // Opcionalmente puedes añadir lógica personalizada aquí
        super.onBackPressed() // Esto devolverá a la actividad anterior
    }
}
