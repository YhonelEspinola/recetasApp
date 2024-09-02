package com.recetas.recetasapp.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.recetas.recetasapp.R
import com.squareup.picasso.Picasso

class DetalleMisRecetasActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_mis_recetas)

        val imagen = findViewById<ImageView>(R.id.btnImagenReceta)
        val titulo = findViewById<TextView>(R.id.textTitulo)
        val textCategoria = findViewById<TextView>(R.id.textCategoria)
        val textDescripcion = findViewById<TextView>(R.id.textDescripcion)
        val textIngredientes = findViewById<TextView>(R.id.textIngredientes)
        val textPreparacion = findViewById<TextView>(R.id.textPreparacion)
        val btnEditar = findViewById<Button>(R.id.btnBotonEditar)

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

        btnEditar.setOnClickListener {
            val intent = Intent(this, EditarRecetaActivity::class.java)
            intent.putExtra("codigo", codReceta)
            intent.putExtra("nombre", nombre)
            intent.putExtra("descripcion", descripcion)
            intent.putExtra("imagenReceta", imagenReceta)
            intent.putExtra("ingredientes", ingredientes)
            intent.putExtra("preparacion", preparacion)
            startActivity(intent)
        }

    }
}