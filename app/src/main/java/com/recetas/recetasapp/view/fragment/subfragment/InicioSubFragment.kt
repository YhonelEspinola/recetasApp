package com.recetas.recetasapp.view.fragment.subfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recetas.recetasapp.R
import com.recetas.recetasapp.view.adapter.PopularAdapter
import com.recetas.recetasapp.view.adapter.RecetasInicioAdapter
import com.recetas.recetasapp.viewModel.ListCategoriaViewModel

class InicioSubFragment : Fragment() {
    private lateinit var adapterP: PopularAdapter
    private lateinit var adapterN: RecetasInicioAdapter
    private lateinit var viewModel: ListCategoriaViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.subfragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ListCategoriaViewModel::class.java]
        val recyclerMasVistos = view.findViewById<RecyclerView>(R.id.recyclerMasVendidos)
        val recyclerRecomendado = view.findViewById<RecyclerView>(R.id.recyclerRecomendado)
        val verTodo = view.findViewById<TextView>(R.id.verTodo)

        adapterP = PopularAdapter()
        recyclerMasVistos.adapter=adapterP
        recyclerMasVistos.layoutManager=
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)

        adapterN = RecetasInicioAdapter()
        recyclerRecomendado.adapter=adapterN
        recyclerRecomendado.layoutManager= GridLayoutManager(context,2)

        viewModel.listRecetasNuevas()
        viewModel.listRecetasN.observe(viewLifecycleOwner) {

            if (it.isNotEmpty()) {
                adapterN.setDatos(it)
            }

        }
        verTodo.setOnClickListener(){

            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.subfragment_home, MasNuevoSubFragment.newInstance())
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction.commit()
        }

    }

    companion object{
        fun newInstance() : InicioSubFragment = InicioSubFragment()
    }

}