package com.example.weather_tz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_tz.Models.Weather
import kotlinx.android.synthetic.main.card_fragment.view.*

class CardAdapter (val cities: List<Weather>) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_fragment, parent, false)
        return CardViewHolder(view)
    }

    override fun getItemCount(): Int = cities.size

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val weather = cities[position]
        holder.itemView.run {
            city_name.text = weather.location
            temperature.text = weather.temperature.toString() + "\u00B0"
        }
    }

}