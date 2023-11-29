package com.glen.finalproject

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class OverviewActivity : BaseActivity() {

    private lateinit var listViewForms: ListView
    private val formList = ArrayList<FormData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        listViewForms = findViewById(R.id.listViewForms)

        formList.addAll(ManageForms.getAllForms())

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, formList.map { formData ->
            "Name: ${formData.name}\nEmail: ${formData.email}\nSubmission Date: ${formData.submissionDate}"
        })

        listViewForms.adapter = adapter

        listViewForms.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = formList[position]
            val answerText = buildAnswersText(selectedItem)
            showMessage(answerText)
        }

        listViewForms.setOnItemLongClickListener { _, _, position, _ ->
            showDeleteConfirmation(position)
            true
        }

    }

    private fun showDeleteConfirmation(position: Int) {
        val selectedItem = formList[position]

        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Delete Form")
            .setMessage("Are you sure you want to delete the form:\n${selectedItem.name}\n${selectedItem.email}")
            .setPositiveButton("Yes") { _, _ ->

                ManageForms.deleteForm(position)

                formList.removeAt(position)

                (listViewForms.adapter as ArrayAdapter<*>).notifyDataSetChanged()

                reloadActivity()
            }
            .setNegativeButton("No", null)
            .create()

        alertDialog.show()
    }

    private fun reloadActivity() {
        val intent = intent
        finish()
        startActivity(intent)
    }





}