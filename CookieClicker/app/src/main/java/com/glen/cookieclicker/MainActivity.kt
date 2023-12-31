package com.glen.cookieclicker

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import android.view.animation.AnimationUtils

import java.lang.Math.pow

class MainActivity : AppCompatActivity() {
    private var cookies = 0
    private var bakery_count = 0
    private var upgrade_count = 0
    var bakery_cost: Int = 100 * (bakery_count + 1)
    var upgrade_cost: Int = 100 * (upgrade_count + 1)

    private var handler: Handler? = null
    private var timer: Runnable? = null

    private val PREFS_FILENAME = "com.glen.cookieclicker.prefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load saved cookies
        val prefs: SharedPreferences = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        cookies = prefs.getInt("cookies", 0)
        bakery_count = prefs.getInt("bakery_count", 0)
        upgrade_count = prefs.getInt("upgrade_count", 0)
        updateUI()
        // create a looper that adds 10 cookies every 2000 milliseconds
        handler = Handler()
        timer = object : Runnable {
            override fun run() {
                if (bakery_count > 0) {
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
            animateBop(main_cookie)
            addCookie((1 * pow(2.0, upgrade_count.toDouble())).toInt())
        }

        add_bakery.setOnClickListener {
            if (cookies >= bakery_cost) {
                addCookie(-1 * bakery_cost)
                addBakery()
            } else {
                Toast.makeText(this, "Not enough cookies!", Toast.LENGTH_SHORT).show()
            }
            bakery_cost = 100 * (bakery_count + 1)
        }

        upgrade.setOnClickListener {
            if (cookies >= upgrade_cost) {
                addCookie(-1 * upgrade_cost)
                addUpgrade()
            } else {
                Toast.makeText(this, "Not enough cookies!", Toast.LENGTH_SHORT).show()
            }
            upgrade_cost = 100 * pow(4.0,upgrade_count.toDouble()).toInt()
        }
    }

    private fun animateBop(view: View) {
        val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce_animation)
        view.startAnimation(bounceAnimation)
    }

    override fun onPause() {
        super.onPause()

        // Save cookies
        val prefs: SharedPreferences = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putInt("cookies", cookies)
        editor.putInt("bakery_count", bakery_count)
        editor.putInt("upgrade_count", upgrade_count)
        editor.apply()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("cookies", cookies)
        outState.putInt("bakery_count", bakery_count)
        outState.putInt("upgrade_count", upgrade_count)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        cookies = savedInstanceState.getInt("cookies", 0)
        bakery_count = savedInstanceState.getInt("bakery_count", 0)
        upgrade_count = savedInstanceState.getInt("upgrade_count", 0)
        updateUI()
    }

    private fun updateUI() {
        findViewById<TextView>(R.id.cookie_count).text = "Cookies: $cookies"
        findViewById<TextView>(R.id.bakery_cost).text = "Cost: $bakery_cost"
        findViewById<TextView>(R.id.bakery_count).text = "$bakery_count"
        findViewById<TextView>(R.id.level).text = "Lvl $upgrade_count Cost: $upgrade_cost"
    }

    fun addCookie(count: Int = 1) {
        cookies += count
        updateUI()
    }

    fun addBakery(count: Int = 1) {
        bakery_count += count
        updateUI()
    }

    fun addUpgrade(count: Int = 1) {
        upgrade_count += count
        updateUI()
    }
}
