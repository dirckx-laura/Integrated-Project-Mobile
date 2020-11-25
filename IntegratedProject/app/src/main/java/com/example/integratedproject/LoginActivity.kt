package com.example.integratedproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView

class LoginActivity : AppCompatActivity() {
    private lateinit var passwordText:EditText
    private lateinit var loginBtn:Button
    private lateinit var adminDbHelper:AdminDBHelper
    private lateinit var bottomNavView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        passwordText=findViewById(R.id.editTextTextPassword)
        loginBtn=findViewById(R.id.loginBtn)
        adminDbHelper= AdminDBHelper(this)
        bottomNavView=findViewById(R.id.bottom_navigation)
        bottomNavView.selectedItemId = R.id.admin;
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
        bottomNavView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.admin ->{
                    Log.d("test","admin")
                    val intent= Intent(this,LoginActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> {
                    /*Log.d("test","students")
                    val intent= Intent(this,AdminList::class.java)
                    startActivity(intent)*/
                    false
                }
            }
        }
    }
}