package com.example.fitnesstrackerandplanner



enum class WeightClassification(){
    Underweight,
    Normal,
    Overweight,
    Obese,
    MorbidObese()



}
class utilityMethods {

    fun calculateBMI(weightKg:Short,heightCm:Short):Float{
        var personalBMI:Float
        personalBMI=(((weightKg/((heightCm)*(heightCm))).toFloat()))
        return personalBMI
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



fun main(){
    val testInst=utilityMethods()
    print(testInst.isHealthyBMI(22.5f,true))
}

