package com.recetas.recetasapp.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.recetas.recetasapp.R
import com.recetas.recetasapp.viewModel.FavoritosViewModel
import com.squareup.picasso.Picasso

class DetalleRecetaActivity : AppCompatActivity() {

    private lateinit var favoritosViewModel : FavoritosViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_receta)

        favoritosViewModel = ViewModelProvider(this)[FavoritosViewModel::class.java]

        var isSaved = false
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val imagen = findViewById<ImageView>(R.id.btnImagenReceta)
        val titulo = findViewById<TextView>(R.id.textTitulo)
        val textCategoria = findViewById<TextView>(R.id.textCategoria)
        val textDescripcion = findViewById<TextView>(R.id.textDescripcion)
        val textIngredientes = findViewById<TextView>(R.id.textIngredientes)
        val textPreparacion = findViewById<TextView>(R.id.textPreparacion)
        val guardar = findViewById<ImageView>(R.id.favoritos)


        val intent = intent

        val codReceta = intent.getStringExtra("codigo")
        if(codReceta == null){
            Toast.makeText(this,"Codigo de la receta no encontrado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        val nombre = intent.getStringExtra("nombre")
        val categoria = intent.getStringExtra("categoria")
        val descripcion = intent.getStringExtra("descripcion")
        val imagenReceta = intent.getStringExtra("imagenReceta")
        val ingredientes = intent.getStringExtra("ingredientes")
        val preparacion = intent.getStringExtra("preparacion")

        titulo.text = nombre
        textCategoria.text = categoria
        textDescripcion.text = descripcion
        textIngredientes.text = ingredientes
        textPreparacion.text = preparacion
        Picasso.get().load(imagenReceta).into(imagen)

        favoritosViewModel.VerificarReceta(codReceta.toString())

        favoritosViewModel.recetaverificada.observe(this){
            if(it){
                isSaved = true
                guardar.setImageResource(R.drawable.icon_guardar_relleno)
            }
        }

        guardar.setOnClickListener{
            progressBar.visibility = View.VISIBLE
            guardar.visibility = View.GONE
            if(isSaved){
                favoritosViewModel.QuitarEvento(codReceta.toString())
            }else{
                favoritosViewModel.GuardarReceta(codReceta.toString())
            }
            isSaved =!isSaved
        }

        favoritosViewModel.guardarFavoritoStatus.observe(this){succes ->
            progressBar.visibility = View.GONE
            guardar.visibility = View.VISIBLE
            if(succes){
                guardar.setImageResource(R.drawable.icon_guardar_relleno)
            }else{
                Toast.makeText(this,"Error al guardar la receta", Toast.LENGTH_SHORT).show()
            }
        }

        favoritosViewModel.quitarFavoritoStatus.observe(this){success ->
            progressBar.visibility = View.GONE
            guardar.visibility = View.VISIBLE
            if(success){
                guardar.setImageResource(R.drawable.icon_guardar)
            }else{
                Toast.makeText(this,"Error al quitar la receta", Toast.LENGTH_SHORT).show()
            }
        }


    }
}