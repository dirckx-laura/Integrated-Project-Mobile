package com.example.integratedproject




import android.Manifest
import android.R.attr.country
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
            //Log.d("coords", coordinaten.toString())
            var difference = 0.0;
            val first = listOf(605.9375,386.03125,605.9375,386.03125,605.9375,386.03125,659.9932,427.89594,723.6486,464.6267,781.86755,481.1595,832.76733,480.79364,873.2014,468.11426,899.8254,449.159,922.10126,415.7365,933.4609,367.86725,939.1671,330.0483,945.69214,289.28647,946.9375,256.8131,940.7864,233.74991,940.7864,233.74991,940.7864,233.74991,923.5657,219.0625,923.5657,219.0625,897.441,232.328,877.93805,256.23355,856.96704,288.74597,826.4387,352.15973,800.69226,425.94006,756.89606,520.62726,720.80896,579.5534,704.3889,610.2202,686.7586,624.95654,686.7586,624.95654,658.327,637.8643,658.327,637.8643,628.05676,634.7377,628.05676,634.7377,607.9375,627.3604,607.9375,627.3604,588.41095,601.6834,583.42633,577.21,579.9375,538.46844,589.38995,497.14282,615.91754,460.7951,632.70325,443.28113,632.70325,443.28113,650.0195,446.89575,663.14685,464.56244,682.50476,511.02155,709.0129,553.1599,729.5607,584.0174,749.08405,602.19086,768.78296,619.89355,768.78296,619.89355,792.9375,626.03125,792.9375,626.03125,792.9375,626.03125,813.57434,615.22327,829.9375,612.0625,829.9375,612.0625)

                val second = listOf(465.9375,370.04688,465.9375,370.04688,508.3041,382.71454,569.8523,416.4626,746.789,472.31458,830.60474,474.0017,896.9047,466.67914,931.5223,448.77252,964.176,361.59302,971.9375,319.72964,971.3594,299.02805,971.3594,299.02805,971.3594,299.02805,952.29553,274.82898,936.097,267.2962,915.35144,264.0625,893.6604,264.0625,893.6604,264.0625,852.8191,311.0904,817.77167,382.90643,774.4577,468.52887,733.8564,525.04694,702.96936,563.79376,665.7732,587.94086,642.81714,599.4586,642.81714,599.4586,614.2077,600.4355,595.7374,595.1312,578.9053,587.1691,578.9053,587.1691,562.01276,562.30975,560.18567,543.48846,570.1633,523.8367,593.8526,503.9796,617.02795,498.0625,649.4336,508.78656,671.80524,528.1977,696.67975,562.7104,718.7572,609.43756,742.8414,652.0118,761.47705,685.5728,779.5462,719.7393,798.9099,748.8685,925.9375,741.0625,925.9375,741.0625)
            first.zip(second).forEach {pair ->
                difference = ((pair.component1() - pair.component2()).toDouble())

            }
            Log.d("coords", difference.toString())


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