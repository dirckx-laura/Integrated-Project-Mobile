package com.example.integratedproject

import android.content.ContentValues
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.lang.StringBuilder
import java.util.ArrayList

class AdminDBHelper(context: Context) :SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable="CREATE TABLE $TABLE_ADMIN(_id Integer PRIMARY KEY, PASSWORD VARCHAR(255), SALT VARCHAR(255))"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_ADMIN")
        onCreate(db)
    }
    fun getRowCount():Long{
        val db =this.readableDatabase
        val count=DatabaseUtils.queryNumEntries(db, TABLE_ADMIN)
        db.close()
        return count

    }
    fun insertPassword(pwd:String){
        val db = this.writableDatabase
        val values = ContentValues()
        val salt=PasswordUtils.generateSalt()
        val saltString=salt.toString()
        Log.d("pwd","cleartext:$pwd")
        Log.d("pwd","salt:$saltString")
        val hashedPwd=PasswordUtils.hash(pwd,salt)
        val hashedStr=String(hashedPwd)
        Log.d("pwd","hashed:$hashedStr")
        values.put("SALT",salt)
        values.put("PASSWORD", hashedPwd)
        db.insert(TABLE_ADMIN, null, values)
        db.close()
    }
    fun getPassword(): String {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_ADMIN", null)
        var pwd=""
        if (cursor.moveToFirst()) {
            pwd=String(cursor.getBlob(cursor.getColumnIndex("PASSWORD")))

        }
        db.close()
        return pwd
    }
    private fun getPasswordByteArray(): ByteArray {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_ADMIN", null)
        lateinit var pwd:ByteArray
        if (cursor.moveToFirst()) {
            pwd=cursor.getBlob(cursor.getColumnIndex("PASSWORD"))

        }
        db.close()
        return pwd
    }
    private fun getSalt(): ByteArray {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_ADMIN", null)
        lateinit var salt:ByteArray
        if (cursor.moveToFirst()) {
            salt=cursor.getBlob(cursor.getColumnIndex("SALT"))

        }
        db.close()
        return salt
    }

    fun comparePassword(inputPwd:String):Boolean{
        return PasswordUtils.isExpectedPassword(inputPwd,getSalt(),getPasswordByteArray())
    }
    companion object {
        private const val DATABASE_NAME = "admin.db"
        private const val TABLE_ADMIN = "admin"
        private const val DATABASE_VERSION = 5
    }
}