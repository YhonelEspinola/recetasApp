package com.recetas.recetasapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recetas.recetasapp.model.Receta
import com.recetas.recetasapp.view.viewHolder.ListaRecetaViewHolder
import com.recetas.recetasapp.view.viewHolder.MisRecetasViewHolder

class MisRecetasAdapter: RecyclerView.Adapter<MisRecetasViewHolder>() {
    private var list = emptyList<Receta>()
    private var listFilter : List<Receta> = list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MisRecetasViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MisRecetasViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return listFilter.size
    }

    override fun onBindViewHolder(holder: MisRecetasViewHolder, position: Int) {
        val receta = listFilter[position]
        holder.bind(receta)
    }

    fun setDatos(datos: List<Receta>) {
        list = datos
        listFilter = datos
        notifyDataSetChanged()
    }

    fun filtrarDatos (query: String) {
        listFilter = if (query.isEmpty()) {
            list
        }else {
            list.filter {
                it.nombre.contains(query, ignoreCase = true) ||
                        it.descripcion.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }
}