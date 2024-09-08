package com.recetas.recetasapp.view.viewHolder

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.recetas.recetasapp.R
import com.recetas.recetasapp.model.Receta
import com.recetas.recetasapp.view.activity.DetalleRecetaActivity
import com.squareup.picasso.Picasso

class FavoritosViewHolder (inflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.item_favoritos, viewGroup, false)) {

    private var imagen: ImageView? = null
    private var titulo: TextView? = null
    private var carView: CardView? = null

    init {
        imagen = itemView.findViewById(R.id.btnImagen)
        titulo = itemView.findViewById(R.id.textTitulo)
        carView = itemView.findViewById(R.id.cardView)
    }

    fun bind(receta: Receta) {

        imagen?.let{
            Picasso.get()
               .load(receta.imagenReceta)
               .placeholder(R.drawable.placeholder_comida)
               .into(it)
        }

        titulo?.text = receta.nombre

        carView?.setOnClickListener{
            val intent = Intent(itemView.context, DetalleRecetaActivity::class.java).apply {
                putExtra("codigo", receta.codigo)
                putExtra("nombre", receta.nombre)
                putExtra("categoria", receta.categoria)
                putExtra("descripcion", receta.descripcion)
                putExtra("imagenReceta", receta.imagenReceta)
                putExtra("ingredientes", receta.ingredientes)
                putExtra("preparacion", receta.preparacion)
                putExtra("fecha", receta.fecha)
            }
            itemView.context.startActivity(intent)
        }
    }

}