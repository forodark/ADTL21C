package com.glen.cookieclicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageButton
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var timer : TextView
    var cookies = 0;
    val max_time: Long = 2147483647;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timer = findViewById(R.id.timer)

        object : CountDownTimer(max_time*1000, 1000) {
            // Callback function, fired on regular interval
            override fun onTick(millisUntilFinished: Long) {
                timer.setText("Timer: " + millisUntilFinished / 1000)
                addCookie()
            }

            // Callback function, fired
            // when the time is up
            override fun onFinish() {
                timer.setText("done!")
            }
        }.start()

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