package com.example.weatherproject.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.weatherproject.R
import com.example.weatherproject.network.RetrofitProvider
import com.example.weatherproject.repository.WeatherRepository
import com.example.weatherproject.databinding.FragmentWeatherBinding

class WeatherFragment : Fragment() {

    private var _binding: FragmentWeatherBinding? = null
    lateinit var weatherViewModel: WeatherViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.searchImage.setOnClickListener {
            val key = getString(R.string.apiKey)
            val loc = binding.loctionET.text.toString()
            val repository = WeatherRepository()
            if(loc.isNotEmpty()){
                weatherViewModel.getWeather(RetrofitProvider.retrofit,repository,key,loc)
            }else{
                Toast.makeText(context,"Please insert city!!",Toast.LENGTH_SHORT).show()
            }
        }
        listenLiveData()
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun listenLiveData(){
        weatherViewModel.weatherLiveData.observe(viewLifecycleOwner){weather ->
            binding.locText.text = weather.location.name + ", " + weather.location.region + ", " + weather.location.country

            //for celsius use \u2103 and for fahrenheit use \u2109

            binding.tempText.text = weather.current.temp_c.toString() + " \u2103"
            binding.feelText.text = "Feels like " + weather.current.feelslike_c.toString() + " \u2103"
            binding.conditionText.text = weather.current.condition.text
            binding.windText.text = weather.current.wind_kph.toString() + " KM/H"
            binding.pressureText.text = weather.current.pressure_in.toString() + " in"
            binding.precipText.text = weather.current.precip_mm.toString() + " mm"
            binding.visiText.text = weather.current.vis_km.toString() + " KM"
            binding.humiText.text = weather.current.humidity.toString() + " %"
            binding.gustText.text = weather.current.gust_kph.toString() + " KM/H"
            Glide.with(this).load("https:"+weather.current.condition.icon).into(binding.iconImage)
        }
    }
}