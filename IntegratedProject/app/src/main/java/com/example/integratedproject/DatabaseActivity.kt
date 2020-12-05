package com.example.integratedproject

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_database.*
import java.lang.Exception


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
                else {
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

        ButtonAddCSV.setOnClickListener {
            if(editTextCSV.text.toString().isNotBlank()){
                var csvtxt = editTextCSV.text.toString()

                val result = csvtxt

                val studentNames =  mutableListOf<String>()
                val studentNumbers = mutableListOf<String>()


                val lstValues: List<String> = result.split(";").map { it -> it.trim() }

                for (i in lstValues.indices) {
                    if(i % 2 == 0){
                        studentNumbers.add(lstValues[i])
                    }else{
                        studentNames.add(lstValues[i])
                    }
                }

                for (i in studentNumbers.indices) {
                    db.insertData(studentNames[i], studentNumbers[i])
                }



            }

        }
        



    }

    private fun clearField(){
        editTextUserName.text.clear()
        editTextTextStudentenNummer.text.clear()
    }
}