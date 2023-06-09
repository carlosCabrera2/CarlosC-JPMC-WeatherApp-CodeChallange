package com.example.carlosc_jpmc_weatherapp_codechallange

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.carlosc_jpmc_weatherapp_codechallange.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.frag_container) as NavHostFragment

        setupActionBarWithNavController(navHost.navController)


    }

    override fun onSupportNavigateUp(): Boolean {

        val navController = findNavController(R.id.frag_container)

        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}