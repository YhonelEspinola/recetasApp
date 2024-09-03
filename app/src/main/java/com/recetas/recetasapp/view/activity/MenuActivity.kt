package com.recetas.recetasapp.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.recetas.recetasapp.R
import com.recetas.recetasapp.view.fragment.BuscarFragment
import com.recetas.recetasapp.view.fragment.FavoritosFragment
import com.recetas.recetasapp.view.fragment.InicioFragment
import com.recetas.recetasapp.view.fragment.PerfilEditFragment
import com.recetas.recetasapp.view.fragment.PerfilFragment

class MenuActivity : AppCompatActivity() {

    private var firebaseAuth: FirebaseAuth?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        firebaseAuth = FirebaseAuth.getInstance()

        val nav_view = findViewById<BottomNavigationView>(R.id.nav_view)
        nav_view.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.itemHome -> {
                    val fragment = InicioFragment.newInstance()
                    openFragment(fragment)
                    true
                }

                R.id.itemSearch -> {
                    val fragment = BuscarFragment.newInstance()
                    openFragment(fragment)
                    true
                }

                R.id.itemFavoritos -> {
                    val fragment = FavoritosFragment.newInstance()
                    openFragment(fragment)
                    true
                }

                R.id.itemProfile -> {
                    comprobarSesion()
                    true
                }
                else -> false
            }
        }
        supportFragmentManager.addOnBackStackChangedListener {
            val fragment = supportFragmentManager.findFragmentById(R.id.fragment_menu)
            if (fragment != null && fragment::class.java in setOf(
                    InicioFragment::class.java,
                    BuscarFragment::class.java,
                    PerfilEditFragment::class.java,
                    PerfilFragment::class.java)) {
                nav_view.selectedItemId = getFragmentMenuItemId(fragment)
            }
        }




    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
            finish()
        }

    }

    override fun onResume() {
        super.onResume()
        val nav_view = findViewById<BottomNavigationView>(R.id.nav_view)
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_menu)
        if (fragment != null) {
            nav_view.selectedItemId = getFragmentMenuItemId(fragment)
        }
    }

    fun openFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val currentFragment = fragmentManager.findFragmentById(R.id.fragment_menu)

        if (currentFragment != null && currentFragment::class.java == fragment::class.java) {
            return // El fragmento ya está mostrado, no hagas nada
        }

        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_menu, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    private fun getFragmentMenuItemId(fragment: Fragment): Int {
        return when (fragment) {
            is InicioFragment -> R.id.itemHome
            is BuscarFragment -> R.id.itemSearch
            is FavoritosFragment -> R.id.itemFavoritos
            is PerfilFragment, is PerfilEditFragment -> R.id.itemProfile
            else -> R.id.itemHome // Default case
        }
    }

    private fun comprobarSesion(){
        if (firebaseAuth!!.currentUser==null){
            startActivity(Intent(this, LoginActivity::class.java))
            Toast.makeText(this,"Usuario no logeado", Toast.LENGTH_SHORT).show()

        }else{
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_menu)

            when(currentFragment){
                is PerfilFragment -> openFragment(PerfilFragment.newInstance())
                is PerfilEditFragment -> openFragment(PerfilEditFragment.newInstance())
                else -> openFragment(PerfilFragment.newInstance())
            }
        }
    }
}