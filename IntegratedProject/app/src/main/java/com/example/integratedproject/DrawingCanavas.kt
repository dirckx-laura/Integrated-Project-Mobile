package com.example.integratedproject




import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_drawing_canavas.*
import kotlinx.android.synthetic.main.registration_list.*
import java.io.IOException
import java.time.LocalDateTime
import java.util.*


class DrawingCanavas : AppCompatActivity() {

    var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private val REQUEST_CODE = 100
    var locatie: String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        val context = this
        val db = DataBaseHandler(context)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawing_canavas)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

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

            getLastLocation();
            db.insertDataRegistration(locatie, coordinaten, sNr.toString(), datum)
            signaturePad.clearCanavas()
            val intent = Intent(this, UserList::class.java)
            startActivity(intent)
        }
    }
    private fun getLastLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("Class","Starting")
            fusedLocationProviderClient!!.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        try {
                            val geocoder = Geocoder(this, Locale.getDefault())
                            val addresses: List<Address> =
                                geocoder.getFromLocation(location.latitude, location.longitude, 1)
                            locatie = addresses[0].getAddressLine(0).toString()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }
        } else {
            askPermission()
        }
    }
    private fun askPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_CODE
        )
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            } else {
                Toast.makeText(
                    this,
                    "Please provide the required permission",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}