package com.recetas.recetasapp.model

data class Receta(val nombre: String, val categoria: String,
                  val descripcion: String, val ingredientes: String,
                  val preparacion: String, val imagenReceta: String,
    val codigo:String)
