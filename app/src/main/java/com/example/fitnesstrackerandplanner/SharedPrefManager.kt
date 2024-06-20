package com.example.fitnesstrackerandplanner

import Exercise
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
class SharedPrefManager(context: Context){
    private val sharedPreferences=context.getSharedPreferences("FitnessApp",Context.MODE_PRIVATE)
    private val gson = Gson()


    fun addCurrentUserCaloriesConsumed(calories: Int) {
        val currentCalories = getCurrentUserCaloriesConsumed()
        val editor = sharedPreferences.edit()
        editor.putInt("currentUserCaloriesConsumed", currentCalories + calories)
        editor.apply()
    }

    fun removeCurrentUserCaloriesConsumed() {
        val editor = sharedPreferences.edit()
        editor.remove("currentUserCaloriesConsumed")
        editor.apply()
    }

    fun getCurrentUserCaloriesConsumed(): Int {
        return sharedPreferences.getInt("currentUserCaloriesConsumed", 0)
    }

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
    fun saveCurrentUserDailyCaloriesBurned(userCaloriesBurned: Double) {
        val editor = sharedPreferences.edit()
        editor.putFloat("userDailyCaloriesBurned", userCaloriesBurned.toFloat())
        editor.apply()
    }
    fun getCurrentUserDailyCaloriesBurned():Float{
        return sharedPreferences.getFloat("userDailyCaloriesBurned",0.0f)
    }





    fun clearAllValues() {
        val editor = sharedPreferences.edit()
        editor.clear() // Clear all values
        editor.apply()
    }
    fun saveCurrentUserWeight(userWeight: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("currentUserWeight", userWeight)
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
    fun saveCurrentUserAge(userAge:Int){
        val editor= sharedPreferences.edit()
        editor.putInt("currentUserAge",userAge)
        editor.apply()
    }
    fun saveCurrentUserGender(userGender:Boolean){
        val editor= sharedPreferences.edit()
        editor.putBoolean("currentUserGender",userGender)
        editor.apply()
    }
    fun getCurrentUserGender():Boolean{
        return sharedPreferences.getBoolean("currentUserGender",false)
    }
    fun getCurrentUserAge():Int{
        return sharedPreferences.getInt("currentUserAge",0)
    }
    fun getCurrentUsername():String?{
        return sharedPreferences.getString("currentUsername",null)
    }

    fun getSelectedExercises(): List<Int> {
        val serializedExercises = sharedPreferences.getString("selected_exercises", "")
        return serializedExercises?.split(",")?.map { it.toIntOrNull() }?.filterNotNull() ?: emptyList()
    }

    fun saveSelectedExercises(exerciseIds: List<Int>) {
        val editor = sharedPreferences.edit()
        editor.putString("selected_exercises", exerciseIds.joinToString(","))
        editor.apply()
    }
    fun getSelectedExercisesGO():List<Int>{
        val serializedExercises = sharedPreferences.getString("selected_exercises_go", "")
        return serializedExercises?.split(",")?.map { it.toIntOrNull() }?.filterNotNull() ?: emptyList()
    }

    fun saveSelectedExercisesGO(exerciseIds: List<Int>) {
        val editor = sharedPreferences.edit()
        editor.putString("selected_exercises_go", exerciseIds.joinToString(","))
        editor.apply()
    }
    fun removeSubExerciseFromSelectedGO(subExerciseId: Int) {
        val selectedExercises = getSelectedExercisesGO().toMutableList()
        selectedExercises.remove(subExerciseId)
        saveSelectedExercisesGO(selectedExercises)
    }
    fun clearSelectedExercisesGO() {
        val editor = sharedPreferences.edit()
        editor.remove("selected_exercises_go")
        editor.apply()
    }
    fun clearSelectedExercises() {
        val editor = sharedPreferences.edit()
        editor.remove("selected_exercises")
        editor.apply()
    }


    fun saveAllExercises(exercises: List<Exercise>) {
        val editor = sharedPreferences.edit()
        val json = gson.toJson(exercises)
        editor.putString("exercises", json)
        editor.apply()
    }

    fun getAllExercises(): List<Exercise> {
        val json = sharedPreferences.getString("exercises", "")
        if (json.isNullOrEmpty()) {
            return emptyList()
        }
        return gson.fromJson(json, object : TypeToken<List<Exercise>>() {}.type)
    }

    fun clearAllExercises() {
        val editor = sharedPreferences.edit()
        editor.remove("exercises")
        editor.apply()
    }









}