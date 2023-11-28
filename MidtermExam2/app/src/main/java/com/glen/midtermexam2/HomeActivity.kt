// HomeActivity.kt

package com.glen.midtermexam2

import android.content.Intent
import android.os.Bundle
import android.widget.Button

class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.title = "Home"

        val listButton = findViewById<Button>(R.id.ListButton)
        val timerButton = findViewById<Button>(R.id.TimerButton)
        val drawButton = findViewById<Button>(R.id.DrawButton)
        val cookieButton = findViewById<Button>(R.id.CookieButton)
        val calculatorButton = findViewById<Button>(R.id.CalculatorButton)
        val lightsOutButton = findViewById<Button>(R.id.LightsOutButton)

        listButton.setOnClickListener {
            openList()
        }

        timerButton.setOnClickListener {
            openTimer()
        }

        drawButton.setOnClickListener {
            openDraw()
        }

        cookieButton.setOnClickListener {
            openCookie()
        }

        calculatorButton.setOnClickListener {
            openCalculator()
        }

        lightsOutButton.setOnClickListener {
            openLightsOut()
        }

    }
}
