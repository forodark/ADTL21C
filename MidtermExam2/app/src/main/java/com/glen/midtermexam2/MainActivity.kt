package com.glen.midtermexam2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //show main_menu.xml
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_game -> {
                Toast.makeText(this, "Game", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.action_shop -> {
                Toast.makeText(this, "Shop", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.action_stats -> {
                Toast.makeText(this, "Stats", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.action_mode -> {
                Toast.makeText(this, "Mode", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.action_about -> {
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}