package com.example.integratedproject

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.*
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.get
import androidx.core.view.drawToBitmap
import kotlinx.android.synthetic.main.activity_drawing_canvas.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import kotlin.math.sign


class DrawingCanavas : AppCompatActivity() {

    /* var coordinaten = ArrayList<TimedPoint>()
     @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)*/
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawing_canvas)

        ClearButton.setOnClickListener {
            signaturePad2.clearCanavas()
        }

        Redraw.setOnClickListener {

            signaturePad2.getCoords()
            signaturePad.Redraw(signaturePad2.getDrawing())


            //val canvas: Canvas
            //canvas.drawPoint(signaturePad2.coordlist.get(0),signaturePad2.coordlist.get(1),Paint())
        }

        saveButton.setOnClickListener {
            signaturePad2.saveCoords()
        }

    }




}