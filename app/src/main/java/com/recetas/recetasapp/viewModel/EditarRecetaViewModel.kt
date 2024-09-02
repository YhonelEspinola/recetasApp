package com.recetas.recetasapp.viewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class EditarRecetaViewModel : ViewModel() {

    private val storage = Firebase.storage
    private val db = FirebaseFirestore.getInstance()

    private val _recetaUpdateStatus = MutableLiveData<Boolean>()

    val recetaUpdateStatus: LiveData<Boolean> get() = _recetaUpdateStatus

    fun uploadImageToFirebaseStorage(imageUri: Uri, path: String, onSuccess: (String) -> Unit) {
        val storageRef = storage.reference.child(path)
        val uploadTask = storageRef.putFile(imageUri)

        uploadTask.addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                onSuccess(uri.toString())
            }
        }.addOnFailureListener{
            Log.e("FirebaseStorage", "Image upload failed: ${it.message}")
        }

    }

    fun updateRecetaInFirestore(
        codigo: String,
        nombre: String,
        categoria: String,
        descripcion: String,
        ingredientes: String,
        preparacion: String,
        imagenReceta: String
    ){
        val data = HashMap<String, Any>()
        data["nombre"] = nombre
        data["categoria"] = categoria
        data["descripcion"] = descripcion
        data["ingredientes"] = ingredientes
        data["preparacion"] = preparacion
        data["imagenReceta"] = imagenReceta

        db.collection("recetas").document(codigo)
            .update(data)
            .addOnSuccessListener {
                _recetaUpdateStatus.value = true
            }.addOnFailureListener{
                _recetaUpdateStatus.value = false
                Log.e("Firestore", "Error al actualizar la receta: ${it.message}")
            }


    }


}