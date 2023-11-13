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

        val drawButton = findViewById<Button>(R.id.ListButton)
        val showAlertButton = findViewById<Button>(R.id.TimerButton)
        val activity3Button = findViewById<Button>(R.id.DrawButton)
        val activity4Button = findViewById<Button>(R.id.CookieButton)

        drawButton.setOnClickListener {
            openList()
        }

        showAlertButton.setOnClickListener {
            openTimer()
        }

        activity3Button.setOnClickListener {
            openDraw()
        }

        activity4Button.setOnClickListener {
            openCookie()
        }
    }
}
