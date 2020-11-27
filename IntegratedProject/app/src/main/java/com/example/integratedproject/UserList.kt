package com.example.integratedproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.time.LocalDate

class UserList : AppCompatActivity() {
    data class User(val name:String, val studentNr:String, val date: LocalDate?, val coordinaten: ArrayList<Float>?, val handtekening:Boolean)
    private lateinit var listView: ListView;
    private lateinit var bottomNavView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
        listView = findViewById<ListView>(R.id.student_list);
        val studentList=ArrayList<User>()
        studentList.add(User("James Stoels","s107197",null, null,true))
        studentList.add(User("Witse Cools","s123456",null, null,false))
        studentList.add(User("Laura Dirckx","s654321",null, null,false))
        val adapter=UserListAdapter(this,studentList)
        listView.adapter=adapter

        listView.setOnItemClickListener{parent, view, position, id ->
            val element = adapter.getItemId(position) // The item that was clicked
            val intent = Intent(this, DrawingCanavas::class.java)
            startActivity(intent)
        }

        bottomNavView=findViewById(R.id.bottom_navigation)
        bottomNavView.selectedItemId = R.id.students;
        bottomNavView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.students ->{
                   /* val intent = Intent(this, UserList::class.java)
                    startActivity(intent)*/
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
    }
}