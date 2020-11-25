package com.example.integratedproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View;
import android.widget.EditText;
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.integratedproject.fragments.AdminFragment
import com.example.integratedproject.fragments.HomeFragment
import kotlinx.android.synthetic.main.activitiy.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitiy)

        val homeFragment = HomeFragment()
        val adminFragment = AdminFragment()

        makeCurrentFragment(homeFragment)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.ic_home -> makeCurrentFragment(homeFragment)
                R.id.ic_admin -> makeCurrentFragment(adminFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
       supportFragmentManager.beginTransaction().apply {
           replace(R.id.fl_wrapper,fragment)
           commit()
       }

}