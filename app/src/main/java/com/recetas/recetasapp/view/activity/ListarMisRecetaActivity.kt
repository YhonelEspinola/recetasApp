package com.recetas.recetasapp.view.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.recetas.recetasapp.R
import com.recetas.recetasapp.view.adapter.MisRecetasAdapter
import com.recetas.recetasapp.viewModel.RecetaViewModel

class ListarMisRecetaActivity : AppCompatActivity() {

    private lateinit var viewModel : RecetaViewModel
    private val TAG = "ListarMisRecetaActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_recetas)

        viewModel = ViewModelProvider(this)[RecetaViewModel::class.java]

        val recycler = findViewById<RecyclerView>(R.id.recycler_view_mis_recetas)
        val searchView = findViewById<SearchView>(R.id.search)

        val adapterE = MisRecetasAdapter()
        recycler.adapter=adapterE
        recycler.layoutManager= LinearLayoutManager(this)

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            val userId = currentUser.uid

            viewModel.listarRecetasDelUsuario(userId)

            viewModel.listaReceta.observe(this) { recetas ->
                if (recetas.isNotEmpty()) {
                    adapterE.setDatos(recetas)
                } else {
                    Log.d(TAG, "No se encontraron recetas para este usuario.")
                }
            }
        } else {
            Log.e(TAG, "No hay ningún usuario autenticado.")
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override  fun onQueryTextSubmit(query: String?):Boolean {
                query?.let { adapterE.filtrarDatos(it) }
                return false
            }
            override fun onQueryTextChange(newText:String?):Boolean{
                newText?.let { adapterE.filtrarDatos(it) }
                return false
            }

        })

    }

}