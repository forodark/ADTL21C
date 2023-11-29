package com.glen.finalproject

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

//alternate base activity that has added menu
open class BaseActivityMenu : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_overview, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_view_all -> {
                startActivity(Intent(this, OverviewActivity::class.java))
                true
            }
            R.id.action_about -> {
                showAbout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showAbout() {
        val alertDialogBuilder = AlertDialog.Builder(this)

        alertDialogBuilder.setTitle("About")

        val message =
            "App: FinalProject\n" +
                    "Version: 1.0\n" +
                    "Author: Glen Angelo Bautista\n" +
                    "Section: C231\n" +
                    "Description: An app that allows users to submit forms and view their answers. Requirement for mrooms final period.\n"
        alertDialogBuilder.setMessage(message)

        alertDialogBuilder.setPositiveButton("Close") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}