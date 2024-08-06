package com.recetas.recetasapp.view.viewHolder

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.recetas.recetasapp.R
import com.recetas.recetasapp.model.Receta
import com.recetas.recetasapp.view.activity.DetalleMisRecetasActivity
import com.squareup.picasso.Picasso

class MisRecetasViewHolder(inflater: LayoutInflater, viewGroup: ViewGroup) :
RecyclerView.ViewHolder(inflater.inflate(R.layout.item_mis_recetas, viewGroup)){

    private var imgReceta : ImageView?= null
    private var txtTitulo : TextView?= null
    private var textCategoria : TextView?= null
    private var txtDescripcion : TextView?= null
    private var btnVerMas : Button?= null

    init {
        imgReceta = itemView.findViewById(R.id.imgReceta)
        txtTitulo = itemView.findViewById(R.id.textTitulo)
        textCategoria = itemView.findViewById(R.id.textCategoria)
        txtDescripcion = itemView.findViewById(R.id.textDescripcion)
        btnVerMas = itemView.findViewById(R.id.btnVerMas)
    }

    fun bind(receta: Receta){
        imgReceta?.let {
            Picasso.get()
                .load(receta.imagenReceta)
                .placeholder(R.drawable.placeholder_comida)
                .into(it)
        }
        txtTitulo?.text=receta.nombre
        textCategoria?.text=receta.categoria
        txtDescripcion?.text=receta.descripcion
        btnVerMas?.setOnClickListener {
            val intent = Intent(itemView.context, DetalleMisRecetasActivity::class.java).apply {
                putExtra("nombre", receta.nombre)
                putExtra("categoria", receta.categoria)
                putExtra("descripcion", receta.descripcion)
                putExtra("imagenReceta", receta.imagenReceta)
                putExtra("ingredientes", receta.ingredientes)
                putExtra("preparacion", receta.preparacion)
            }
            itemView.context.startActivity(intent)
        }


    }

}