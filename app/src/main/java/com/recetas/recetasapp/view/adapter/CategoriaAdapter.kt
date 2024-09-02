package com.recetas.recetasapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.recetas.recetasapp.R
import com.recetas.recetasapp.model.Categoria
import com.recetas.recetasapp.model.OnCategoriaClickListener
import com.recetas.recetasapp.view.viewHolder.CategoriaViewHolder

class CategoriaAdapter(val list : List<Categoria>, private val listener: OnCategoriaClickListener) : RecyclerView.Adapter<CategoriaViewHolder>() {

    private var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CategoriaViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        val categoria = list[position]
        var pos:Int = position
        holder.bind(categoria, listener)

        if(pos==selectedPosition){
            holder.btnCategorias?.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.bottom))
            holder.btnCategorias?.layoutParams?.height = holder.itemView.context.resources.getDimensionPixelSize(R.dimen.categoria_select)
        }else{
            holder.btnCategorias?.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.color_boton_categ))
            holder.btnCategorias?.layoutParams?.height = holder.itemView.context.resources.getDimensionPixelSize(R.dimen.categoria_normal)
        }
        holder.btnCategorias?.setOnClickListener{
            val previousPosition = selectedPosition
            selectedPosition = pos
            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)
            listener.onCategoriaClick(categoria)
        }

    }


}