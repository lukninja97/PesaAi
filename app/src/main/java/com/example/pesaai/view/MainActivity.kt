package com.example.pesaai.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pesaai.R
import com.example.pesaai.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_PesaAi)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val navHostFragment = supportFragmentManager.findFragmentById(binding.navHostFragmentContentMain.id) as NavHostFragment
        navController = navHostFragment.navController

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        configureDestination()
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun configureDestination() {
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            binding.imgLogo.visibility = when (destination.id) {
//                R.id.dashboardFragment -> View.VISIBLE
//                else -> View.GONE
//            }
//        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}