package com.dam2024m8uf1_tfinal.mmelis.manager

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.dam2024m8uf1_tfinal.mmelis.Pelicula
import com.dam2024m8uf1_tfinal.mmelis.PeliculaActivity
import com.dam2024m8uf1_tfinal.mmelis.PeliculaAdapter
import com.dam2024m8uf1_tfinal.mmelis.singleton.MovieRepository

class MovieManager(
    private val activity: Activity,
    private val recyclerView: RecyclerView,
    private val adapter: PeliculaAdapter
) {

    fun selectMovie(position: Int) {

    }

    fun editMovie(selectedPosition: Int) {
        if (selectedPosition != -1) {
            val selectedMovie = MovieRepository.getInstance().getMovies()[selectedPosition]
            MovieRepository.getInstance().currentMovie = selectedMovie  // Añadir esta línea
            val intent = Intent(activity, PeliculaActivity::class.java).apply {
                putExtra("isEdit", true)
                putExtra("position", selectedPosition)
            }
            activity.startActivity(intent)
        } else {
            Toast.makeText(activity, "Selecciona una película para editar.", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteMovie(selectedPosition: Int) {
        if (selectedPosition != -1) {
            val selectedMovie = MovieRepository.getInstance().getMovies()[selectedPosition]
            showDeleteConfirmationDialog(selectedMovie, selectedPosition)
        } else {
            Toast.makeText(activity, "Por favor, selecciona una película para eliminar.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDeleteConfirmationDialog(movie: Pelicula, position: Int) {
        val alertDialogBuilder = AlertDialog.Builder(activity)
        alertDialogBuilder.setTitle("Confirmar eliminación")
        alertDialogBuilder.setMessage("¿Estás seguro de que deseas eliminar '${movie.titulo}'?")
        alertDialogBuilder.setPositiveButton("Sí") { _, _ ->
            MovieRepository.getInstance().deleteMovie(movie)
            adapter.removeMovie(position)
            adapter.clearSelectedPosition()
        }
        alertDialogBuilder.setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
        alertDialogBuilder.create().show()
    }

    fun getSelectedPosition(): Int {
        return adapter.getSelectedPosition()
    }
}