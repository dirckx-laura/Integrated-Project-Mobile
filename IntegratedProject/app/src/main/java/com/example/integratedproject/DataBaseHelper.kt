package com.example.integratedproject

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import kotlin.math.roundToInt

val DATABASENAME = "iWasThereDatabase"
val TABLENAMESTUDENTS = "Students"
val COL_NAME = "name"
val COL_STUDENTENNUMMER = "studentennummer"
val TABLENAMEREGISTRATION = "Registration"
val COL_ID = "id"
val COL_LOCATION = "location"
val COL_SIGNATUREPOINTS = "signaturepoints"
val COL_DATUM = "datum"
val TABLEMASTERSIGNATURE="mastersignature"
val COL_MASTERCOORDS="coordinates"
val COL_GELIJKENIS="gelijkenis"

class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASENAME, null,
    1) {
    override fun onCreate(db: SQLiteDatabase?) {

        val createTableStudents = "CREATE TABLE " + TABLENAMESTUDENTS + " (" + COL_STUDENTENNUMMER + " INTEGER PRIMARY KEY," + COL_NAME + " VARCHAR(256));"
        val createTableRegistration = "CREATE TABLE " + TABLENAMEREGISTRATION + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_STUDENTENNUMMER + " INTEGER, " + COL_LOCATION + " VARCHAR(256)," + COL_SIGNATUREPOINTS + " VARCHAR(256), " + COL_DATUM + " VARCHAR(256), "+ COL_GELIJKENIS+" INTEGER);"
        val createMasterSignature="CREATE TABLE ${TABLEMASTERSIGNATURE} (${COL_STUDENTENNUMMER} INTEGER PRIMARY KEY, ${COL_MASTERCOORDS} VARCHAR(256))"
        db?.execSQL(createTableStudents)
        db?.execSQL(createTableRegistration)
        db?.execSQL(createMasterSignature)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //onCreate(db);
    }

    //insert into STUDENTS Table
    fun insertData(name: String, studentennummer: String) {
        val database = this.writableDatabase

            val contentValues = ContentValues()
            contentValues.put(COL_STUDENTENNUMMER, studentennummer)
            contentValues.put(COL_NAME, name)

            val result = database.insert(TABLENAMESTUDENTS, null, contentValues)
            if (result == (0).toLong()) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
            }


    }

    //Read Data from Students Table
    fun readData(): MutableList<Student> {
        val list: MutableList<Student> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLENAMESTUDENTS"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val student = Student()

                student.name = result.getString(result.getColumnIndex(COL_NAME))
                student.studentennummer = result.getString(result.getColumnIndex(COL_STUDENTENNUMMER)).toInt()

                list.add(student)
            }
            while (result.moveToNext())
        }
        return list
    }


    //Insert data into Registraion Table
    fun insertDataRegistration(location: String, signaturePoints: String, studentennummer: String, datum: String) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        val resizedSignature=resizeSignature(signaturePoints,studentennummer)
        //Log.d("db",resizedSignature)
        contentValues.put(COL_LOCATION, location)
        contentValues.put(COL_SIGNATUREPOINTS, resizedSignature[0])
        contentValues.put(COL_STUDENTENNUMMER, studentennummer)
        contentValues.put(COL_DATUM, datum);
        contentValues.put(COL_GELIJKENIS,resizedSignature[1])

        val result = database.insert(TABLENAMEREGISTRATION, null, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    //Read data from Registration Table
    fun readDataRegistration(): MutableList<Registration> {
        val list: MutableList<Registration> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLENAMEREGISTRATION"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val registration = Registration()

                registration.location = result.getString(result.getColumnIndex(COL_LOCATION))
                registration.signaturePoints = result.getString(result.getColumnIndex(
                    COL_SIGNATUREPOINTS))
                registration.datum = result.getString(result.getColumnIndex(COL_DATUM)).toString()
                registration.studentennummer = result.getString(result.getColumnIndex(
                    COL_STUDENTENNUMMER)).toInt();
                registration.percentage=result.getString(result.getColumnIndex(COL_GELIJKENIS)).toInt()

                list.add(registration)
            }
            while (result.moveToNext())
        }
        return list
    }

    fun readDataRegistrationByStudentNumber(studentennummer : String): MutableList<Registration>{
        val list: MutableList<Registration> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLENAMEREGISTRATION WHERE $COL_STUDENTENNUMMER = $studentennummer"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val registration = Registration()

                registration.location = result.getString(result.getColumnIndex(COL_LOCATION))
                registration.signaturePoints = result.getString(result.getColumnIndex(
                    COL_SIGNATUREPOINTS))
                registration.datum = result.getString(result.getColumnIndex(COL_DATUM)).toString()
                registration.studentennummer = result.getString(result.getColumnIndex(
                    COL_STUDENTENNUMMER)).toInt();
                registration.percentage=result.getString(result.getColumnIndex(COL_GELIJKENIS)).toInt()

                list.add(registration)
            }
            while (result.moveToNext())
        }
        return list
    }
    fun doesMasterSignatureExist(studentennummer: String):Boolean{
        val db = this.readableDatabase
        val query = "Select * FROM $TABLEMASTERSIGNATURE WHERE $COL_STUDENTENNUMMER = $studentennummer"
        val result=db.rawQuery(query,null)
        return result.count>=1
    }
    private fun resizeSignature(newSignature: String, studentennummer: String):ArrayList<String>{
        var multiplyX:Float
        var multiplyY:Float
        var addX:Float
        var addY:Float
        var resizedString=""
        var mostLeftX:Float
        var mostRightX:Float
        var mostTopY:Float
        var mostDownY:Float
        var mostLeftXNew:Float
        var mostRightXNew:Float
        var mostTopYNew:Float
        var mostDownYNew:Float
        var resizedSignatureList=ArrayList<Float>()
        var resizedSignatureList2=ArrayList<Float>()
        val masterSignature=readMasterSignature(studentennummer)
        var masterSignatureListString=masterSignature?.trim()?.splitToSequence(',')
            ?.filter { it.isNotEmpty() }
            ?.toList()
        var masterSignatureListFloat=ArrayList<Float>()
        masterSignatureListString?.forEach { it->
            masterSignatureListFloat.add(it.toFloat())
        }
        var newSignatureListString=newSignature?.trim()?.splitToSequence(',')
            ?.filter { it.isNotEmpty() }
            ?.toList()
        var newSignatureListFloat=ArrayList<Float>()
        newSignatureListString?.forEach { it->
            newSignatureListFloat.add(it.toFloat())
        }
        mostLeftX=masterSignatureListFloat[0]
        mostRightX=masterSignatureListFloat[0]
        mostTopY=masterSignatureListFloat[1]
        mostDownY=masterSignatureListFloat[1]
        var tellerMaster=0
        masterSignatureListFloat.forEach { it->
            if(tellerMaster%2==0){
                if(mostLeftX>masterSignatureListFloat[tellerMaster]){
                    mostLeftX=masterSignatureListFloat[tellerMaster]
                }
                if(mostRightX<masterSignatureListFloat[tellerMaster]){
                    mostRightX=masterSignatureListFloat[tellerMaster]
                }
            }
            else{
                if(mostDownY>masterSignatureListFloat[tellerMaster]){
                    mostDownY=masterSignatureListFloat[tellerMaster]
                }
                if(mostTopY<masterSignatureListFloat[tellerMaster]){
                    mostTopY=masterSignatureListFloat[tellerMaster]
                }
            }
            tellerMaster++
        }
        mostLeftXNew=masterSignatureListFloat[0]
        mostRightXNew=masterSignatureListFloat[0]
        mostTopYNew=masterSignatureListFloat[1]
        mostDownYNew=masterSignatureListFloat[1]
        var teller2=0
        newSignatureListFloat.forEach { it->
            if(teller2%2==0){
                if(mostLeftXNew>newSignatureListFloat[teller2]){
                    mostLeftXNew=newSignatureListFloat[teller2]
                }
                if(mostRightXNew<newSignatureListFloat[teller2]){
                    mostRightXNew=newSignatureListFloat[teller2]
                }
            }
            else{
                if(mostDownYNew>newSignatureListFloat[teller2]){
                    mostDownYNew=newSignatureListFloat[teller2]
                }
                if(mostTopYNew<newSignatureListFloat[teller2]){
                    mostTopYNew=newSignatureListFloat[teller2]
                }
            }
            teller2++
        }
        var distanceYMaster=mostTopY-mostDownY
        var distanceXMaster=mostRightX-mostLeftX
        var distanceYNew=mostTopYNew-mostDownYNew
        var distanceXNew=mostRightXNew-mostLeftXNew
        multiplyX=distanceXMaster/distanceXNew
        multiplyY=distanceYMaster/distanceYNew
        var tellerNew=0
        newSignatureListFloat.forEach { it->
            if(tellerNew%2==0){
                resizedSignatureList.add((newSignatureListFloat[tellerNew])*multiplyX)
            }
            else{
                resizedSignatureList.add((newSignatureListFloat[tellerNew])*multiplyY)
            }
            tellerNew++
        }
        tellerNew=0
        addX=masterSignatureListFloat[0]-resizedSignatureList[0]
        addY=masterSignatureListFloat[1]-resizedSignatureList[1]
        resizedSignatureList.forEach { it->
            if(tellerNew%2==0){
                resizedSignatureList2.add(resizedSignatureList[tellerNew]+addX)
            }
            else{
                resizedSignatureList2.add(resizedSignatureList[tellerNew]+addY)
            }
            tellerNew++
        }
        resizedSignatureList2.forEach { it->
            resizedString+="${it},"
        }
        Log.d("masterLength:",masterSignatureListFloat.size.toString())
        Log.d("resizedLength: ",resizedSignatureList2.size.toString())
        var total=masterSignatureListFloat.size
        var goodCoords=0
/*        if(masterSignatureListFloat.size<=resizedSignatureList2.size){
            total=masterSignatureListFloat.size
            *//*for(i in 0 until masterSignatureListFloat.size step 2){
                if((resizedSignatureList2[i] in (masterSignatureListFloat[i]-20f)..(masterSignatureListFloat[i]+20f))&&(resizedSignatureList2[i+1] in (masterSignatureListFloat[i+1]-5f)..(masterSignatureListFloat[i+1]+5f))){
                    goodCoords++
                }
            }*//*
        }
        else{
            total=resizedSignatureList2.size
            for(i in 0 until resizedSignatureList2.size step 2){
                if((resizedSignatureList2[i] in (masterSignatureListFloat[i]-20f)..(masterSignatureListFloat[i]+20f))&&(resizedSignatureList2[i+1] in (masterSignatureListFloat[i+1]-5f)..(masterSignatureListFloat[i+1]+5f))){
                    goodCoords++
                }
            }
        }*/
        for(i in 0 until resizedSignatureList2.size step 2){
            for( i2 in 0 until masterSignatureListFloat.size step 2){
                if(resizedSignatureList2[i] in (masterSignatureListFloat[i2]-30f)..(masterSignatureListFloat[i2]+30f)){
                    if(resizedSignatureList2[i+1] in (masterSignatureListFloat[i2+1]-30f)..(masterSignatureListFloat[i2+1]+30f)){
                        goodCoords++
                        break
                    }
                }
            }
        }
        var percentage=((goodCoords.toFloat()/(total.toFloat()/2))*100).roundToInt()
        if(percentage>=100 &&masterSignature!=newSignature){
            percentage=99
        }
        var resultList=ArrayList<String>()
        resultList.add(resizedString.substring(0,resizedString.length-1))
        resultList.add(percentage.toString())
        return resultList
    }
    private fun readMasterSignature(studentennummer: String):String{
        val db = this.readableDatabase
        var masterSignature=""
        val query = "Select * from $TABLEMASTERSIGNATURE WHERE $COL_STUDENTENNUMMER = $studentennummer"
        val result=db.rawQuery(query,null)
        if(result.moveToFirst()){
            masterSignature=result.getString(result.getColumnIndex(COL_MASTERCOORDS)).toString()
        }
        return masterSignature
    }
    fun insertMasterSignature(signaturePoints: String, studentennummer: String){
        val database = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(COL_MASTERCOORDS, signaturePoints)
        contentValues.put(COL_STUDENTENNUMMER, studentennummer)

        val result = database.insert(TABLEMASTERSIGNATURE, null, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
    fun deleteStudent(studentennummer: String){
        val db=this.writableDatabase
        db.delete(TABLENAMESTUDENTS,COL_STUDENTENNUMMER+"="+studentennummer,null)
        db.delete(TABLEMASTERSIGNATURE,COL_STUDENTENNUMMER+"="+studentennummer,null)
        db.delete(TABLENAMEREGISTRATION,COL_STUDENTENNUMMER+"="+studentennummer,null)
    }
    fun dropDatabase(){
        val db=this.writableDatabase
        db.delete(TABLENAMESTUDENTS,"",null)
        db.delete(TABLEMASTERSIGNATURE,"",null)
        db.delete(TABLENAMEREGISTRATION,"",null)
    }
}