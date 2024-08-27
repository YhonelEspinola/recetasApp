package com.recetas.recetasapp.viewModel

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.recetas.recetasapp.model.Constantes

class RegistrarUsuarioViewModel: ViewModel() {

    private lateinit var firesbaseAuth: FirebaseAuth

    val registroStatus = MutableLiveData<Boolean>()
    val usuarioError = MutableLiveData<String>()
    val correoError = MutableLiveData<String>()
    val passwordError = MutableLiveData<String>()
    val cPasswordError = MutableLiveData<String>()
    val mensajeError = MutableLiveData<String>()

    fun validarInformacion(usuario: String, correo: String, password: String, cPassword: String) {
        if (usuario.isEmpty()){
            usuarioError.value = "Ingrese un Usuario"
            registroStatus.value = false
        }
        else if (correo.isEmpty()){
            correoError.value = "Ingrese un Correo"
            registroStatus.value = false
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            correoError.value = "Ingrese un Correo Valido"
            registroStatus.value = false
        }
        else if (password.isEmpty()){
            passwordError.value = "Ingrese una Contraseña"
            registroStatus.value = false
        }
        else if (password.length < 6){
            passwordError.value = "Contraseña demasiado corta, debe tener al menos 6 caracteres"
            registroStatus.value = false
        }
        else if (cPassword.isEmpty()){
            cPasswordError.value = "Repita la Contraseña"
            registroStatus.value = false
        }
        else if (password != cPassword){
            cPasswordError.value = "Las Contraseñas no Coinciden"
            registroStatus.value = false
        }
        else{
            registrarUsuario(usuario, correo, password)
        }
    }

    private fun registrarUsuario(usuario: String, correo: String, password: String) {
        firesbaseAuth = FirebaseAuth.getInstance()
        firesbaseAuth.createUserWithEmailAndPassword(correo,password)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    insertarInfoBD(usuario,correo)
                }else{
                    registroStatus.value = false
                    mensajeError.value = "Error al registrar : ${task.exception?.message}"
                }
            }
    }

    private fun insertarInfoBD(usuario: String, correo: String){
        val db = FirebaseFirestore.getInstance()

        val uidBD = firesbaseAuth.uid
        val tiempoBD = Constantes().obtenerTimeD()

        val datosUsuario = hashMapOf(
            "uid" to uidBD,
            "usuario" to usuario,
            "correo" to correo,
            "tiempo_registro" to tiempoBD
        )

        if (uidBD != null){
            db.collection("usuarios").document(uidBD).set(datosUsuario)
                .addOnSuccessListener {
                    registroStatus.value = true
                }
                .addOnFailureListener {e ->
                    registroStatus.value = false
                    mensajeError.value = "Fallo el registro en la Base de Datos debido a ${e.message}"
                }
        }else{
            registroStatus.value = false
            mensajeError.value = "Error al obtener UID del usuario."
        }
    }

}