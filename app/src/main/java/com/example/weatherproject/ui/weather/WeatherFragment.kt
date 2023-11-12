package com.example.weatherproject.ui.weather

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.location.LocationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.example.weatherproject.R
import com.example.weatherproject.network.RetrofitProvider
import com.example.weatherproject.repository.WeatherRepository
import com.example.weatherproject.databinding.FragmentWeatherBinding

class WeatherFragment : Fragment() {

    private var _binding: FragmentWeatherBinding? = null
    lateinit var weatherViewModel: WeatherViewModel
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 1
    lateinit var key: String
    lateinit var repository: WeatherRepository
    lateinit var sharedPreferences: SharedPreferences

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        val root: View = binding.root

        key = getString(R.string.apiKey)
        repository = WeatherRepository()

        binding.searchImage.setOnClickListener {
            val loc = binding.loctionET.text.toString()
            if(loc.isNotEmpty()){
                weatherViewModel.getWeather(RetrofitProvider.retrofit,repository,key,loc)
                binding.loctionET.text?.clear()
            }else{
                Toast.makeText(context,"Please insert city!!",Toast.LENGTH_SHORT).show()
            }
        }

        binding.locImage.setOnClickListener {
            getLocation()
        }

        binding.favBtn.setOnClickListener {
            val city = binding.locText.text.toString().trim()
            weatherViewModel.addCity(requireContext(), city)
        }

        listenLiveData()
        return root
    }

    private fun getLocation() {
        locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (checkPermission()) {
            if (LocationManagerCompat.isLocationEnabled(locationManager)) {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 5000, 5f
                ) { location ->
                    weatherViewModel.getWeather(RetrofitProvider.retrofit,repository,key,location.toString())
                }
            } else {
                Toast.makeText(context, "Please turn on your location.", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                ContextCompat.startActivity(requireActivity(), intent, null)
            }
        } else {
            requestPermission()
        }
    }

    private fun checkPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }


    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            context as Activity, arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), locationPermissionCode
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                getLocation()
        } else {
            Toast.makeText(context, "You denied location access permission.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun listenLiveData(){
        sharedPreferences = context?.let { PreferenceManager.getDefaultSharedPreferences(it) }!!
        val tempUnit = sharedPreferences.getBoolean("temperature_unit",  false)
        val windUnit = sharedPreferences.getBoolean("wind_unit",  false)
        val pressureUnit = sharedPreferences.getBoolean("pressure_unit",  false)
        val precipitationUnit = sharedPreferences.getBoolean("precipitation_unit",  false)
        val visibilityUnit = sharedPreferences.getBoolean("visibility_unit",  false)
        val gustUnit = sharedPreferences.getBoolean("gust_unit",  false)

        weatherViewModel.weatherLiveData.observe(viewLifecycleOwner){weather ->
            if(weather.current.is_day == 1){
                binding.weatherLL.background = ResourcesCompat.getDrawable(resources,R.drawable.day,null)
            }else{
                binding.weatherLL.background = ResourcesCompat.getDrawable(resources,R.drawable.night,null)
            }
            binding.locText.text = weather.location.name + ", " + weather.location.region + ", " + weather.location.country

            //for celsius use \u2103 and for fahrenheit use \u2109

            if(tempUnit){
                binding.tempText.text = weather.current.temp_f.toString() + " \u2109"
                binding.feelText.text = "Feels like " + weather.current.feelslike_f.toString() + " \u2109"
            }else{
                binding.tempText.text = weather.current.temp_c.toString() + " \u2103"
                binding.feelText.text = "Feels like " + weather.current.feelslike_c.toString() + " \u2103"
            }

            if(windUnit){
                binding.windText.text = weather.current.wind_mph.toString() + " M/H"
            }else{
                binding.windText.text = weather.current.wind_kph.toString() + " KM/H"
            }

            if(pressureUnit){
                binding.pressureText.text = weather.current.pressure_mb.toString() + " mb"
            }else{
                binding.pressureText.text = weather.current.pressure_in.toString() + " in"
            }

            if(precipitationUnit){
                binding.precipText.text = weather.current.precip_in.toString() + " in"
            }else{
                binding.precipText.text = weather.current.precip_mm.toString() + " mm"
            }

            if(visibilityUnit){
                binding.visiText.text = weather.current.vis_miles.toString() + " Miles"
            }else{
                binding.visiText.text = weather.current.vis_km.toString() + " KM"
            }

            if(gustUnit){
                binding.gustText.text = weather.current.gust_mph.toString() + " M/H"
            }else{
                binding.gustText.text = weather.current.gust_kph.toString() + " KM/H"
            }
            binding.conditionText.text = weather.current.condition.text
            binding.humiText.text = weather.current.humidity.toString() + " %"

            Glide.with(this).load("https:"+weather.current.condition.icon).into(binding.iconImage)
        }
    }
}