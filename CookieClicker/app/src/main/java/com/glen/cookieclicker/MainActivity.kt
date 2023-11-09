package com.glen.cookieclicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.SystemClock
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    var cookies = 0;

    private var handler: Handler? = null
    private var timer: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create a handler to post the toast message
        handler = Handler()

        // Create a runnable to update the timer
        timer = Runnable {
            // Get the current time in milliseconds
            val time = SystemClock.elapsedRealtime()

            // Update the timer text
            // ...

            // Display the toast message every 4500 milliseconds
            if (time % 2000 == 0L) {
                addCookie(10)
                Toast.makeText(this, "+10", Toast.LENGTH_SHORT).show()
            }

            // Schedule the next timer update
            handler?.postDelayed(timer!!, 1)
        }

        // Start the timer
        handler?.post(timer!!)



        val main_cookie: ImageButton = findViewById(R.id.cookie)

        main_cookie.setOnClickListener {
            addCookie()
        }
    }

    fun addCookie(count: Int = 1) {
        cookies += count
        findViewById<TextView>(R.id.cookie_count).text = "Cookies: $cookies"
    }
}