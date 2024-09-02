package com.recetas.recetasapp.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.recetas.recetasapp.R
import com.recetas.recetasapp.viewModel.PerfilViewModel
import androidx.lifecycle.Observer
import com.recetas.recetasapp.view.activity.CrearRecetasActivity
import com.recetas.recetasapp.view.activity.ListarMisRecetaActivity
import com.recetas.recetasapp.view.activity.MenuActivity

class PerfilFragment : Fragment() {

    private  var firebaseAuth : FirebaseAuth?= null

    private lateinit var viewModel: PerfilViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_perfil, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        viewModel = ViewModelProvider(requireActivity()).get(PerfilViewModel::class.java)

        val miPerfil: LinearLayout = view.findViewById(R.id.miPerfil)
        val misRecetas : LinearLayout = view.findViewById(R.id.misRecetas)
        val crearRecetas : LinearLayout = view.findViewById(R.id.textCrearrecetas)
        val cerrarSesion: AppCompatButton = view.findViewById(R.id.logout_button)

        val nombreUsuario = view.findViewById<TextView>(R.id.user_name)

        viewModel.nombreU.observe(viewLifecycleOwner, Observer {nombre ->
            nombreUsuario.text = nombre
        })

        viewModel.readInformation()

        miPerfil.setOnClickListener{
            val newFragment = PerfilEditFragment.newInstance()
            openSubFragment(newFragment)
        }

        misRecetas.setOnClickListener{
            val intent = Intent(activity, ListarMisRecetaActivity:: class.java)
            startActivity(intent)
        }

        crearRecetas.setOnClickListener{
            val intent = Intent(activity, CrearRecetasActivity::class.java)
            startActivity(intent)
        }

        cerrarSesion.setOnClickListener{
            closeSesion()
        }

    }

    private fun openSubFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_menu, fragment)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun closeSesion(){
        firebaseAuth!!.signOut()
        startActivity(Intent(activity, MenuActivity::class.java))
        activity?.finish()
    }

    companion object{
        fun newInstance() : PerfilFragment = PerfilFragment()
    }

}