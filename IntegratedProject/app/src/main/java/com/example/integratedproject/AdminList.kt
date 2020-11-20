package com.example.integratedproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ListView

class AdminList : AppCompatActivity() {
    data class Student(val name:String,val studentNr:String)
    lateinit var searchStudent:EditText
    private lateinit var listView: ListView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_list)
        listView = findViewById<ListView>(R.id.student_list)
        searchStudent=findViewById<EditText>(R.id.searchStudent)
        val studentList=ArrayList<Student>()
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
    }
}