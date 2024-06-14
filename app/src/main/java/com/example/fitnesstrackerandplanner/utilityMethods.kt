package com.example.fitnesstrackerandplanner

import Exercise
import SubExercise
import android.content.Context
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
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
import androidx.compose.ui.platform.LocalContext
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
fun getExerciseByID(exercises: List<Exercise>, id: Int): Exercise {
    for (exercise in exercises) {
        if (exercise.exerciseID == id) {
            return exercise
        }
        for (subExercise in exercise.subExercises) {
            if (subExercise.exerciseID == id) {
                return subExercise
            }
        }
    }
    return Exercise("NoEX",0)
}



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
private fun calculateIdealWeight(heightCm:Short,isFemale:Boolean):Float{
    if(isFemale){
      return (45.5f+ 2.3f*((heightCm/2.54f)-60))

    }
    return (50+ 2.3f*((heightCm/2.54f)-60))
}
fun getIdealWeight(heightCm: Short,isFemale: Boolean,context: Context):String{
    val sharedPrefManager=SharedPrefManager(context =context)
    val currentWeight=sharedPrefManager.getCurrentUserWeight()
    val difference=calculateIdealWeight(heightCm,isFemale)-currentWeight
    if(difference>0){
        return "You are ${difference}kg behind of your ideal weight."
    }else if(difference<0){
        return "You are ${difference}kg ahead of your ideal weight."
    }
    else{
        return "You are at your ideal weight. Keep it up!"
    }
}
fun calculateHowManyCaloriesBurned(metValue:Double,weightKg:Double,durationMin: Double):Double{
    val caloriesBurnedPerMin=metValue*weightKg*0.0175
    return caloriesBurnedPerMin*durationMin
}



fun initializeExercises(): List<Exercise> {
    val exercises = mutableListOf<Exercise>()

    // Initialize exercise groups
    val chestExercises = Exercise("Chest", 1)
    val legExercises = Exercise("Legs", 2)
    val absExercises = Exercise("Abs", 3)

    // Initialize sub-exercises
    val chestSubExercises = listOf(
        SubExercise(
            exerciseName = "Incline Dumbbell Press",
            description = "Lie on the table and push the dumbbells up.",
            videoUrl = "https://example.com/videos/incline_dumbbell_press.mp4",
            groupName = "Chest",
            exerciseIDs = 1,
            approximateCaloriesPerSecond = 0.12 // Example value
        ),
        SubExercise(
            exerciseName = "Dumbbell Chest Fly",
            description = "Lie on the table and spread your arms with dumbbells.",
            videoUrl = "https://example.com/videos/dumbbell_chest_fly.mp4",
            groupName = "Chest",
            exerciseIDs = 1,
            approximateCaloriesPerSecond = 0.10 // Example value
        )
    )

    val legSubExercises = listOf(
        SubExercise(
            exerciseName = "Squat",
            description = "Stand with your feet shoulder-width apart and squat down.",
            videoUrl = "https://example.com/videos/squat.mp4",
            groupName = "Legs",
            exerciseIDs = 2,
            approximateCaloriesPerSecond = 0.15 // Example value
        ),
        SubExercise(
            exerciseName = "Lunge",
            description = "Step forward and lower your hips until both knees are bent.",
            videoUrl = "https://example.com/videos/lunge.mp4",
            groupName = "Legs",
            exerciseIDs = 2,
            approximateCaloriesPerSecond = 0.14 // Example value
        )
    )

    val absSubExercises = listOf(
        SubExercise(
            exerciseName = "Crunch",
            description = "Lie on your back and lift your shoulders off the floor.",
            videoUrl = "https://example.com/videos/crunch.mp4",
            groupName = "Abs",
            exerciseIDs = 3,
            approximateCaloriesPerSecond = 0.08 // Example value
        ),
        SubExercise(
            exerciseName = "Plank",
            description = "Hold your body in a straight line from head to heels.",
            videoUrl = "https://example.com/videos/plank.mp4",
            groupName = "Abs",
            exerciseIDs = 3,
            approximateCaloriesPerSecond = 0.05 // Example value
        )
    )

    // Add sub-exercises to their respective exercise groups
    chestExercises.addExercise(chestSubExercises)
    legExercises.addExercise(legSubExercises)
    absExercises.addExercise(absSubExercises)

    // Add exercise groups to the main list
    exercises.add(chestExercises)
    exercises.add(legExercises)
    exercises.add(absExercises)

    return exercises
}


fun List<Exercise>.getSubExercisesByIds(subExerciseIds: List<Int>): List<SubExercise> {
    val result = mutableListOf<SubExercise>()

    // Iterate through each Exercise in the list
    forEach { exercise ->
        // Iterate through each SubExercise in the current Exercise
        exercise.subExercises.forEach { subExercise ->
            // Check if the subExerciseID is in the list of desired IDs
            if (subExercise.subExerciseID in subExerciseIds) {
                result.add(subExercise)
            }
        }
    }

    return result
}

//Health Connect Utility Methods
fun dateTimeWithOffsetOrDefault(time: Instant, offset: ZoneOffset?): ZonedDateTime =
    if (offset != null) {
        ZonedDateTime.ofInstant(time, offset)
    } else {
        ZonedDateTime.ofInstant(time, ZoneId.systemDefault())
    }

fun Duration.formatTime() = String.format(
    "%02d:%02d:%02d",
    this.toHours() % 24,
    this.toMinutes() % 60,
    this.seconds % 60
)

fun Duration.formatHoursMinutes() = String.format(
    "%01dh%02dm",
    this.toHours() % 24,
    this.toMinutes() % 60
)





