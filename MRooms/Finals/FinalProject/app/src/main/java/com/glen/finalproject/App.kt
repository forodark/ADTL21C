package com.glen.finalproject

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        ManageForms.initialize(this)
    }
}