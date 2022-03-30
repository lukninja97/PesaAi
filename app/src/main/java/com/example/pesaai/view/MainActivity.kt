package com.example.pesaai.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.pesaai.R
import com.example.pesaai.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var destinationListener: NavController.OnDestinationChangedListener
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!


    private val noVisiblelogo = setOf(
        R.id.all_fazendas_fragment,
        R.id.pesar_fragment,
        R.id.all_pesagens_fragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_PesaAi)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(binding.navHostFragmentContentMain.id) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        destinationListener = NavController.OnDestinationChangedListener{_, destination, arguments ->
            if (destination.id in noVisiblelogo){
                binding.imgLogo.visibility = View.INVISIBLE
            } else{
                binding.imgLogo.visibility = View.VISIBLE
            }
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}