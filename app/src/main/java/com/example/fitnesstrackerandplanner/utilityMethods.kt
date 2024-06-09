package com.example.fitnesstrackerandplanner

import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitnesstrackerandplanner.ui.theme.ButtonPurple
import org.w3c.dom.Text
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