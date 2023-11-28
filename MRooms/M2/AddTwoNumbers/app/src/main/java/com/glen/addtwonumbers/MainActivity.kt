package com.glen.addtwonumbers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        val editTextNumber1: EditText = findViewById(R.id.editTextNumber1)
        val editTextNumber2: EditText = findViewById(R.id.editTextNumber2)
        val btnAdd: Button = findViewById(R.id.btnAdd)
        val textViewResult: TextView = findViewById(R.id.textViewResult)

        btnAdd.setOnClickListener {
            val strNumber1 = editTextNumber1.text.toString()
            val strNumber2 = editTextNumber2.text.toString()

            if (strNumber1.isNotEmpty() && strNumber2.isNotEmpty()) {
                val number1 = strNumber1.toDouble()
                val number2 = strNumber2.toDouble()

                val result = number1 + number2

                textViewResult.text = "Result: $result"
            } else {
                Toast.makeText(this, "Please enter two numbers", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
