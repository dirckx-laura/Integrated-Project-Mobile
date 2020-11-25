package com.example.integratedproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "iWasThere"
        val context = this
        val db = DataBaseHandler(context)
        AddUserButton.setOnClickListener {
            if(editTextUserName.text.toString().isNotEmpty() &&
                    editTextTextPassword.text.toString().isNotEmpty()){
                val name = editTextUserName.text.toString()
                db.insertData(name)
                clearField()
            }
            else{
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }

        }
       userListButton.setOnClickListener{
            val intent = Intent(this, UserList::class.java)
            startActivity(intent)
        }

        ButtonViewData.setOnClickListener {
            val data = db.readData()
            textViewResult.text = ""
            for(i in 0 until data.size){
                textViewResult.append(
                    data[i].id.toString() + " " + data[i].name + "\n"
                )
            }
        }

    }

    private fun clearField(){
        editTextUserName.text.clear()
        editTextTextPassword.text.clear()
    }
}