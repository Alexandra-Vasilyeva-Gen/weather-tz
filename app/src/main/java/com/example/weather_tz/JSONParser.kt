package com.example.weather_tz

import com.example.weather_tz.Models.Weather
import org.json.JSONObject

class JSONParser {
    fun getWeather(data: String) : Weather {
        var jObj = JSONObject(data)
        var jArr = jObj.getJSONArray("weather")
        var JSONWeather = jArr.getJSONObject(0)
        var mainObj = getObject("main", jObj)

        var weather = Weather()
        weather.location = getString("name", jObj)
        weather.description = getString("description", JSONWeather)
        weather.temperature = getFloat("temp", mainObj).toInt()
        return weather
    }

    fun getCity(cityName: String) : JSONObject {
        val obj = JSONObject()
        obj.put("name", cityName)
        return obj
    }

    private fun getObject(tagName: String, jsonObj: JSONObject) : JSONObject {
        return jsonObj.getJSONObject(tagName)
    }

    private fun getString(tagName: String, jsonObj: JSONObject) : String {
        return jsonObj.getString(tagName)
    }

    private fun getFloat(tagName: String, jsonObj: JSONObject) : Float {
        return jsonObj.getDouble(tagName).toFloat()
    }

    private fun getInt(tagName: String, jsonObj: JSONObject) : Int {
        return jsonObj.getInt(tagName)
    }
}