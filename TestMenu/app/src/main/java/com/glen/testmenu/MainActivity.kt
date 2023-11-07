package com.glen.testmenu

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.glen.testmenu.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.submenu_item1 -> {
                val fragment = FirstFragment() // Create an instance of the FirstFragment
                supportFragmentManager.beginTransaction()
                    .replace(R.id.textview_first, fragment) // Replace the existing fragment with the FirstFragment
                    .addToBackStack(null) // Add the transaction to the back stack, so you can navigate back
                    .commit()
                return true
            }
            // Add cases for other submenu items if needed
            R.id.submenu_item2 -> {
                // Handle the second submenu item
                val fragment = SecondFragment() // Create an instance of the SecondFragment
                supportFragmentManager.beginTransaction()
                    .replace(R.id.textview_second, fragment) // Replace the existing fragment with the SecondFragment
                    .addToBackStack(null) // Add the transaction to the back stack, so you can navigate back
                    .commit()
                return true
            }
            R.id.submenu_item3 -> {
                // Handle the third submenu item
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}