package com.recetas.recetasapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recetas.recetasapp.model.RecetaList
import com.recetas.recetasapp.view.viewHolder.RecetasInicioViewHolder

class RecetasInicioAdapter (): RecyclerView.Adapter<RecetasInicioViewHolder>() {
    private var list= emptyList<RecetaList>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecetasInicioViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RecetasInicioViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecetasInicioViewHolder, position: Int) {
        val masVistos = list[position]
        holder.bind(masVistos)
    }

    fun setDatos(datos: List<RecetaList>){
        this.list = datos
        notifyDataSetChanged()
    }
}