package com.glen.cookieclicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    var cookies = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val main_cookie: ImageButton = findViewById(R.id.cookie)

        main_cookie.setOnClickListener {
            cookies++
            findViewById<TextView>(R.id.cookie_count).text = "Cookies: $cookies"
        }
    }
}