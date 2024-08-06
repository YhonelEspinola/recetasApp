package com.recetas.recetasapp.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recetas.recetasapp.R
import com.recetas.recetasapp.view.adapter.MisRecetasAdapter
import com.recetas.recetasapp.viewModel.RecetaViewModel

class ListarMisRecetaActivity : AppCompatActivity() {

    private lateinit var viewModel : RecetaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_recetas)

        viewModel = ViewModelProvider(this)[RecetaViewModel::class.java]

        val recycler = findViewById<RecyclerView>(R.id.recycler_view_mis_recetas)
        val searchView = findViewById<SearchView>(R.id.search)

        val adapterE = MisRecetasAdapter()
        recycler.adapter=adapterE
        recycler.layoutManager= LinearLayoutManager(this)

        viewModel.listarReceta()
        viewModel.listaReceta.observe(this){
            if(it.isNotEmpty()){
                adapterE.setDatos(it)
            }
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