package com.example.integratedproject

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
val DATABASENAME = "MY DATABASE"
val TABLENAME = "Students"
val COL_NAME = "name"
val COL_ID = "id"
val COL_STUDENTENNUMMER = "studentennummer"
val COL_LOCATION = "location"
class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASENAME, null,
    1) {
    override fun onCreate(db: SQLiteDatabase?) {

        val createTable = "CREATE TABLE " + TABLENAME + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_NAME + " VARCHAR(256)," + COL_STUDENTENNUMMER + " VARCHAR(256)," + COL_LOCATION +   "BLOB);"

        db?.execSQL(createTable)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //onCreate(db);
    }
    fun insertData(name: String, studentennummer: String) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_NAME, name)
        contentValues.put(COL_STUDENTENNUMMER, studentennummer)
        //contentValues.put(COL_LOCATION, location)

        val result = database.insert(TABLENAME, null, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
    fun readData(): MutableList<Student> {
        val list: MutableList<Student> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLENAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val student = Student()
                student.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                student.name = result.getString(result.getColumnIndex(COL_NAME))
                student.studentennummer = result.getString(result.getColumnIndex(COL_STUDENTENNUMMER))
               // student.location = result.getString(result.getColumnIndex(COL_LOCATION))
                list.add(student)
            }
            while (result.moveToNext())
        }
        return list
    }
}