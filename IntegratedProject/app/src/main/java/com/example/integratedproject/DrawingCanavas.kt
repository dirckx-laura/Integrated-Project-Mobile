package com.example.integratedproject

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.*
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.activity_drawing_canavas.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream


class DrawingCanavas : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawing_canavas)

        signaturePad.setPenColor(Color.BLACK)

        ClearButton.setOnClickListener {
            signaturePad.clearView()
        }
        saveButton.setOnClickListener {
            val signature = signaturePad.signatureBitmap;
            val coordinaten = ArrayList<Int>();
            val savedPath = bitmapToFile(signature)
            Toast.makeText(this, "Signature saved into: " + savedPath.encodedPath, Toast.LENGTH_LONG).show()
            setResult(Activity.RESULT_OK)
            signaturePad.points.forEach{Log.d("Coordinaten", it.toString())}
          //  signaturePad.points.toString()
            finish()


        }



    }
    private fun bitmapToFile(bitmap:Bitmap): Uri {
        // Get the context wrapper
        val wrapper = ContextWrapper(applicationContext)

        // Initialize a new file instance to save bitmap object
        var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
        file = File(file,"saved-signature.jpg")

        try{
            // Compress the bitmap and save in jpg format
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
            stream.flush()
            stream.close()
        }catch (e: IOException){
            e.printStackTrace()
        }

        // Return the saved bitmap uri
        return Uri.parse(file.absolutePath)
    }



}