package com.recetas.recetasapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recetas.recetasapp.model.Receta
import com.recetas.recetasapp.view.viewHolder.FavoritosViewHolder

class FavoritosAdapter : RecyclerView.Adapter<FavoritosViewHolder> (){

    private var list = emptyList<Receta>()
    private var filterlist : List<Receta> = list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritosViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return FavoritosViewHolder(inflate, parent)
    }

    override fun getItemCount(): Int {
        return filterlist.size
    }

    override fun onBindViewHolder(holder: FavoritosViewHolder, position: Int) {
        val receta = filterlist[position]
        holder.bind(receta)
    }

    fun setDatos(datos: List<Receta>) {
        list = datos
        filterlist = datos
        notifyDataSetChanged()
    }

    fun filtrarDatos (query: String) {
        filterlist = if (query.isEmpty()) {
            list
        } else {
            list.filter {
                it.nombre.contains(query, ignoreCase = true) ||
                        it.ingredientes.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }

}