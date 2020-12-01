package com.example.integratedproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView

class AdminList : AppCompatActivity() {
    data class Student(val name: String, val studentNr:String)
    lateinit var searchStudent:EditText
    private lateinit var listView: ListView;
    private lateinit var adminDbHelper:AdminDBHelper
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var addStudentButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_list)
        listView = findViewById<ListView>(R.id.student_list)
        searchStudent=findViewById<EditText>(R.id.searchStudent)
        var studentList=ArrayList<Student>()
        adminDbHelper= AdminDBHelper(this)
        bottomNavView=findViewById(R.id.bottom_navigation)
        bottomNavView.selectedItemId=R.id.admin
        addStudentButton=findViewById(R.id.addStudentButton)
        val context = this
        val db = DataBaseHandler(context)

        val data = db.readData()

        if(data.size != 0){
            for(item in data){
                studentList.add(Student(item.name.toString(), item.studentennummer.toString()));
            }
        }else{
            Toast.makeText(context, "NO DATA!", Toast.LENGTH_SHORT).show()
        }

        if(studentList.size !=0){



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
        bottomNavView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.admin ->{
                    /*Log.d("test","admin")
                    val intent= Intent(this,LoginActivity::class.java)
                    startActivity(intent)*/
                    true
                }
                else -> {
/*
                    Log.d("test","students")
*/
                    val intent = Intent(this, UserList::class.java)
                    startActivity(intent)
                    false
                }
            }
        }
        }else{
            Toast.makeText(context, "NO DATA!", Toast.LENGTH_SHORT).show()
        }

        addStudentButton.setOnClickListener {
            val intent= Intent(this,DatabaseActivity::class.java)
            startActivity(intent)
        }

    }
}