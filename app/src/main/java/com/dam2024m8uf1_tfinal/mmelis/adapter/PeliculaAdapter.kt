package com.dam2024m8uf1_tfinal.mmelis.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dam2024m8uf1_tfinal.mmelis.`object`.Pelicula
import com.dam2024m8uf1_tfinal.mmelis.R

class PeliculaAdapter(
    private var peliculas: MutableList<Pelicula>,
    private val onMovieClick: (Int) -> Unit // Callback para manejar el clic
) : RecyclerView.Adapter<PeliculaAdapter.PeliculaViewHolder>() {

    private var selectedPosition = -1

    // Crea la vista del ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliculaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pelicula, parent, false)
        return PeliculaViewHolder(view)
    }

    // Asigna datos a cada elemento de la lista
    override fun onBindViewHolder(holder: PeliculaViewHolder, position: Int) {
        val pelicula = peliculas[position]
        holder.bind(pelicula)

        // Resalta el elemento seleccionado
        holder.itemView.setBackgroundColor(if (selectedPosition == holder.adapterPosition) 0xFFCCFFCC.toInt() else 0xFFFFFFFF.toInt())

        holder.itemView.setOnClickListener {
            selectedPosition = holder.adapterPosition
            notifyDataSetChanged() // Actualiza la vista
            onMovieClick(holder.adapterPosition) // Llama al callback con la posición seleccionada
        }
    }

    // Devuelve el número de elementos en la lista
    override fun getItemCount(): Int {
        return peliculas.size
    }

    // Método para obtener la posición seleccionada
    fun getSelectedPosition(): Int {
        return selectedPosition
    }

    fun removeMovie(position: Int) {
        if (position in peliculas.indices) {
            peliculas.removeAt(position) // Elimina la película de la lista interna
            notifyItemRemoved(position) // Notifica al adaptador sobre la eliminación
            notifyItemRangeChanged(position, itemCount) // Actualiza el rango de elementos
        }
    }

    fun clearSelectedPosition() {
        selectedPosition = -1
        notifyDataSetChanged() // Refresca la vista para quitar el resaltado
    }

    // Método para actualizar la lista de películas
    fun updateMovies(newMovies: MutableList<Pelicula>) {
        peliculas = newMovies
        notifyDataSetChanged() // Notifica que los datos han cambiado para refrescar la vista
    }

    // Clase interna para manejar cada elemento de la lista
    inner class PeliculaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textTitulo: TextView = itemView.findViewById(R.id.textTitulo)

        fun bind(pelicula: Pelicula) {
            Log.d("PeliculaAdapter", "Título de la película: ${pelicula.titulo}")
            textTitulo.text = pelicula.titulo
        }
    }
}
