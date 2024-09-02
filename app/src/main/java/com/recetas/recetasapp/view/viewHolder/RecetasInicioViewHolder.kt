package com.recetas.recetasapp.view.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.recetas.recetasapp.R
import com.recetas.recetasapp.model.RecetaList
import com.squareup.picasso.Picasso

class RecetasInicioViewHolder (inflater: LayoutInflater, viewGroup: ViewGroup):
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_receta_inicio,viewGroup,false)) {

    private var textTitulo : TextView? = null
    private var textCategoria : TextView? = null
    private var textDescripcion : TextView? = null
    private var btnImagen : ImageView? = null
    private var item_receta : CardView? = null
    private var btnBotonPreparar : Button? = null


    init {
        textTitulo = itemView.findViewById(R.id.textTitulo)
        textCategoria = itemView.findViewById(R.id.textCategoria)
        textDescripcion = itemView.findViewById(R.id.textDescripcion)
        btnImagen = itemView.findViewById(R.id.btnImagen)

        item_receta = itemView.findViewById(R.id.item_receta)
        btnBotonPreparar = itemView.findViewById(R.id.btnBotonPreparar)
    }

    fun bind(recetaList: RecetaList) {
        textTitulo?.text = recetaList.nombre
        textDescripcion?.text = recetaList.descripcion
        textCategoria?.text = recetaList.categoria

        btnImagen?.let {
            Picasso.get()
                .load(recetaList.imagenReceta)
                .placeholder(R.drawable.placeholder_comida)
                .into(it)
        }

        btnBotonPreparar?.setOnClickListener {
            // Aquí se podría implementar el evento para preparar la receta
        }
    }
}