package com.example.fitnesstrackerandplanner

data class ExerciseType(val targetArea:String, val exercises:List<String>? = null){
    val abs:ExerciseType=ExerciseType("Abs")
    val arm:ExerciseType=ExerciseType("Arm")
    val legs:ExerciseType=ExerciseType("Legs")
    val shoulders:ExerciseType=ExerciseType("Shoulders")
    val ches:ExerciseType=ExerciseType("Chest")
    val belly:ExerciseType=ExerciseType("Belly")
    val back:ExerciseType=ExerciseType("Back")
 //TODO: Query ile dolduracak içini her ExerciseType listini

}



//TODO: INITIALIZE VALUES AFTER IMPLEMENTING DATABASE

