package com.recetas.recetasapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recetas.recetasapp.R
import com.recetas.recetasapp.view.adapter.FavoritosAdapter
import com.recetas.recetasapp.viewModel.FavoritosViewModel

class FavoritosFragment : Fragment() {

    private lateinit var recetasFavoritosViewModel : FavoritosViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favoritos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recetasFavoritosViewModel = ViewModelProvider(this)[FavoritosViewModel::class.java]
        val recyclerR = view.findViewById<RecyclerView>(R.id.recyclerFavoritos)
        val adapterR = FavoritosAdapter()

        recyclerR.adapter = adapterR
        recyclerR.layoutManager = LinearLayoutManager(context)

        recetasFavoritosViewModel.listarRecetas()
        recetasFavoritosViewModel.listRecetas.observe(viewLifecycleOwner) {
            if(it.isNotEmpty()){
                adapterR.setDatos(it)
            }
        }
    }



    companion object{
        fun newInstance() : FavoritosFragment = FavoritosFragment()
    }
}