package com.recetas.recetasapp.view.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.recetas.recetasapp.databinding.ActivityLoginBinding
import com.recetas.recetasapp.viewModel.LoginViewModel

class LoginActivity: AppCompatActivity() {

    private lateinit var viewModel : LoginViewModel

    private lateinit var progressDialog: ProgressDialog
    private lateinit var binding : ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnLogin.setOnClickListener{
            val email = binding.textEmail.text.toString()
            val password = binding.textPassword.text.toString()

            progressDialog.setMessage("Ingresando...")
            progressDialog.show()

            viewModel.validarInfo(email, password)
        }

        binding.textRegistrarse.setOnClickListener{
            startActivity(Intent(applicationContext, RegistrarUsuarioActivity::class.java))
        }
        binding.txtOlvidarContra.setOnClickListener{
            startActivity(Intent(applicationContext, RecuperarContraseniaActivity::class.java))
        }
        observerLiveData()
    }

    private fun observerLiveData(){
        viewModel.userLoginStatus.observe(this){status ->
            if(status){
                progressDialog.dismiss()
                startActivity(Intent(this, MenuActivity::class.java))
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
            }else{
                progressDialog.dismiss()
                viewModel.mesajeError.value?.let{
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.emailError.observe(this){errorMessage ->
            binding.textEmail.error = errorMessage
            if(errorMessage != null){
                binding.textEmail.requestFocus()
            }
        }

        viewModel.passwordError.observe(this){errorMessage ->
            binding.textPassword.error = errorMessage
            if (errorMessage != null){
                binding.textPassword.requestFocus()
            }
        }

        viewModel.mesajeError.observe(this){ errorMessage ->
            if (errorMessage != null){
                Toast.makeText(this,errorMessage,Toast.LENGTH_SHORT).show()
            }
        }

    }


}