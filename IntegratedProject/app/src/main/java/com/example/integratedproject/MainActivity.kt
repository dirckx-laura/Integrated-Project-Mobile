package com.example.integratedproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View;
import android.widget.EditText;
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activitiy.*

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavView:BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitiy)
        bottomNavView=findViewById(R.id.bottom_navigation)
        bottomNavView.selectedItemId = R.id.students;
        bottomNavView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.students ->{
                    val intent = Intent(this, UserList::class.java)
                    startActivity(intent)
                    true
                }
                else -> {
                    Log.d("test","admin")
                    val intent= Intent(this,LoginActivity::class.java)
                    startActivity(intent)
                    false
                }
            }
        }
/*        val homeFragment = HomeFragment()
        val adminFragment = AdminFragment()*/

/*        makeCurrentFragment(homeFragment)


        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.ic_home -> makeCurrentFragment(homeFragment)
                R.id.ic_admin -> makeCurrentFragment(adminFragment)
            }
            true
        }*/
    }

/*    private fun makeCurrentFragment(fragment: Fragment) =
       supportFragmentManager.beginTransaction().apply {
           replace(R.id.fl_wrapper,fragment)
           commit()
       }*/

}