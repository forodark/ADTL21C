package com.glen.midtermexam2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast


open class BaseActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_game -> {
                Toast.makeText(this, "Game", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_shop -> {
                Toast.makeText(this, "Shop", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, SampleActivity::class.java)
                startActivity(intent)
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