package com.example.weather_tz.Activities

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_tz.*
import com.example.weather_tz.Models.Weather

class ManageCitiesActivity : AppCompatActivity() {
    private lateinit var cardRecycler: RecyclerView
    private var weathers = ArrayList<Weather>()
    private var citiesList = ArrayList<String>()
    private var newCity = String()
    val cardAdapter = CardAdapter(weathers)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_cities)
        cardRecycler = findViewById(R.id.recyclerView)
        citiesList = intent.getSerializableExtra(EXTRA_CITIES_LIST) as ArrayList<String>
        for (c in citiesList) {
            WeatherTask().execute(c)
        }
        cardRecycler.adapter = cardAdapter
        cardRecycler.layoutManager = LinearLayoutManager(this)
    }

    fun addCity(view: View) {
        val intent = Intent(this, AddCityActivity::class.java)
        startActivityForResult(intent, 1)
    }

    inner class WeatherTask : AsyncTask<String, Void, Weather>() {
        override fun doInBackground(vararg params: String?): Weather? {
            var weather: Weather
            val data: String = WeatherClient(this@ManageCitiesActivity)
                .GetWeatherData(params[0].toString())
            try {
                weather = JSONParser().getWeather(data)
                return weather
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }

        override fun onPostExecute(result: Weather?) {
            super.onPostExecute(result)
            if (result != null) {
                weathers.add(result)
                cardAdapter.notifyDataSetChanged()
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null)
            return
        if (resultCode == RESULT_OK) {
            var city = data.getStringExtra(EXTRA_NEW_CITY)
            if (!city.isNullOrEmpty()) {
                newCity = city
                WeatherTask().execute(city)
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent()
        if (newCity.isNullOrEmpty()) {
            setResult(Activity.RESULT_CANCELED)
        } else {
            intent.putExtra(EXTRA_NEW_CITY, newCity)
            setResult(Activity.RESULT_OK, intent)
        }
        finish()
    }
//TODO rewrite for any city
    fun deleteCity(view: View) {
        IOHelper().deleteCity(this, "Cheboksary")
        weathers.removeIf{it.location == "Cheboksary"}
        cardAdapter.notifyDataSetChanged()
    }
}