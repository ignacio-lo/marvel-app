package com.example.marvelapp

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.marvelapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.toolbar_app.view.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(binding.toolbarApp as Toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.mainFragment -> showBottomNav(true)
                R.id.eventsFragment -> showBottomNav(true)
            }
        }

        binding.bottomNavView.setOnNavigationItemSelectedListener(this)
        binding.bottomNavView.itemIconTintList = null
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_characters -> navController.navigate(R.id.mainFragment)
            R.id.navigation_events -> navController.navigate(R.id.eventsFragment)
        }

        return true
    }

    override fun onBackPressed() {
        if (binding.bottomNavView.selectedItemId == R.id.navigation_events) {
            super.onBackPressed()
            binding.bottomNavView.selectedItemId = R.id.navigation_characters
        } else {
            super.onBackPressed()
            binding.toolbarApp.tv_toolbar_title.text = resources.getString(R.string.app_name)
        }
    }

    private fun showBottomNav(show: Boolean) {
        binding.bottomNavView.visibility = if (show) View.VISIBLE else View.GONE
    }
}