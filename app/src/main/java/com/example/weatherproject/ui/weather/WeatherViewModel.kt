package com.example.weatherproject.ui.weather


import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.test.WeatherData
import com.example.weatherproject.database.DatabaseHelper
import com.example.weatherproject.repository.IWeatherRepository
import com.example.weatherproject.ui.cities.Cities
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class WeatherViewModel : ViewModel() {

    val weatherLiveData = MutableLiveData<WeatherData>()


    fun getWeather(retrofit: Retrofit, repository: IWeatherRepository, key:String, place: String){
        viewModelScope.launch {
           repository.getWeatherData(retrofit,weatherLiveData,key,place)
        }
    }

    fun addCity(context: Context, city: String){
        val db = DatabaseHelper(context)
        db.addCityData(city)
    }

}