package com.example.fitnesstrackerandplanner



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
    //
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
    }

//TODO: Implement methods that are might be used in anywhere in the app.
    //fun calculateBMI(weight:Double,height:Double){}
   // fun caloriesBurned(){}
    // and such ...



}

fun main(){
   // print(utilityMethods.isHealthyBMI(22.5f, true))
    print(utilityMethods.calculateBMI(52,166))

}