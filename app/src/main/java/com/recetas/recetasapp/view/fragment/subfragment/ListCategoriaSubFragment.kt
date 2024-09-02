package com.recetas.recetasapp.view.fragment.subfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recetas.recetasapp.R
import com.recetas.recetasapp.view.adapter.ListCategoriaAdapter
import com.recetas.recetasapp.viewModel.ListCategoriaViewModel

class ListCategoriaSubFragment : Fragment() {

    private lateinit var viewModel : ListCategoriaViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.subfragment_categoriahome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ListCategoriaViewModel::class.java]

        val recyclerPC = view.findViewById<RecyclerView>(R.id.recyclerPC)
        val adapterPLA = ListCategoriaAdapter()
        recyclerPC.adapter=adapterPLA
        recyclerPC.layoutManager = GridLayoutManager(context, 2)

        val catego = arguments?.getString("catselect").toString()
        viewModel.listRecetas(catego)
        viewModel.listRecetasLCat.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                adapterPLA.setDatos(it)
            }
        }
    }

    companion object{
        fun newInstance() : ListCategoriaSubFragment = ListCategoriaSubFragment()
    }

}