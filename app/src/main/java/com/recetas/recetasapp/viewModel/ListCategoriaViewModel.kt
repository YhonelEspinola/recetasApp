package com.recetas.recetasapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.recetas.recetasapp.model.RecetaList


class ListCategoriaViewModel : ViewModel(){

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("recetas")
    val listRecetasLCat = MutableLiveData<List<RecetaList>>()
    val listRecetasMV = MutableLiveData<List<RecetaList>>()
    val listRecetasN = MutableLiveData<List<RecetaList>>()

    fun listRecetas(catego: String){
        val query = if(catego.isNullOrEmpty()){
            collection.get()} else{collection.whereEqualTo("categoria", catego).get()}

        query.addOnSuccessListener { querySnapshot ->
            var newList = arrayListOf<RecetaList>()
            for(document in querySnapshot){
                val data = document.data
                val nombre = data["nombre"] as? String ?: ""
                val categoria = data["categoria"] as? String ?: ""
                val descripcion = data["descripcion"] as? String ?: ""
                val ingredientes = data["ingredientes"] as? String ?: ""
                val preparacion = data["preparacion"] as? String ?: ""
                val imagenReceta = data["imagenReceta"] as? String?: ""
                val fecha = data["fecha"] as? Timestamp

                val modelo = RecetaList(nombre, categoria, descripcion,ingredientes,preparacion,imagenReceta,fecha)
                newList.add(modelo)
            }
            listRecetasLCat.value = newList
        }
    }

    fun listRecetasNuevas(){
        collection.orderBy("fecha", Query.Direction.DESCENDING)
            .limit(4)
            .get()
            .addOnSuccessListener { querySnapshot ->
                var newList = arrayListOf<RecetaList>()
                for(document in querySnapshot){
                    val data = document.data
                    val nombre = data["nombre"] as? String ?: ""
                    val categoria = data["categoria"] as? String ?: ""
                    val descripcion = data["descripcion"] as? String ?: ""
                    val ingredientes = data["ingredientes"] as? String ?: ""
                    val preparacion = data["preparacion"] as? String ?: ""
                    val imagenReceta = data["imagenReceta"] as? String?: ""
                    val fecha = data["fecha"] as? Timestamp

                    val modelo = RecetaList(nombre, categoria, descripcion, ingredientes, preparacion, imagenReceta,fecha)
                    newList.add(modelo)
                }
                listRecetasN.value = newList
            }
    }

    fun listProductosNuevos(){
        collection.orderBy("fecha", Query.Direction.DESCENDING)
            .limit(10)
            .get()
            .addOnSuccessListener { querySnapshot ->
                var newList = arrayListOf<RecetaList>()
                for(document in querySnapshot){
                    val data = document.data
                    val nombre = data["nombre"] as? String ?: ""
                    val categoria = data["categoria"] as? String ?: ""
                    val descripcion = data["descripcion"] as? String ?: ""
                    val ingredientes = data["ingredientes"] as? String ?: ""
                    val preparacion = data["preparacion"] as? String ?: ""
                    val imagenReceta = data["imagenReceta"] as? String?: ""
                    val fecha = data["fecha"] as? Timestamp

                    val modelo = RecetaList(nombre, categoria, descripcion, ingredientes, preparacion, imagenReceta,fecha)
                    newList.add(modelo)
                }
                listRecetasN.value = newList
            }
    }

}