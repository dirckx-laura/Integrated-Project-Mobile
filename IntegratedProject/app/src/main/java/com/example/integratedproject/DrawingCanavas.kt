package com.example.integratedproject




import android.Manifest
import android.annotation.SuppressLint

import android.os.Build

import androidx.annotation.RequiresApi
import androidx.constraintlayout.motion.widget.Debug.getLocation


import kotlinx.android.synthetic.main.activity_drawing_canavas.*
import java.time.LocalDateTime
import java.util.*


import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper

import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*



class DrawingCanavas : AppCompatActivity() {

    val RequestPermissionCode = 1
    var mLocation: Location? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        val context = this
        val db = DataBaseHandler(context)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawing_canavas)



        ClearButton.setOnClickListener {
            signaturePad.clearCanavas()
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
            getLastLocation()
            Log.d("Result:" , getCityName(51.093240,4.376850))
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
    fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermission()
            Log.d("selfper: " , ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION).toString())
            Log.d("Packetmanager : " , PackageManager.PERMISSION_GRANTED.toString())

        } else {
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    mLocation = location
                    if (location != null) {
                        Log.d("Test1: " , location.latitude.toString())
                        Log.d("Test1: " , location.longitude.toString())
                    }
                }
        }
    }
     private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            RequestPermissionCode
        )
    }


    private fun getCityName(lat: Double,long: Double):String{
        var adress = ""
        var geoCoder = Geocoder(this, Locale.getDefault())
        var Adress = geoCoder.getFromLocation(lat,long,3)
        adress = Adress.get(0).getAddressLine(0)
        Log.d("Debug:","Adress" + adress)
        return adress
    }

}