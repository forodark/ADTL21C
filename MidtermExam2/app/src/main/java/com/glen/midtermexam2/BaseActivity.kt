package com.glen.midtermexam2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate.*


open class BaseActivity : AppCompatActivity() {

    var dark_mode : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PreferencesUtil.getInstance(this)
        loadDarkModeSetting()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_home -> {
                openHome()
                return true
            }
            R.id.action_list -> {
                val intent = Intent(this, ListActivity::class.java)
                openList()
                return true
            }
            R.id.action_timer -> {
                openTimer()
                return true
            }
            R.id.action_draw -> {
                openDraw()
                return true
            }
            R.id.action_cookie -> {
                openCookie()
                return true
            }
            R.id.action_mode -> {
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
            dark_mode = 0
        } else {
            setDefaultNightMode(MODE_NIGHT_YES)
            dark_mode = 1
        }
        saveDarkModeSetting()
        this.recreate()
    }

    private fun saveDarkModeSetting() {
        PreferencesUtil.saveInt("dark_mode", dark_mode)
    }

    private fun loadDarkModeSetting() {
        val selected = PreferencesUtil.loadInt("dark_mode", 0)
        if (selected == 1) {
            setDefaultNightMode(MODE_NIGHT_YES)
        } else {
            setDefaultNightMode(MODE_NIGHT_NO)
        }
    }

    private fun showAbout() {
        val alertDialogBuilder = AlertDialog.Builder(this)

        alertDialogBuilder.setTitle("About")

        val message =
                "App: Prod-Activity\n" +
                "Version: 1.1\n" +
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