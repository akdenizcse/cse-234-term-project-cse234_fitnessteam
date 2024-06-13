package com.example.fitnesstrackerandplanner

import android.content.Context

class SharedPrefManager(context: Context){
    private val sharedPreferences=context.getSharedPreferences("FitnessApp",Context.MODE_PRIVATE)

    fun saveCurrentUserFirstName(userFirstName:String){
        val editor=sharedPreferences.edit()
        editor.putString("currentUserFirstName",userFirstName)
        editor.apply()
    }
    fun getCurrentUserFirstName():String?{
        return sharedPreferences.getString("currentUserFirstName",null)
    }
    fun saveCurrentUserHeight(userHeight: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("currentUserHeight", userHeight)
        editor.apply()
    }

    fun getCurrentUserHeight(): Int {
        return sharedPreferences.getInt("currentUserHeight", 0)
    }

    fun saveCurrentUserWeight(userWeight: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("currentUserWeight", userWeight)
        editor.apply()
    }
    fun clearAllValues() {
        val editor = sharedPreferences.edit()
        editor.clear() // Clear all values
        editor.apply()
    }

    fun getCurrentUserWeight(): Int {
        return sharedPreferences.getInt("currentUserWeight", 0)
    }
    fun saveCurrentUsername(username:String){
        val editor=sharedPreferences.edit()
        editor.putString("currentUsername",username)
        editor.apply()
    }
    fun getCurrentUsername():String?{
        return sharedPreferences.getString("currentUsername",null)
    }



}