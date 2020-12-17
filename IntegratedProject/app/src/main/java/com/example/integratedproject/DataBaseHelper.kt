package com.example.integratedproject

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
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

class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASENAME, null,
    1) {
    override fun onCreate(db: SQLiteDatabase?) {

        val createTableStudents = "CREATE TABLE " + TABLENAMESTUDENTS + " (" + COL_STUDENTENNUMMER + " INTEGER PRIMARY KEY," + COL_NAME + " VARCHAR(256));"
        val createTableRegistration = "CREATE TABLE " + TABLENAMEREGISTRATION + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_STUDENTENNUMMER + " INTEGER, " + COL_LOCATION + " VARCHAR(256)," + COL_SIGNATUREPOINTS + " VARCHAR(256), " + COL_DATUM + " VARCHAR(256));"
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
        //val resizedSignature=resizeSignature(signaturePoints,studentennummer)
        contentValues.put(COL_LOCATION, location)
        contentValues.put(COL_SIGNATUREPOINTS, signaturePoints)
        contentValues.put(COL_STUDENTENNUMMER, studentennummer)
        contentValues.put(COL_DATUM, datum);

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

                list.add(registration)
            }
            while (result.moveToNext())
        }
        return list
    }
    fun doesMasterSignatureExist(studentennummer: String):Boolean{
        val db = this.readableDatabase
        val query = "Select * TABLE$TABLEMASTERSIGNATURE WHERE $COL_STUDENTENNUMMER = $studentennummer"
        val result=db.rawQuery(query,null)
        return result.count>=1
    }
    private fun resizeSignature(newSignature: String, studentennummer: String):String{
        var multiplyX:Float
        var multiplyY:Float
        var resizedString=""
        var resizedSignatureList=ArrayList<Float>()
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
        multiplyX=masterSignatureListFloat[0]/newSignatureListFloat[0]
        multiplyY=masterSignatureListFloat[1]/newSignatureListFloat[1]
        var teller=0
        newSignatureListFloat.forEach { it->
            if(teller%2==0){
                resizedSignatureList.add(newSignatureListFloat[teller]*multiplyX)
            }
            else{
                resizedSignatureList.add(newSignatureListFloat[teller]*multiplyY)
            }
        }
        resizedSignatureList.forEach { it->
            resizedString+="${it},"
        }
        return resizedString.substring(0,resizedString.length-1)
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
}