package com.recetas.recetasapp.viewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.storage
import com.recetas.recetasapp.model.Receta
import java.util.UUID

class RecetaViewModel : ViewModel() {

    private val store = Firebase.storage
    private val db = FirebaseFirestore.getInstance()
    val recetaCreada = MutableLiveData<Boolean>()
    val listaReceta = MutableLiveData<List<Receta>>()
    val errorMessage = MutableLiveData<String>()
    private val TAG = "RecetaViewModel"




    fun crearReceta(
        nombre: String,
        categoria: String,
        descripcion: String,
        ingredientes: String,
        preparacion: String,
        imagenReceta: Uri?,

    ) {
        val user = FirebaseAuth.getInstance().currentUser

        if (user == null) {
            errorMessage.value = "No hay usuario autenticado"
            return
        }

        if (imagenReceta == null) {
            errorMessage.value = "La imagen de la receta está vacía"
            return
        }

        val imageFileName = UUID.randomUUID().toString()
        val refReceta = store.reference.child(imageFileName)
        val uploadTaskReceta = refReceta.putFile(imagenReceta)

        uploadTaskReceta.continueWithTask { refReceta.downloadUrl }
            .addOnSuccessListener { uri ->
                val imageUrl = uri.toString()

                val data = hashMapOf(
                    "nombre" to nombre,
                    "categoria" to categoria,
                    "descripcion" to descripcion,
                    "ingredientes" to ingredientes,
                    "preparacion" to preparacion,
                    "imagenReceta" to imageUrl,
                    "fecha" to Timestamp.now(),
                    "uid" to user.uid
                )
                db.collection("recetas")
                    .add(data)
                    .addOnSuccessListener {
                        recetaCreada.value = true
                    }
                    .addOnFailureListener { e ->
                        errorMessage.value = "Error al guardar la receta: ${e.message}"
                    }
            }
            .addOnFailureListener {
                errorMessage.value = "Error al subir la imagen: ${it.message}"
            }
    }



    fun validar(nombre: String,
                categoria:String,
                descripcion: String,
                ingredientes: String,
                preparacion: String,
                imagenReceta: Uri?) : String? {

        return when {
            imagenReceta == null -> "La imagen de la receta está vacía"
            nombre.isEmpty() -> "El nombre de la receta está vacío"
            categoria.isEmpty() -> "Elige una categoria"
            descripcion.isEmpty() -> "Ingrese una descripcion"
            ingredientes.isEmpty() -> "Ingrese los ingredientes"
            preparacion.isEmpty() -> "Ingrese la preparación"

            else -> null
        }
    }

    fun listarRecetasDelUsuario(userId: String) {
        Log.d(TAG, "Buscando recetas para el UID: $userId")

        db.collection("recetas")
            .whereEqualTo("uid", userId) // Filtramos por el UID del usuario
            .orderBy("nombre", com.google.firebase.firestore.Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val recetasList = ArrayList<Receta>()
                for (document in querySnapshot) {
                    val data = document.data
                    val nombre = data["nombre"] as? String ?: ""
                    val categoria = data["categoria"] as? String ?: ""
                    val descripcion = data["descripcion"] as? String ?: ""
                    val ingredientes = data["ingredientes"] as? String ?: ""
                    val preparacion = data["preparacion"] as? String ?: ""
                    val imagenReceta = data["imagenReceta"] as? String ?: ""
                    val fecha = document.getTimestamp("fecha")
                    val codigo = document.id

                    val receta = Receta(nombre, categoria, descripcion, ingredientes, preparacion, imagenReceta, fecha, codigo)
                    recetasList.add(receta)
                }

                listaReceta.value = recetasList
                Log.d(TAG, "Se han encontrado ${recetasList.size} recetas para el UID: $userId")
            }
            .addOnFailureListener { exception ->
                errorMessage.value = "Error al cargar las recetas: ${exception.message}"
                Log.e(TAG, "Error al cargar las recetas: ${exception.message}")
            }
    }

    fun listarRecetasPorFiltros(categoria: String){

        var query:Query = db.collection("recetas")
        if (!categoria.isNullOrEmpty()){
            query = query.whereEqualTo("categoria", categoria)
        }

        query.orderBy("nombre", com.google.firebase.firestore.Query.Direction.ASCENDING)
        query.get()
        .addOnSuccessListener { querySnapshots ->
            var newList = arrayListOf<Receta>()
            for (document in querySnapshots){
                val data = document.data
                val nombre = data["nombre"] as? String?: ""
                val categoria = data["categoria"] as? String?: ""
                val descripcion = data["descripcion"] as? String?: ""
                val ingredientes = data["ingredientes"] as? String?: ""
                val preparacion = data["preparacion"] as? String?: ""
                val imagenReceta = data["imagenReceta"] as? String?: ""
                val fecha = document.getTimestamp("fecha")
                val codigo = document.id

                val modelo = Receta(nombre, categoria, descripcion, ingredientes, preparacion, imagenReceta,fecha, codigo)
                newList.add(modelo)
            }
            listaReceta.value = newList
        }
    }
}