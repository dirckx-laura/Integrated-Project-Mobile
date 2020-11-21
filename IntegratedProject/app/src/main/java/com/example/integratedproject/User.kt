package com.example.integratedproject

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime

class User {

    var id: Int = 0
    var name: String = ""
    var studentNr:String= ""
    @RequiresApi(Build.VERSION_CODES.O) // Moet er staat anders error
    var date:LocalDateTime = LocalDateTime.now()
    var coordinaten: ArrayList<Float>? = null;
    val handtekening:Boolean = false;


}
