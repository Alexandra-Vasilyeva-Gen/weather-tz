package com.example.weather_tz.Activities

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.weather_tz.IOHelper
import com.example.weather_tz.JSONParser
import com.example.weather_tz.Models.Weather
import com.example.weather_tz.R
import com.example.weather_tz.WeatherClient

class AddCityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_city)
        val editText = findViewById<EditText>(R.id.new_city)
        var okButton = findViewById<Button>(R.id.ok_button)
        okButton.setOnClickListener {
            WeatherTask().execute(editText.text.toString())
        }
    }

    inner class WeatherTask : AsyncTask<String, Void, Weather>() {
        override fun doInBackground(vararg params: String?): Weather? {
            var weather: Weather
            val data: String = WeatherClient(this@AddCityActivity)
                .GetWeatherData(params[0].toString())
            if (data.isEmpty()) {
                return null
            }
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
                IOHelper().writeFile(this@AddCityActivity, result.location)
                val intent = Intent()
                intent.putExtra(EXTRA_NEW_CITY, result.location)
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                val builder = AlertDialog.Builder(this@AddCityActivity)
                builder.setTitle("Invalid city name!")
                builder.setMessage("Please, enter correct city name")
                builder.setPositiveButton("Ok") { dialog, _ ->
                    dialog.dismiss()
                }
                builder.show()
            }
        }
    }

    fun cancelClicked(view: View) {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }
}