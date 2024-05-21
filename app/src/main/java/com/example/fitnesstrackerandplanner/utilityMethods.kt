package com.example.fitnesstrackerandplanner

import java.time.Duration

//TODO: Implement methods that are might be used in anywhere in the app.
//fun calculateBMI(weight:Double,height:Double){}
// fun caloriesBurned(){}
// and such ...



enum class WeightClassification(){
    Underweight,
    Normal,
    Overweight,
    Obese,
    MorbidObese()
}

class utilityMethods {
companion object{
    fun calculateBMI(weightKg:Short,heightCm:Short):Float{
        var personalBMI:Float
        val heightM:Float=heightCm*0.01f
        personalBMI=(((weightKg/((heightM)*(heightM))))).toFloat()
        return Math.round(personalBMI* 10.0f)/10.0f
    }
    //f
    fun isHealthyBMI(personalBMI:Float,Gender:Boolean):WeightClassification{
        if(personalBMI>14){
            return WeightClassification.MorbidObese
        }
        return WeightClassification.Overweight
    }
    fun calculateIdealWeight(heightCm:Short,isFemale:Boolean):Float{
        if(isFemale){
          return (45.5f+ 2.3f*((heightCm/2.54f)-60))

        }
        return (50+ 2.3f*((heightCm/2.54f)-60))
    }
    fun calculateHowManyCaloriesBurned(metValue:Double,weightKg:Double,durationMin: Double):Double{
        val caloriesBurnedPerMin=metValue*weightKg*0.0175
        return caloriesBurnedPerMin*durationMin
    }

    }



}

fun main(){
   // print(utilityMethods.isHealthyBMI(22.5f, true))
    print(utilityMethods.calculateBMI(52,166))

}