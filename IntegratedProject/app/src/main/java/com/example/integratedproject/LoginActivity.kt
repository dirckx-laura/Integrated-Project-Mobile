package com.example.integratedproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    private lateinit var passwordText:EditText
    private lateinit var loginBtn:Button
    private lateinit var adminDbHelper:AdminDBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        passwordText=findViewById(R.id.editTextTextPassword)
        loginBtn=findViewById(R.id.loginBtn)
        adminDbHelper= AdminDBHelper(this)
        Log.d("pwd","rowcount: ${adminDbHelper.getRowCount()}")
        if(adminDbHelper.getRowCount()>0L){
            val hashedPwd=adminDbHelper.getPassword()
            Log.d("pwd","hashedPwd$hashedPwd")
        }
        else{
            adminDbHelper.insertPassword("admin")
        }

        loginBtn.setOnClickListener {
            if(!passwordText.text.toString().isEmpty()){
                if(adminDbHelper.comparePassword(passwordText.text.toString())){
                    Toast.makeText(this, "Login Success!", Toast.LENGTH_SHORT).show()
                    val intent= Intent(this,AdminList::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, "Wrong Password!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}