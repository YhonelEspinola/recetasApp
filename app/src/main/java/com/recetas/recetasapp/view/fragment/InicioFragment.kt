package com.recetas.recetasapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recetas.recetasapp.R
import com.recetas.recetasapp.model.Categoria
import com.recetas.recetasapp.model.OnCategoriaClickListener
import com.recetas.recetasapp.view.adapter.CategoriaAdapter
import com.recetas.recetasapp.view.fragment.subfragment.InicioSubFragment
import com.recetas.recetasapp.view.fragment.subfragment.ListCategoriaSubFragment

class InicioFragment : Fragment(), OnCategoriaClickListener {

    private lateinit var recyclerCategoria: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_inicio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerCategoria = view.findViewById(R.id.recyclerCategorias)

        val listaCategoria = listOf<Categoria>(
            Categoria("Comida Rapida"),
            Categoria("Desayuno"),
            Categoria("Menu"),
            Categoria("Postre"),
            Categoria("Merienda"),
            Categoria("Cena")
        )
        val adapterC = CategoriaAdapter(listaCategoria,this)
        recyclerCategoria.adapter = adapterC
        recyclerCategoria.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)

        if (listaCategoria.isNotEmpty()){
            onCategoriaClick(listaCategoria[0])
        }

    }

    override fun onCategoriaClick(categoria: Categoria) {
        val fragment = when (categoria.categoria){
            "Comida Rapida" -> InicioSubFragment()
                "Desayuno",
                "Menu",
                "Postre",
                "Merienda",
                "Cena" -> ListCategoriaSubFragment()
            else -> InicioSubFragment()
        }
        val bundle = Bundle()
        bundle.putString("catselect", categoria.categoria)
        fragment.arguments = bundle

        childFragmentManager.beginTransaction()
            .replace(R.id.subfragment_home, fragment)
            .commit()
    }

    companion object {
        fun newInstance() : InicioFragment = InicioFragment()
    }
}