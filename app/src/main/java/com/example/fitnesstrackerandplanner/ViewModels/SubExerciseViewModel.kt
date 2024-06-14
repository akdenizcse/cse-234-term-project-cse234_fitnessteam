package com.example.fitnesstrackerandplanner.ViewModels

import androidx.lifecycle.ViewModel

class SubExerciseViewModel:ViewModel(){
    private val _selectedSubExercises=mutableListOf<Int>()
    val selectedSubExercises:List<Int> =_selectedSubExercises

    fun addSubExercise(id:Int){
        if(!_selectedSubExercises.contains(id)){
            _selectedSubExercises.add(id)
        }
    }
    fun removeSubExercise(id:Int){
        _selectedSubExercises.remove(id)
    }
    fun clearSubExercises(){
        _selectedSubExercises.clear()
    }
    val isConfirmButton:Boolean
        get()=_selectedSubExercises.isNotEmpty()
}
