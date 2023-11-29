package com.glen.finalproject
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

//reusable prefs loader/saver class
class PrefsLoader(context: Context, prefsName: String) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    fun <T> saveData(key: String, data: T) {
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(data)
        editor.putString(key, json)
        editor.apply()
    }

    fun <T> loadData(key: String, type: TypeToken<T>): T? {
        val gson = Gson()
        val json = sharedPreferences.getString(key, null)
        return gson.fromJson(json, type.type)
    }
}

