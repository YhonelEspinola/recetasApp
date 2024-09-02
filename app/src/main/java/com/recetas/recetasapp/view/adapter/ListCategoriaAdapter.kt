package com.recetas.recetasapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recetas.recetasapp.model.RecetaList
import com.recetas.recetasapp.view.viewHolder.ListCategoriaViewHolder

class ListCategoriaAdapter (): RecyclerView.Adapter<ListCategoriaViewHolder>() {
    private var list= emptyList<RecetaList>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCategoriaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ListCategoriaViewHolder(inflater,parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ListCategoriaViewHolder, position: Int) {
        val recetasList = list[position]
        holder.bind(recetasList)
    }

    fun setDatos(datos: List<RecetaList>) {
        list = datos
        notifyDataSetChanged()
    }
}