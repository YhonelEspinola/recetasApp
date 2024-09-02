package com.recetas.recetasapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recetas.recetasapp.model.RecetaList
import com.recetas.recetasapp.view.viewHolder.ListCategoriaViewHolder

class BuscarAdapter() : RecyclerView.Adapter<ListCategoriaViewHolder>() {

    private var recetas = emptyList<RecetaList>()
    private var recetasFiltradas : List<RecetaList> = recetas

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCategoriaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ListCategoriaViewHolder(inflater, parent)
    }


    override fun getItemCount(): Int {
        return recetasFiltradas.size
    }

    override fun onBindViewHolder(holder: ListCategoriaViewHolder, position: Int) {
        holder.bind(recetasFiltradas[position])
    }
    fun setDatos(recetas: List<RecetaList>) {
        this.recetas = recetas
        this.recetasFiltradas = recetas
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        recetasFiltradas = if (query.isEmpty()) {
            recetas
        } else {
            recetas.filter {
                it.nombre.contains(query, ignoreCase = true) ||
                        it.ingredientes.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }
}