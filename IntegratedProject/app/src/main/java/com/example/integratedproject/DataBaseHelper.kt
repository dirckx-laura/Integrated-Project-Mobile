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

class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASENAME, null,
    1) {
    override fun onCreate(db: SQLiteDatabase?) {

        val createTableStudents = "CREATE TABLE " + TABLENAMESTUDENTS + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+COL_STUDENTENNUMMER+" INTEGER," + COL_NAME + " VARCHAR(256));"
        val createTableRegistration = "CREATE TABLE " + TABLENAMEREGISTRATION + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_LOCATION + " VARCHAR(256)," + COL_SIGNATUREPOINTS + " VARCHAR(256));"

        db?.execSQL(createTableStudents)
        db?.execSQL(createTableRegistration)

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
    fun insertDataRegistration(location: String, signaturePoints: String) {
        val database = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(COL_LOCATION, location)
        contentValues.put(COL_SIGNATUREPOINTS, signaturePoints)

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

                list.add(registration)
            }
            while (result.moveToNext())
        }
        return list
    }
}