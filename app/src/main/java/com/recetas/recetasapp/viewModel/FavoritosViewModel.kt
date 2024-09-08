package com.recetas.recetasapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.recetas.recetasapp.model.Receta
import com.google.firebase.Timestamp

class FavoritosViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    val listRecetas = MutableLiveData<List<Receta>>()
    val recetaverificada = MutableLiveData<Boolean>()
    val currentUser = FirebaseAuth.getInstance().currentUser
    val uid = currentUser?.uid
    private val TAG = "FavoritosViewModel"

    private val _guardarFavoritoStatus = MutableLiveData<Boolean>()
    private val _quitarFavoritoStatus = MutableLiveData<Boolean>()

    val guardarFavoritoStatus: LiveData<Boolean> get() = _guardarFavoritoStatus
    val quitarFavoritoStatus: LiveData<Boolean> get() = _quitarFavoritoStatus

    fun listarRecetas(){
        if (uid != null){
            db.collection("favoritos")
                .whereEqualTo("uid", uid)
                .get()
                .addOnSuccessListener { documents ->
                    val eids = documents.map {it.getString("rid")}
                    if (eids.isNotEmpty()){
                        db.collection("recetas")
                            .whereIn(FieldPath.documentId(), eids)
                            .get()
                            .addOnSuccessListener { querySnapshot ->
                                val newList = arrayListOf<Receta>()
                                for (document in querySnapshot){
                                    val data = document.data
                                    val nombre = data["nombre"] as? String?: ""
                                    val categoria = data["categoria"] as? String?: ""
                                    val descripcion = data["descripcion"] as? String?: ""
                                    val ingredientes = data["ingredientes"] as? String?: ""
                                    val preparacion = data["preparacion"] as? String?: ""
                                    val imagenReceta = data["imagenReceta"] as? String?: ""
                                    val fecha = document.getTimestamp("fecha")
                                    val codigo = document.id

                                    val modelo = Receta(nombre, categoria, descripcion, ingredientes, preparacion,imagenReceta,fecha,codigo)
                                    newList.add(modelo)
                                    Log.d(TAG, "  recetas para : $modelo")
                                }
                                listRecetas.value = newList
                                Log.d(TAG, "Se han encontrado  recetas para el UID: $uid")
                            }
                            .addOnFailureListener{ exception ->
                                listRecetas.value = emptyList()
                            }
                    }else{
                        listRecetas.value = emptyList()
                    }
                }
        }
    }


    fun GuardarReceta(rid : String){
        val data = hashMapOf(
            "rid" to rid,
            "uid" to uid
        )
        db.collection("favoritos")
            .add(data)
            .addOnSuccessListener {
                _guardarFavoritoStatus.value = true
            }.addOnFailureListener{
                _guardarFavoritoStatus.value = false
            }
    }

    fun VerificarReceta(rid : String){
        db.collection("favoritos")
           .whereEqualTo("rid", rid)
           .whereEqualTo("uid", uid)
           .get()
           .addOnSuccessListener { documents ->
                if(documents.isEmpty){
                    recetaverificada.value = false
                }else{
                    recetaverificada.value = true
                }
            }
           .addOnFailureListener { exception ->
               recetaverificada.value = false
            }
    }

    fun QuitarEvento(rid : String){
        if (uid != null){
            db.collection("favoritos")
               .whereEqualTo("rid", rid)
               .whereEqualTo("uid", uid)
               .get()
               .addOnSuccessListener { documents ->
                    for (document in documents){
                        db.collection("favoritos").document(document.id).delete()
                           .addOnSuccessListener {
                                _quitarFavoritoStatus.value = true
                            }
                           .addOnFailureListener{ exception ->
                                _quitarFavoritoStatus.value = false
                            }
                    }
                }
               .addOnFailureListener{
                    _quitarFavoritoStatus.value = false
                }
        }
    }

}