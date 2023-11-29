package com.glen.finalproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//main form activity
class MainActivity : BaseActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextAge: EditText
    private lateinit var radioGroupSex: RadioGroup
    private lateinit var radioGroupYearLevel: RadioGroup
    private lateinit var radioGroupCourse: RadioGroup
    private lateinit var editTextEmail: EditText
    private lateinit var buttonSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextName = findViewById(R.id.editTextName)
        editTextAge = findViewById(R.id.editTextAge)
        radioGroupSex = findViewById(R.id.radioGroupSex)
        radioGroupYearLevel = findViewById(R.id.radioGroupYearLevel)
        radioGroupCourse = findViewById(R.id.radioGroupCourse)
        editTextEmail = findViewById(R.id.editTextEmail)
        buttonSubmit = findViewById(R.id.buttonSubmit)

        buttonSubmit.setOnClickListener {
            if (validateForm()) {
                submitForm()
            }
        }
    }

    private fun validateForm(): Boolean {
        if (editTextName.text.isBlank() ||
            editTextAge.text.isBlank() ||
            getSelectedRadioText(radioGroupSex).isBlank() ||
            getSelectedRadioText(radioGroupYearLevel).isBlank() ||
            getSelectedRadioText(radioGroupCourse).isBlank() ||
            editTextEmail.text.isBlank()
        ) {
            Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun submitForm() {
        val name = editTextName.text.toString()
        val age = editTextAge.text.toString()
        val sex = getSelectedRadioText(radioGroupSex)
        val yearLevel = getSelectedRadioText(radioGroupYearLevel)
        val course = getSelectedRadioText(radioGroupCourse)
        val email = editTextEmail.text.toString()

        val formData = FormData(name, age, sex, yearLevel, course, email, getCurrentDate())

        ManageForms.addForm(formData)

        val intent = Intent(this, ThankYouActivity::class.java)
        startActivity(intent)
    }

    private fun getSelectedRadioText(radioGroup: RadioGroup): String {
        val selectedId = radioGroup.checkedRadioButtonId
        if (selectedId == -1) {
            return ""
        }
        val radioButton = findViewById<RadioButton>(selectedId)
        return radioButton.text.toString()
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }
}