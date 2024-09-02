package com.recetas.recetasapp.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.recetas.recetasapp.R
import com.recetas.recetasapp.viewModel.EditarRecetaViewModel
import com.squareup.picasso.Picasso

class EditarRecetaActivity : AppCompatActivity() {

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){ uri ->
        if(uri != null){
            imgReceta.setImageURI(uri)
            imgUriReceta = uri
        }else{
            Log.i("ari", "Img no selecionada")
        }
    }

    private var imgUriReceta: Uri? = null

    lateinit var btnImagen:Button
    lateinit var imgReceta: ImageView
    lateinit var edtTitulo: TextInputEditText
    lateinit var edtCategoria: AutoCompleteTextView
    lateinit var edtDescripcion: TextInputEditText
    lateinit var textIngredientes : EditText
    lateinit var textPreparacion : EditText
    lateinit var textCodigo : TextView
    private lateinit var btnEdit : Button
    private lateinit var editarRecetaViewModel : EditarRecetaViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_receta)

        editarRecetaViewModel = ViewModelProvider(this).get(EditarRecetaViewModel::class.java)

        btnImagen = findViewById(R.id.btnImagen)
        imgReceta = findViewById(R.id.imgReceta)
        edtTitulo = findViewById(R.id.edtTitulo)
        edtCategoria = findViewById(R.id.edtCategoria)
        edtDescripcion = findViewById(R.id.edtDescripcion)
        textIngredientes = findViewById(R.id.TextIngredientes)
        textPreparacion = findViewById(R.id.TextPreparacion)
        textCodigo = findViewById(R.id.textCodigo)
        btnEdit = findViewById(R.id.btnEditProductos)

        val itemsCat = listOf("Comida rapida","Desayuno","Almuerzo","Postre","Merienda","Cena")
        val adapterC = ArrayAdapter(this, R.layout.item_dropdown, itemsCat)
        edtCategoria.setAdapter(adapterC)

        btnImagen.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        val btnButtonAtras = findViewById<ImageView>(R.id.retroceder)
        btnButtonAtras.setOnClickListener{
            finish()
        }

        val intent = intent

        var nombre = intent.getStringExtra("nombre")
        var categoria = intent.getStringExtra("categoria")
        var descripcion = intent.getStringExtra("descripcion")
        var imagenReceta = intent.getStringExtra("imagenReceta")
        var ingredientes = intent.getStringExtra("ingredientes")
        var preparacion = intent.getStringExtra("preparacion")
        val codReceta = intent.getStringExtra("codigo")

        edtTitulo.setText(nombre)
        edtCategoria.setText(categoria, false)
        edtDescripcion.setText(descripcion)
        Picasso.get().load(imagenReceta).into(imgReceta)
        textIngredientes.setText(ingredientes)
        textPreparacion.setText(preparacion)
        textCodigo.text = codReceta

        editarRecetaViewModel.recetaUpdateStatus.observe(this){isSuccess ->
            if(isSuccess){
                Toast.makeText(this,"Receta actualizada correctamente",Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ListarMisRecetaActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this,"Error al actualizar la receta",Toast.LENGTH_SHORT).show()
            }
        }

        btnEdit.setOnClickListener{
            if(validateFiel()){
                if(imgUriReceta != null){
                    imgUriReceta?.let{uriReceta ->
                        editarRecetaViewModel.uploadImageToFirebaseStorage(uriReceta, "recetas/$codReceta/recetaImage.jpg") { imagenReceta ->
                            updateRecetaInFirestore(imagenReceta)
                        }
                    } ?: run{
                        updateRecetaInFirestore(imagenReceta ?: "")
                    }
                }else{
                    updateRecetaInFirestore(imagenReceta ?:"")
                }
            }else{
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun validateFiel() : Boolean {
        return when{
            edtTitulo.text.isNullOrEmpty() -> {
                edtTitulo.error = "Ingrese un título"
                false
            }
            edtDescripcion.text.isNullOrEmpty() -> {
                edtDescripcion.error = "Ingrese una descripción"
                false
            }
            textIngredientes.text.toString().trim().isEmpty() -> {
                textIngredientes.error = "Ingrese ingredientes"
                false
            }
            textPreparacion.text.toString().trim().isEmpty() -> {
                textPreparacion.error = "Ingrese la preparación"
                false
            }
            else -> true
        }

    }

    private fun updateRecetaInFirestore(imagenReceta: String){
        val nombre = edtTitulo.text.toString()
        val categoria = edtCategoria.text.toString()
        val descripcion = edtDescripcion.text.toString()
        val ingredientes = textIngredientes.text.toString()
        val preparacion = textPreparacion.text.toString()
        val codigo = textCodigo.text.toString()

        editarRecetaViewModel.updateRecetaInFirestore(
            codigo,
            nombre,
            categoria,
            descripcion,
            ingredientes,
            preparacion,
            imagenReceta
        )

    }
}