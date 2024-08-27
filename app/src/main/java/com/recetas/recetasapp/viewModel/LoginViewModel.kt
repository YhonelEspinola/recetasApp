package com.recetas.recetasapp.viewModel

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {

    private lateinit var firebaseAuth: FirebaseAuth

    val userLoginStatus = MutableLiveData<Boolean>()
    val emailError = MutableLiveData<String>()
    val passwordError = MutableLiveData<String>()
    val mesajeError = MutableLiveData<String>()

    fun validarInfo(email: String, password: String){
        if(email.isEmpty()){
            emailError.value = "Ingrese su correo"
            userLoginStatus.value = false
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailError.value = "Correo no valido"
            userLoginStatus.value = false
        }else if (password.isEmpty()){
            passwordError.value = "Ingrese su contraseña"
            userLoginStatus.value = false
        }else{
            loginFirebase(email,password)
        }

    }

    fun loginFirebase(email: String, password: String){
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                userLoginStatus.value = it.isSuccessful
                if(!it.isSuccessful){
                    mesajeError.value = "Error al Iniciar sesion, verifique sus datos"
                }
            }
    }

}