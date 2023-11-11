package com.example.weatherproject.ui.cities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CitiesViewModel : ViewModel() {

    private val cityTxt = MutableLiveData<String>().apply {
        value = "This is cities Fragment"
    }
    val text: LiveData<String> = cityTxt
}