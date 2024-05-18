package com.example.fitnesstrackerandplanner

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "FitnessApp.db"
        private const val DATABASE_VERSION = 1

        // User Table
        const val TABLE_USER = "user"
        const val COLUMN_USER_ID = "id"
        const val COLUMN_USER_NAME = "name"
        const val COLUMN_USER_EMAIL = "email"
        const val COLUMN_USER_PASSWORD="password"
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
                        "$COLUMN_USER_NAME TEXT, " +
                        "$COLUMN_USER_EMAIL TEXT)"
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
}
