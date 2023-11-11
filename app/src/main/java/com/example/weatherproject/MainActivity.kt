package com.example.weatherproject

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.weatherproject.databinding.ActivityMainBinding
import com.example.weatherproject.ui.cities.CitiesFragment
import com.example.weatherproject.ui.settings.SettingsFragment
import com.example.weatherproject.ui.weather.WeatherFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.materialToolbar
        setSupportActionBar(toolbar)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_weather,
                R.id.navigation_cities,
                R.id.navigation_settings
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_weather -> {
                    NavigationUI.onNavDestinationSelected(it,navController)
                }

                R.id.navigation_cities -> {
                    NavigationUI.onNavDestinationSelected(it,navController)
                }

                R.id.navigation_settings -> {
                    NavigationUI.onNavDestinationSelected(it,navController)
                }

                else -> {
                    false
                }
            }
            true
        }
    }
}