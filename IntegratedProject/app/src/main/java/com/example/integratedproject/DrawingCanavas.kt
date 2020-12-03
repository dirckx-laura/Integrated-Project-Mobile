package com.example.integratedproject


import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_drawing_canavas.*
import java.time.LocalDate
import java.time.LocalDateTime

class DrawingCanavas : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        val context = this
        val db = DataBaseHandler(context)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawing_canavas)

        ClearButton.setOnClickListener {
            signaturePad.clearCanavas()
        }

        // Hertekenen

        /* signaturePad.Redraw().setOnClickListener {
             signaturePad.getCoords()
             signaturePad.Redraw(signaturePad.getDrawing())
         }*/

        saveButton.setOnClickListener {
            signaturePad.saveCoords()
            val coordinaten =  signaturePad.getCoords()
            val datum = LocalDateTime.now().toString()
            val sNr=intent.getStringExtra("sNr")
            val location = "Test Location"
            db.insertDataRegistration(location,coordinaten, sNr.toString() ,datum)
            signaturePad.clearCanavas()
            val intent = Intent(this, UserList::class.java)
            startActivity(intent)
        }
    }
}