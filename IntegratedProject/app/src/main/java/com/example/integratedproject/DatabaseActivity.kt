package com.example.integratedproject

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_database.*


class DatabaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)
        title = "iWasThere"
        val context = this
        val db = DataBaseHandler(context)
        AddStudentButton.setOnClickListener {
            if(editTextUserName.text.toString().isNotEmpty() &&
                editTextTextStudentenNummer.text.toString().isNotEmpty()){
                val name = editTextUserName.text.toString()
                val studentenNummer = editTextTextStudentenNummer.text.toString()

                db.insertData(name, studentenNummer)
                clearField()
            }
            else{
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }

        }

        ButtonViewData.setOnClickListener {
            /*val data = db.readData()
            textViewResult.text = ""
            for(i in 0 until data.size){
                textViewResult.append(
                    data[i].studentennummer.toString() + " " + data[i].name + "\n"
                )
            }*/
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    private fun clearField(){
        editTextUserName.text.clear()
        editTextTextStudentenNummer.text.clear()
    }
}