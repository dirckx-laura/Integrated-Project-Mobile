package com.example.integratedproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_database.*

class AdminList : AppCompatActivity() {
    data class Student(val name:String,val studentNr:String)
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

/*        Log.d("pwd","rowcount: ${adminDbHelper.getRowCount()}")
        if(adminDbHelper.getRowCount()>0){
            val hashedPwd=adminDbHelper.getPassword()
            Log.d("pwd","hashedPwd$hashedPwd")
        }
        else{
            adminDbHelper.insertPassword("test")
        }*/

            val data = db.readData()

            //studentList = data as ArrayList<Student>

        Log.d("studentlist", data.toString());


        studentList.add(Student("James Stoels","s107197"))
        studentList.add(Student("Witse Cools","s123456"))
        studentList.add(Student("Laura Dirckx","s654321"))
        Log.d("studentlist", studentList.toString());
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

        addStudentButton.setOnClickListener {
            val intent= Intent(this,DatabaseActivity::class.java)
            startActivity(intent)
        }

    }
}