package com.example.weatherproject.ui.cities

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.example.weatherproject.database.DatabaseHelper
import com.example.weatherproject.network.RetrofitProvider.retrofit
import com.example.weatherproject.services.WeatherService
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class CitiesViewModel : ViewModel() {

    val cityList = MutableLiveData<ArrayList<Cities>>()
    private var cities = ArrayList<Cities>()

    init{
        cityList.value = cities
    }

    fun getCityData(context: Context, key:String, place: String){
        val sharedPreferences = context.let { PreferenceManager.getDefaultSharedPreferences(it) }!!
        val tempUnit = sharedPreferences.getBoolean("temperature_unit",  false)
       cities.clear()
        viewModelScope.launch {
            val weatherService = retrofit.create(WeatherService::class.java)
            val weather = weatherService.getWeather(key,place)
            if(tempUnit){
                cities.add(Cities(weather.location.name, weather.current.temp_f.toString() + " \u2109", weather.current.condition.icon))
            }else{
                cities.add(Cities(weather.location.name, weather.current.temp_c.toString()+ " \u2103", weather.current.condition.icon))
            }
            cityList.value = cities
        }
    }

    fun getCities(context: Context):ArrayList<String>{
        val db = DatabaseHelper(context)
        var cities = ArrayList<String>()
        val count = db.countTableRow()
        if(count != 0){
            cities = db.getCities()
        }
        return cities
    }
}