package com.glen.midtermexam2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate.*


open class BaseActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_home -> {
                Toast.makeText(this, "@strings/menu_item_1", Toast.LENGTH_SHORT).show()
                openHome()
                return true
            }
            R.id.action_list -> {
                Toast.makeText(this, "@strings/menu_item_2", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ListActivity::class.java)
                openList()
                return true
            }
            R.id.action_timer -> {
                Toast.makeText(this, "@strings/menu_item_3", Toast.LENGTH_SHORT).show()
                openTimer()
                return true
            }
            R.id.action_draw -> {
                Toast.makeText(this, "@strings/menu_item_4", Toast.LENGTH_SHORT).show()
                openDraw()
                return true
            }
            R.id.action_cookie -> {
                Toast.makeText(this, "@strings/menu_item_5", Toast.LENGTH_SHORT).show()
                openCookie()
                return true
            }
            R.id.action_mode -> {
                Toast.makeText(this, "Switching Theme", Toast.LENGTH_SHORT).show()
                toggleTheme()
                return true
            }
            R.id.action_about -> {
                showAbout()
                return true
            }
//            R.id.action_reset -> {
//                Toast.makeText(this, "Reset", Toast.LENGTH_SHORT).show()
//                resetData()
//                return true
//            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun openHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    fun openList() {
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
    }

    fun openTimer() {
        val intent = Intent(this, TimerActivity::class.java)
        startActivity(intent)
    }

    fun openDraw() {
        val intent = Intent(this, DrawActivity::class.java)
        startActivity(intent)
    }

    fun openCookie() {
        val intent = Intent(this, CookieActivity::class.java)
        startActivity(intent)
    }

    private fun toggleTheme() {
        if (getDefaultNightMode() == MODE_NIGHT_YES) {
            setDefaultNightMode(MODE_NIGHT_NO)
        } else {
            setDefaultNightMode(MODE_NIGHT_YES)
        }
        this.recreate()
    }
    private fun showAbout() {
        val alertDialogBuilder = AlertDialog.Builder(this)

        alertDialogBuilder.setTitle("About")

        val message =
                "App: Prod-Activity\n" +
                "Version: 1.0\n" +
                "Author: Glen Angelo Bautista\n" +
                "Section: C231\n" +
                "Description: An app with several functions to boost your productivity.\n"
        alertDialogBuilder.setMessage(message)



        // Add a close button
        alertDialogBuilder.setPositiveButton("Close") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    //this doesnt work
    private fun resetData() {

        val cookieActivityIntent = Intent(this, CookieActivity::class.java)
        cookieActivityIntent.action = "resetPrefs"
        startActivity(cookieActivityIntent)

    }
}