package com.glen.finalproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast

class WelcomeActivity : BaseActivityMenu() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val checkBoxAgree = findViewById<CheckBox>(R.id.checkBoxAgree)
        val buttonContinue = findViewById<Button>(R.id.buttonContinue)

        buttonContinue.setOnClickListener {
            if (checkBoxAgree.isChecked) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please agree to the terms.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
