package com.example.weather_tz
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_tz.Models.Weather
import kotlinx.android.synthetic.main.weather_fragment.view.*

class PagerAdapter(private val cities: List<Weather>) : RecyclerView.Adapter<PagerAdapter.PagerViewHolder>() {

    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_fragment, parent, false)
        return PagerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (cities.isEmpty()) {
            1
        } else {
            cities.size
        }
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        if (cities.isEmpty()) {
            holder.itemView.run {}
        } else {
            val curWeather = cities[position]
            holder.itemView.run {
                city_name.text = curWeather.location
                temperature.text = curWeather.temperature.toString() + "\u00B0"
                condition.text = curWeather.description
            }
        }
    }
}