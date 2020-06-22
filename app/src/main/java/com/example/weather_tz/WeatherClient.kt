package com.example.weather_tz

import android.content.Context
import android.os.AsyncTask
import com.example.weather_tz.Models.Weather
import java.io.BufferedReader;
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset

class WeatherClient(context: Context) {
    private val BASE_URL: String = "https://api.openweathermap.org/data/2.5/weather?q="
    private val IMG_URL: String = "https://openweathermap.org/img/w/"
    private val API_KEY: String = "&appid=${context.getString(R.string.weather_api_key)}"

    fun GetWeatherData(location: String): String {
        try {
            return URL("$BASE_URL$location&units=metric&$API_KEY").readText(Charset.defaultCharset())
        }
        catch(e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}

