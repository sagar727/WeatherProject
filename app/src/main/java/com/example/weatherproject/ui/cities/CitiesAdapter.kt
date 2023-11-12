package com.example.weatherproject.ui.cities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherproject.R

class CitiesAdapter(val context: Context,val cityArray: ArrayList<Cities>?) : RecyclerView.Adapter<CitiesAdapter.viewholder>() {

    class viewholder(cityItem: View) : RecyclerView.ViewHolder(cityItem){
        val cityName = cityItem.findViewById<TextView>(R.id.cityName)
        val temperature = cityItem.findViewById<TextView>(R.id.temperature)
        val temperatureImage = cityItem.findViewById<ImageView>(R.id.temperatureImage)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_city_layout, parent, false)
        return viewholder(view)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val city = cityArray?.get(position)
        holder.cityName.text = city!!.cityName
        holder.temperature.text = city.temperature
        Glide.with(context).load("https:"+city.temperatureImage).into(holder.temperatureImage);
    }

    override fun getItemCount(): Int {
        return cityArray?.size ?: 0
    }

}