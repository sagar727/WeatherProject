package com.example.weatherproject.repository

import androidx.lifecycle.MutableLiveData
import com.example.weather.test.WeatherData
import com.example.weatherproject.services.WeatherService
import com.example.weatherproject.ui.cities.Cities
import retrofit2.Retrofit


class WeatherRepository : IWeatherRepository {

    override suspend fun getWeatherData(
        retrofit: Retrofit,
        weatherLiveData: MutableLiveData<WeatherData>,
        key: String,
        loc: String,

    ) {
        val weatherService = retrofit.create(WeatherService::class.java)
        val weather = weatherService.getWeather(key, loc, 3)
        weatherLiveData.value = weather
    }

}

interface IWeatherRepository {
    suspend fun getWeatherData(
        retrofit: Retrofit,
        weatherLiveData: MutableLiveData<WeatherData>,
        key: String,
        loc: String,
    )
}
