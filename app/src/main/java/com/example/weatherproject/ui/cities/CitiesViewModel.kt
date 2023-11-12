package com.example.weatherproject.ui.cities

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherproject.database.DatabaseHelper
import com.example.weatherproject.services.WeatherService
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class CitiesViewModel : ViewModel() {

    val cityList = MutableLiveData<ArrayList<Cities>>()
    private var cities = ArrayList<Cities>()


    init{
        cityList.value = cities
    }


    fun getCityData(retrofit: Retrofit, key:String, place: String){
       cities.clear()
        viewModelScope.launch {
            val weatherService = retrofit.create(WeatherService::class.java)
            val weather = weatherService.getWeather(key,place)
            cities.add(Cities(weather.location.name, weather.current.temp_c.toString(), weather.current.condition.icon))
            cityList.value = cities
        }
    }

    fun getcities(context: Context):ArrayList<String>{
        val db = DatabaseHelper(context)
        var cities = ArrayList<String>()
        val count = db.countTableRow()
        if(count != 0){
            cities = db.getCities()
        }
        return cities
    }
}