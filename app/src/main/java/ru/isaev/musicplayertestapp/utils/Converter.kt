package ru.isaev.musicplayertestapp.utils

import android.util.Log

object Converter {

    fun getMinutes(millis: Long): String {
        Log.e("Converter", millis.toString())
        var time = ""
        val minute = millis / 1000 / 60
        var second = millis / 1000 % 60

        time += "$minute:"

        if (second < 10) {
            time += "0"
        }
        time += second
        return time
    }
}