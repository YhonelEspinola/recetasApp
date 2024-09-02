package com.recetas.recetasapp.model

import com.google.firebase.Timestamp

data class RecetaList(val nombre: String, val categoria: String,
                      val descripcion: String, val ingredientes: String,
                      val preparacion: String, val imagenReceta: String,val fecha: Timestamp?
)
