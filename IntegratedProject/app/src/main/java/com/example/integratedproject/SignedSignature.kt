package com.example.integratedproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import kotlinx.android.synthetic.main.activity_signed_signature.*


class SignedSignature : AppCompatActivity() {
    private val REQUEST_CODE=2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signed_signature)
    }

    override fun onResume() {
        super.onResume()
        val coords=intent.getStringExtra("coords")
        var coordsListString=coords?.trim()?.splitToSequence(',')
            ?.filter { it.isNotEmpty() }
            ?.toList()
        var coordsListFloat=ArrayList<Float>()
        coordsListString?.forEach { it->
            coordsListFloat.add(it.toFloat())
        }
        val height = this.resources.displayMetrics.heightPixels
        val width = this.resources.displayMetrics.widthPixels
        signaturePadAdmin.Redraw(coordsListFloat,width,height)
    }
}