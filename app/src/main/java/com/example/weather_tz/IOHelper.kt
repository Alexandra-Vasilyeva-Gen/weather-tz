package com.example.weather_tz

import android.content.Context
import java.io.*


class IOHelper {
    val FILE_NAME = "saved_cities.txt"
    fun writeFile(context: Context, string: String) {
        val newString = string + "\n"
        try {
            val fos = context.openFileOutput(FILE_NAME, Context.MODE_APPEND)
            fos.write(newString.toByteArray())
            fos.close()
        }
        catch (e: IOException) {
            e.printStackTrace()
        }
    }
    fun readFile(context: Context) : ArrayList<String> {
        var resList = ArrayList<String>()
        try {
            val file = File(context.filesDir, FILE_NAME)
            val fileReader = FileReader(file)
            val bufferedReader = BufferedReader(fileReader)
            var string: String? = bufferedReader.readLine()
            while (string != null) {
                resList.add(string)
                string = bufferedReader.readLine()
            }
            bufferedReader.close()
            fileReader.close()
        }
        catch (e: IOException) {
            e.printStackTrace()
        }
        return resList
    }
}