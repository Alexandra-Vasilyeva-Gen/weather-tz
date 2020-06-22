package com.example.weather_tz.Activities

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.weather_tz.*
import com.example.weather_tz.Models.Weather
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

const val EXTRA_CITIES_LIST = "CITIES_LIST"
const val EXTRA_NEW_CITY = "NEW_CITY"

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private var weathers = ArrayList<Weather>()
    private var citiesList = ArrayList<String>()
    val pagerAdapter = PagerAdapter(weathers)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager = findViewById(R.id.pager)
        val tabLayout = findViewById<TabLayout>(R.id.into_tab_layout)

        citiesList = getCitiesList()
        for (c in citiesList) {
            WeatherTask().execute(c)
        }
        viewPager.adapter = pagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, _ ->
            viewPager.setCurrentItem(tab.position, true)
        }.attach()
    }

    private fun getCitiesList() : ArrayList<String> {
        return IOHelper().readFile(this)
    }

    inner class WeatherTask : AsyncTask<String, Void, Weather>() {
        override fun doInBackground(vararg params: String?): Weather? {
            var weather: Weather
            val data: String = WeatherClient(this@MainActivity)
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
                pagerAdapter.notifyDataSetChanged();
            }
        }
    }

    fun manageCities(view: View) {
        val intent = Intent(this, ManageCitiesActivity::class.java)
        intent.putExtra(EXTRA_CITIES_LIST, citiesList)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null)
            return
        if (resultCode == RESULT_OK) {
            var newCity = data.getStringExtra(EXTRA_NEW_CITY)
            if (!newCity.isNullOrEmpty()) {
                citiesList.add(newCity)
                WeatherTask().execute(newCity)
            }
        }
    }
}