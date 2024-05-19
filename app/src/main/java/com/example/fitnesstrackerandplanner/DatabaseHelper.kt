package com.example.fitnesstrackerandplanner

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "FitnessApp.db"
        private const val DATABASE_VERSION = 2 //MUST BE UPDATED AFTER EVERY CHANGE

        // User Table
        const val TABLE_USER = "user"
        const val COLUMN_USER_ID = "id"
        const val COLUMN_USER_FIRST_NAME = "firstName"
        const val COLUMN_USER_LAST_NAME = "lastName"
        const val COLUMN_USER_EMAIL = "email"
        const val COLUMN_USER_NAME = "userName"
        const val COLUMN_USER_PASSWORD = "password"

        // Personal Info Table
        const val TABLE_PERSONAL_INFO = "personalInfo"
        const val COLUMN_INFO_ID = "id"
        const val COLUMN_INFO_USER_ID = "userId"
        const val COLUMN_INFO_ADDRESS = "address"
        const val COLUMN_INFO_PHONE = "phone"

        // Create Table SQL Statements
        private const val CREATE_USER_TABLE = (
                "CREATE TABLE $TABLE_USER (" +
                        "$COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "$COLUMN_USER_FIRST_NAME TEXT," +
                        "$COLUMN_USER_LAST_NAME TEXT," +
                        "$COLUMN_USER_EMAIL TEXT," +
                        "$COLUMN_USER_NAME TEXT," +
                        "$COLUMN_USER_PASSWORD BIGINT)"
                )

        private const val CREATE_PERSONAL_INFO_TABLE = (
                "CREATE TABLE $TABLE_PERSONAL_INFO (" +
                        "$COLUMN_INFO_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "$COLUMN_INFO_USER_ID INTEGER, " +
                        "$COLUMN_INFO_ADDRESS TEXT, " +
                        "$COLUMN_INFO_PHONE TEXT, " +
                        "FOREIGN KEY($COLUMN_INFO_USER_ID) REFERENCES $TABLE_USER($COLUMN_USER_ID))"
                )
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_USER_TABLE)
        db?.execSQL(CREATE_PERSONAL_INFO_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PERSONAL_INFO")
        onCreate(db)
    }

    fun addUser(
        db: SQLiteDatabase?,
        context: Context,
        firstName: String,
        lastName: String?,
        email: String,
        userName: String,
        password: String
    ) : Int {
        val time by lazy { Calendar.getInstance().time }
        if(db==null){
            Log.e("DatabaseHelper","@$time--DATABASE IS NULL!!!")
            Toast.makeText(context,"Database is null",Toast.LENGTH_SHORT).show()
            return -2
        }
        db.let { database ->
            val contentValues = ContentValues().apply {
                put(DatabaseHelper.COLUMN_USER_FIRST_NAME, firstName)
                put(DatabaseHelper.COLUMN_USER_LAST_NAME, lastName)
                put(DatabaseHelper.COLUMN_USER_EMAIL, email)
                put(DatabaseHelper.COLUMN_USER_NAME, userName)
                put(DatabaseHelper.COLUMN_USER_PASSWORD, password)
            }
            val newRowId = database.insert(DatabaseHelper.TABLE_USER, null, contentValues)
            if (newRowId != -1L) {
                Log.d("UserCreation", "@$time--User created successfully: $userName")
                Toast.makeText(context, "User added successfully!", Toast.LENGTH_SHORT).show()
                return 0
            } else {
                Toast.makeText(context, "Failed to add user!", Toast.LENGTH_SHORT).show()
                return -1
            }

        }
    }

     /*   fun getUser(db: SQLiteDatabase, search: String): String? {
            val searchStr = "%$search%"
            val cursor = db.rawQuery(
                "SELECT ${DatabaseHelper.COLUMN_USER_NAME} FROM ${DatabaseHelper.TABLE_USER} WHERE ${DatabaseHelper.COLUMN_USER_FIRST_NAME} LIKE ? OR ${DatabaseHelper.COLUMN_USER_LAST_NAME} LIKE ?",
                arrayOf(searchStr, searchStr)
            )
            var userName: String? = null
            if (cursor.moveToFirst()) {
                userName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_NAME))
            }
            cursor.close()
            return userName

        }*/


}
