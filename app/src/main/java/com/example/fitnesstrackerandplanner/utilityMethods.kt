package com.example.fitnesstrackerandplanner

import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import java.time.Duration

//TODO: Implement methods that are might be used in anywhere in the app.
//fun calculateBMI(weight:Double,height:Double){}
// fun caloriesBurned(){}
// and such ...


@Composable
fun ProgressBar(progress: Float,modifier: Modifier=Modifier,color:Color,trackColor:Color,strokeCap: StrokeCap) {
    LinearProgressIndicator(progress = progress,modifier=modifier,color=color,trackColor=trackColor,strokeCap=strokeCap)
}

@Preview
@Composable
fun PreviewProgressBar() {
    ProgressBar(progress = 0.5f,color=Color.Green,modifier=Modifier, strokeCap = StrokeCap.Round, trackColor = Color.LightGray)
}

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