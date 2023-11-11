package com.example.weatherproject

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {
    private val BaseUrl = "https://api.weatherapi.com/v1/"

    val retrofit by lazy { getRetrofitProvider() }

    private fun getRetrofitProvider(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }
}