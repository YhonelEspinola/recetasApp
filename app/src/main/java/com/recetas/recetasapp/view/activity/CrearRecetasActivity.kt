package com.recetas.recetasapp.view.activity

import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.recetas.recetasapp.R
import com.recetas.recetasapp.viewModel.RecetaViewModel

class CrearRecetasActivity : AppCompatActivity() {

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){ uri ->
        if(uri != null){
            imgReceta.setImageURI(uri)
            imgUriReceta = uri
        } else {
            Log.i("CrearRecetasActivity","Imagen no seleccionada")
        }
    }
    lateinit var imgReceta: ImageView
    private var imgUriReceta: Uri? = null
    private lateinit var progressDialog: ProgressDialog
    private lateinit var recetaViewModel: RecetaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_receta)

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Creando receta...")
        progressDialog.setCancelable(false)

        recetaViewModel = ViewModelProvider(this)[RecetaViewModel::class.java]

        imgReceta = findViewById(R.id.imgReceta)
        val btnImgReceta = findViewById<Button>(R.id.btnImagen)
        val btnCrearReceta = findViewById<Button>(R.id.btnCrearReceta)
        btnImgReceta.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        val edtNombre = findViewById<TextInputEditText>(R.id.edtTitulo)
        val edtCategoria = findViewById<AutoCompleteTextView>(R.id.edtCategoria)
        val edtDescripcion = findViewById<TextInputEditText>(R.id.edtDescripcion)
        val edtIngredientes = findViewById<EditText>(R.id.TextIngredientes)
        val edtPreparacion = findViewById<EditText>(R.id.TextPreparacion)

        val items = listOf("Entradas","Desayunos","Almuerzos","Cenas","Postres")
        val adapter = ArrayAdapter(this, R.layout.item_dropdown, items)
        edtCategoria.setAdapter(adapter)

        btnCrearReceta.setOnClickListener {
            val errorMessage = recetaViewModel.validar(
                edtNombre.text.toString(),
                edtCategoria.text.toString(),
                edtDescripcion.text.toString(),
                edtIngredientes.text.toString(),
                edtPreparacion.text.toString(),
                imgUriReceta
            )
            if(errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            } else {
                progressDialog.show()
                recetaViewModel.crearReceta(
                    edtNombre.text.toString(),
                    edtCategoria.text.toString(),
                    edtDescripcion.text.toString(),
                    edtIngredientes.text.toString(),
                    edtPreparacion.text.toString(),
                    imgUriReceta
                )
            }
        }

       recetaViewModel.recetaCreada.observe(this) { creada ->
            if (creada) {
                progressDialog.dismiss()
                Toast.makeText(this, "Receta creada exitosamente", Toast.LENGTH_SHORT).show()
            }
        }

        recetaViewModel.errorMessage.observe(this) { message ->
            if (message != null) {
                progressDialog.dismiss()
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}