package com.recetas.recetasapp.view.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.recetas.recetasapp.databinding.ActivityRegisterBinding
import com.recetas.recetasapp.viewModel.RegistrarUsuarioViewModel

class RegistrarUsuarioActivity : AppCompatActivity() {

    private lateinit var viewModel: RegistrarUsuarioViewModel

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[RegistrarUsuarioViewModel::class.java]

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Creando cuenta...")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnRegistrarU.setOnClickListener{
            val usuario = binding.textUsuario.text.toString().trim()
            val correo = binding.textEmail.text.toString().trim()
            val password = binding.textPassword.text.toString().trim()
            val cPassword = binding.textCpassword.text.toString().trim()

            progressDialog.setMessage("Registrando Informacion...")
            progressDialog.show()

            viewModel.validarInformacion(usuario, correo, password, cPassword)
        }
        observeLiveData()

    }

    private fun observeLiveData() {
        viewModel.registroStatus.observe(this){ status ->
            if(status){
                progressDialog.dismiss()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }else{
                progressDialog.dismiss()
            }
        }

        viewModel.usuarioError.observe(this){errorMessage ->
            binding.textUsuario.error = errorMessage
        }

        viewModel.correoError.observe(this){errorMessage ->
            binding.textEmail.error = errorMessage
        }

        viewModel.passwordError.observe(this){errorMessage ->
            binding.textPassword.error = errorMessage
        }

        viewModel.cPasswordError.observe(this){errorMessage ->
            binding.textCpassword.error = errorMessage
        }

        viewModel.mensajeError.observe(this){errorMessage ->
            if(errorMessage!=null){
                progressDialog.dismiss()
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }

    }

}