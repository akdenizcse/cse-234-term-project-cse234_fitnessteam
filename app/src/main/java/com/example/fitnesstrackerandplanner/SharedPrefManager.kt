package com.example.fitnesstrackerandplanner

import android.content.Context

class SharedPrefManager(context: Context){
    private val sharedPreferences=context.getSharedPreferences("FitnessApp",Context.MODE_PRIVATE)

    fun saveCurrentUser(userName:String){
        val editor=sharedPreferences.edit()
        editor.putString("currentUser",userName)
        editor.apply()
    }
    fun getCurrentUser():String?{
        return sharedPreferences.getString("currentUser",null)
    }


}