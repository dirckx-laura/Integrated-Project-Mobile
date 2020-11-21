package com.example.integratedproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import java.time.LocalDate

class UserList : AppCompatActivity() {
    data class User(val name:String, val studentNr:String, val date: LocalDate?, val coordinaten: ArrayList<Float>?, val handtekening:Boolean)
    private lateinit var listView: ListView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
        listView = findViewById<ListView>(R.id.studentList)
        val studentList=ArrayList<User>()
        studentList.add(User("James Stoels","s107197",null, null,false))
        studentList.add(User("Witse Cools","s123456",null, null,false))
        studentList.add(User("Laura Dirckx","s654321",null, null,false))
        val adapter=UserListAdapter(this,studentList)
        listView.adapter=adapter

    }
}