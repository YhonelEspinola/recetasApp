package com.recetas.recetasapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recetas.recetasapp.model.Receta
import com.recetas.recetasapp.view.viewHolder.PopularViewHolder

class PopularAdapter : RecyclerView.Adapter<PopularViewHolder>() {
    private var list= emptyList<Receta>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PopularViewHolder(inflater,parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val popular=list[position]
        holder.bind(popular)
    }

    fun setDatos(datos: List<Receta>) {
        this.list = datos
        notifyDataSetChanged()
    }
}