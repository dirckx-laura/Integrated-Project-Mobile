package com.example.integratedproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.children
import kotlinx.android.synthetic.main.activity_student_info.*

class StudentInfoActivity : AppCompatActivity() {
    data class Registration(val location:String, val signature: String,val date:String)
    private lateinit var textView:TextView
    private var registrationList=ArrayList<Registration>()
    private lateinit var db:DataBaseHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_info)
        db= DataBaseHandler(this)
        registrationList=loadRegistrations()
        registrationList.forEach{it->
            Log.d("test",it.date)
        }
        registrationListView.setOnItemClickListener{_, _, position, _->
            val studentSignatureIntent= Intent(this,SignedSignature::class.java)
            studentSignatureIntent.putExtra("coords",registrationList[position].signature)
            Log.d("test","Student clicked")
            startActivity(studentSignatureIntent)
        }
    }
    private fun loadRegistrations():ArrayList<Registration>{
        db = DataBaseHandler(this)

        val test = db.readDataRegistrationByStudentNumber("1000")
        Log.d("readdata", test.toString());

        val data = db.readDataRegistration()
        if(data.size != 0){
            for(item in data){
                registrationList.add(Registration(item.location,item.signaturePoints,item.datum));
            }
        }else{
            Toast.makeText(this, "NO DATA!", Toast.LENGTH_SHORT).show()
        }

        if(registrationList.size !=0){
            val adapter=RegistrationAdapter(this,registrationList)
            registrationListView.adapter=adapter
        }
        val adapter=RegistrationAdapter(this,registrationList)
        registrationListView.adapter=adapter
        return registrationList
    }
}