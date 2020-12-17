package com.example.integratedproject

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavView:BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitiy)
        bottomNavView = findViewById(R.id.bottom_navigation)
        bottomNavView.selectedItemId = R.id.students;
        bottomNavView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.students -> {
                    val intent = Intent(this, UserList::class.java)
                    startActivity(intent)
                    true
                }
                else -> {
                    Log.d("test", "admin")
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    false
                }
            }
        }
        Handler().postDelayed(Runnable {
            val i = Intent(this@MainActivity, UserList::class.java)
            startActivity(i)
            finish()
        }, 1500)
    }
}