package com.example.integratedproject

import android.content.Context
import com.google.firebase.database.FirebaseDatabase

class FirebaseHelper {
    private val database = FirebaseDatabase.getInstance("https://integratedproject-953b8-default-rtdb.firebaseio.com/")
    private  lateinit var dbHelper:DataBaseHandler
    private var students=ArrayList<Student>()
    private var registrations=ArrayList<Registration>()
    private fun clearDatabase(){
        database.reference.setValue(null)
    }
    private fun formatStudents(){
        students=dbHelper.readData() as ArrayList<Student>
        var counter=0
        students.forEach { it->
            database.getReference("Students").child(counter.toString()).setValue(it)
            counter++
        }

    }
    private fun formatRegistrations(){
        registrations=dbHelper.readDataRegistration()as ArrayList<Registration>
        var counter=0
        registrations.forEach { it->
            database.getReference("Registrations").child(counter.toString()).setValue(it)
            counter++
        }
    }
    fun syncDatabase(context: Context){
        clearDatabase()
        dbHelper= DataBaseHandler(context)
        formatStudents()
        formatRegistrations()
    }
}