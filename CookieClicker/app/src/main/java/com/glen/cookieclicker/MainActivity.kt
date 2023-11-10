package com.glen.cookieclicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.SystemClock
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import java.lang.Math.pow

class MainActivity : AppCompatActivity() {
    var cookies = 0
    var bakery_count = 0
    var upgrade_count = 0

    private var handler: Handler? = null
    private var timer: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // create a looper that adds 10 cookies every 2000 milliseconds
        handler = Handler()
        timer = object : Runnable {
            override fun run() {
                if(bakery_count>0) {
                    addCookie(bakery_count * 5)
                }
                handler!!.postDelayed(this, 1000)
            }
        }
        handler!!.post(timer!!)



        val main_cookie: ImageButton = findViewById(R.id.cookie)
        val add_bakery: ImageButton = findViewById(R.id.bakery_button)
        val upgrade: Button = findViewById(R.id.upgrade)

        main_cookie.setOnClickListener {

            addCookie((1*pow(2.0, upgrade_count.toDouble())).toInt())
        }

        add_bakery.setOnClickListener {
            val bakery_cost: Int = 100*(bakery_count+1)
            if(cookies >= bakery_cost) {
                addCookie(-1*bakery_cost)
                addBakery()
            }
            else {
                Toast.makeText(this, "Not enough cookies!", Toast.LENGTH_SHORT).show()
            }
        }

        upgrade.setOnClickListener {

            if(cookies >= 20) {
                addCookie(-20)
                addUpgrade()
            }
            else {
                Toast.makeText(this, "Not enough cookies!", Toast.LENGTH_SHORT).show()
            }
        }


    }

    fun addCookie(count: Int = 1) {
        cookies += count
        findViewById<TextView>(R.id.cookie_count).text = "Cookies: $cookies"
    }

    fun addBakery(count: Int = 1) {
        bakery_count += count
        findViewById<TextView>(R.id.bakery_count).text = "$bakery_count"
    }

    fun addUpgrade(count: Int = 1) {
        upgrade_count += count
        findViewById<TextView>(R.id.level).text = "Lvl $upgrade_count"
    }
}