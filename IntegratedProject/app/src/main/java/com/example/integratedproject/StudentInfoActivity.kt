package com.example.integratedproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class StudentInfoActivity : AppCompatActivity() {
    private lateinit var textView:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_info)
        textView=findViewById(R.id.textView)
        val sNr=intent.getStringExtra("sNr")
        textView.text=sNr
    }
}