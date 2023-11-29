package com.glen.finalproject

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

//base activity to hold common methods for all activities
open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    protected fun showMessage(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Form Details")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .create()
            .show()
    }

    protected fun buildAnswersText(selectedItem: FormData): String {

        val stringBuilder = StringBuilder()

        if (selectedItem != null) {
            stringBuilder.append("Name: ${selectedItem.name}\n")
            stringBuilder.append("Age: ${selectedItem.age}\n")
            stringBuilder.append("Sex: ${selectedItem.sex}\n")
            stringBuilder.append("Year Level: ${selectedItem.yearLevel}\n")
            stringBuilder.append("Course: ${selectedItem.course}\n")
            stringBuilder.append("Email: ${selectedItem.email}\n")
            stringBuilder.append("Submission Date: ${selectedItem.submissionDate}\n\n")
        }

        return stringBuilder.toString()
    }
}