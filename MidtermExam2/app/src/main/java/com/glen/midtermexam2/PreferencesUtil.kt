package com.glen.midtermexam2

import android.content.Context
import android.content.SharedPreferences
import java.io.File

object PreferencesUtil {
    private const val PREFS_NAME = "app_prefs"
    private var instance: PreferencesUtil? = null
    private lateinit var prefs: SharedPreferences

    fun getInstance(context: Context): PreferencesUtil {
        if (instance == null) {
            instance = PreferencesUtil
            prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        }
        return instance!!
    }

    fun deletePrefsFile(context: Context) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit().clear().apply()
        val prefsFile = File(context.applicationInfo.dataDir + "/shared_prefs/$PREFS_NAME.xml")
        prefsFile.delete()
    }

    fun resetPrefs() {
        prefs.edit().clear().apply()
    }

    fun getPrefs(): SharedPreferences {
        return prefs
    }

    fun saveInt(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }

    fun saveString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    // Load methods
    fun loadInt(key: String, defaultValue: Int): Int {
        return prefs.getInt(key, defaultValue)
    }

    fun loadString(key: String, defaultValue: String): String? {
        return prefs.getString(key, defaultValue)
    }

    fun getEditor(): SharedPreferences.Editor {
        return prefs.edit()
    }
}
