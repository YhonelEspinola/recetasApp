package com.recetas.recetasapp.view.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.recetas.recetasapp.R
import com.recetas.recetasapp.model.Categoria
import com.recetas.recetasapp.model.OnCategoriaClickListener

class CategoriaViewHolder(inflater: LayoutInflater, viewGroup: ViewGroup): RecyclerView.ViewHolder(inflater.inflate(
    R.layout.item_categorias, viewGroup, false)) {

    var btnCategorias: Button? = null

    init {
        btnCategorias = itemView.findViewById(R.id.btnCategoria)
    }

    fun bind(categorias: Categoria, listener: OnCategoriaClickListener) {
        btnCategorias?.text = categorias.categoria

        btnCategorias?.setOnClickListener {
            listener.onCategoriaClick(categorias)
        }
    }
}
