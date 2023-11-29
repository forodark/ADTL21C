package com.glen.finalproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button

class ThankYouActivity : BaseActivityMenu() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thank_you)

        val buttonViewAnswers = findViewById<Button>(R.id.buttonViewAnswers)
        val buttonSubmitAnotherForm = findViewById<Button>(R.id.buttonSubmitAnotherForm)

        val formDataList = ManageForms.getAllForms()
        val answersText = buildAnswersText(formDataList[0])

        buttonViewAnswers.setOnClickListener {
            showMessage(answersText)
        }

        buttonSubmitAnotherForm.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
        }
    }



}
