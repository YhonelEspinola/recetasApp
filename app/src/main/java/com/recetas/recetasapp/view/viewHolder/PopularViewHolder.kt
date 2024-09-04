package com.recetas.recetasapp.view.viewHolder

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.recetas.recetasapp.R
import com.recetas.recetasapp.model.Receta
import com.recetas.recetasapp.model.RecetaList
import com.recetas.recetasapp.view.activity.DetalleRecetaActivity
import com.squareup.picasso.Picasso

class PopularViewHolder(inflater: LayoutInflater, viewGroup: ViewGroup):
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_receta_popular,viewGroup,false)) {

    private var textTitulo: TextView?=null
    private var textCategoria: TextView?=null
    private var textCodigo: TextView?=null
    private var imgReceta: ImageView?=null
    private var itemReceta: LinearLayout?=null
    private var textDescripcion: TextView?=null


    init{
        textTitulo = itemView.findViewById(R.id.textTitulo)
        textCategoria= itemView.findViewById(R.id.textCategoria)
        textCodigo= itemView.findViewById(R.id.textCodigo)
        imgReceta=itemView.findViewById(R.id.imgReceta)
        textDescripcion=itemView.findViewById(R.id.textDescripcion)

        itemReceta=itemView.findViewById(R.id.itemReceta)
    }

    fun bind(recetashome: Receta){
        textTitulo?.text=recetashome.nombre
        textCategoria?.text=recetashome.categoria
        textCodigo?.text=recetashome.codigo

        textDescripcion?.text=recetashome.descripcion
        imgReceta?.let{
            Picasso.get()
                .load(recetashome.imagenReceta)
                .placeholder(R.drawable.placeholder_comida)
                .into(it)
        }

        itemReceta?.setOnClickListener {
            val intent = Intent(itemView.context, DetalleRecetaActivity::class.java).apply {
                putExtra("codigo", recetashome.codigo)
                putExtra("nombre", recetashome.nombre)
                putExtra("categoria", recetashome.categoria)
                putExtra("descripcion", recetashome.descripcion)
                putExtra("imagenReceta", recetashome.imagenReceta)
                putExtra("ingredientes", recetashome.ingredientes)
                putExtra("preparacion", recetashome.preparacion)
            }
            itemView.context.startActivity(intent)
        }

    }
}