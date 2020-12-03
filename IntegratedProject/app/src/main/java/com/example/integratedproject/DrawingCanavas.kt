package com.example.integratedproject


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_drawing_canavas.*

class DrawingCanavas : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

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
            signaturePad.getCoords()
            signaturePad.saveCoords()
            signaturePad.clearCanavas()

            val intent = Intent(this, UserList::class.java)
            startActivity(intent)
        }
    }
}