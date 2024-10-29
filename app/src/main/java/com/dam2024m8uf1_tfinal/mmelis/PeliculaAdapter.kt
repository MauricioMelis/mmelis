package com.dam2024m8uf1_tfinal.mmelis

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PeliculaAdapter(private var peliculas: MutableList<Pelicula>) :
    RecyclerView.Adapter<PeliculaAdapter.PeliculaViewHolder>() {

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
