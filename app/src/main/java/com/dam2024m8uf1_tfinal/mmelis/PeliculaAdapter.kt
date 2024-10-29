package com.dam2024m8uf1_tfinal.mmelis

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PeliculaAdapter(private val peliculas: MutableList<Pelicula>) :
    RecyclerView.Adapter<PeliculaAdapter.PeliculaViewHolder>() {

    private var selectedPosition = -1

    // Crea la vista del ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliculaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return PeliculaViewHolder(view)
    }

    // Asigna datos a cada elemento de la lista
    override fun onBindViewHolder(holder: PeliculaViewHolder, position: Int) {
        val pelicula = peliculas[position]
        holder.bind(pelicula)

        // Resalta el elemento seleccionado
        holder.itemView.setBackgroundColor(if (selectedPosition == holder.adapterPosition) 0xFFCCFFCC.toInt() else 0xFFFFFFFF.toInt())

        holder.itemView.setOnClickListener {
            // Usar holder.adapterPosition para obtener la posición actual
            selectedPosition = holder.adapterPosition
            notifyDataSetChanged() // Notifica que los datos han cambiado para actualizar la vista
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

    // Clase interna para manejar cada elemento de la lista
    inner class PeliculaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(android.R.id.text1)

        fun bind(pelicula: Pelicula) {
            textView.text = pelicula.titulo // Muestra solo el título en este caso
        }
    }
}
