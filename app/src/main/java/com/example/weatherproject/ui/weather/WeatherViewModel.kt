package com.example.weatherproject.ui.weather


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.test.WeatherData
import com.example.weatherproject.WeatherService
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class WeatherViewModel : ViewModel() {

    val weatherLiveData = MutableLiveData<WeatherData>()

    fun getWeather(retrofit: Retrofit, key:String, place: String){
        viewModelScope.launch {
            val weatherService = retrofit.create(WeatherService::class.java)
            val weather = weatherService.getWeather(key,place)
            weatherLiveData.value = weather
        }
    }
}