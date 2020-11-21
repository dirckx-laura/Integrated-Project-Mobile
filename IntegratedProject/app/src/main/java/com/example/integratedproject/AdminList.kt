package com.example.integratedproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ListView

class AdminList : AppCompatActivity() {
    data class Student(val name:String,val studentNr:String)
    lateinit var searchStudent:EditText
    private lateinit var listView: ListView;
    private lateinit var adminDbHelper:AdminDBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_list)
        listView = findViewById<ListView>(R.id.student_list)
        searchStudent=findViewById<EditText>(R.id.searchStudent)
        val studentList=ArrayList<Student>()
        adminDbHelper= AdminDBHelper(this)
/*        Log.d("pwd","rowcount: ${adminDbHelper.getRowCount()}")
        if(adminDbHelper.getRowCount()>0){
            val hashedPwd=adminDbHelper.getPassword()
            Log.d("pwd","hashedPwd$hashedPwd")
        }
        else{
            adminDbHelper.insertPassword("test")
        }*/
        studentList.add(Student("James Stoels","s107197"))
        studentList.add(Student("Witse Cools","s123456"))
        studentList.add(Student("Laura Dirckx","s654321"))
        val adapter=StudentListAdapter(this,studentList)
        listView.adapter=adapter
        searchStudent.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                adapter.getFilter().filter(s);
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
        listView.setOnItemClickListener { _, _, position, _ ->
            val studentInfoIntent= Intent(this,StudentInfoActivity::class.java)
            studentInfoIntent.putExtra("sNr",studentList[position].studentNr)
            startActivity(studentInfoIntent)
        }

    }
}