package com.recetas.recetasapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PerfilViewModel : ViewModel() {

    private val  _nombreU = MutableLiveData<String>()
    private val  _correo = MutableLiveData<String>()
    private val _newPass = MutableLiveData<Boolean>()

    val nombreU : LiveData<String> get() = _nombreU
    val correoU : LiveData<String> get() = _correo
    val newPass : LiveData<Boolean> get() = _newPass

    val db = FirebaseFirestore.getInstance().collection("usuarios")

    fun readInformation(){
        val userId = FirebaseAuth.getInstance().uid ?: return

        db.document(userId).get()
            .addOnSuccessListener { document ->
                val nombre = document.getString("usuario")
                val correo = document.getString("correo")

                _nombreU.value = nombre
                _correo.value = correo
            }
    }

    fun updateInformation(nombre: String){
        val userId = FirebaseAuth.getInstance().uid?: return

        val userUpdates = hashMapOf<String, Any>("usuario" to nombre)

        db.document(userId).update(userUpdates)
            .addOnSuccessListener {
                _nombreU.value = nombre
            }
            .addOnFailureListener {exception ->}
    }

    fun updatePassword(currentContraseña : String, newContraseña : String){
        val user = FirebaseAuth.getInstance().currentUser
        if(user!=null && user.email!=null){
            val credential = EmailAuthProvider.getCredential(user.email!!, currentContraseña)

            user.reauthenticate(credential)
               .addOnCompleteListener { reauthTask ->
                    if (reauthTask.isSuccessful) {
                        user.updatePassword(newContraseña)
                           .addOnCompleteListener { updateTask ->
                               _newPass.value = updateTask.isSuccessful
                           }
                    } else{
                        _newPass.value = false
                    }
                }
        }else{
            _newPass.value = false
        }
    }

}