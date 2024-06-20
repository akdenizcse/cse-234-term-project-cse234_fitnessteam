package com.example.fitnesstrackerandplanner

import Exercise
import SubExercise
import android.content.Context
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.fitnesstrackerandplanner.ui.theme.CharcoalGray
import com.ozcanalasalvar.wheelview.SelectorOptions
import com.ozcanalasalvar.wheelview.WheelView
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDateTime
import java.util.Date
import java.util.Locale
import kotlin.math.log

//TODO: Implement methods that are might be used in anywhere in the app.
//fun calculateBMI(weight:Double,height:Double){}
// fun caloriesBurned(){}
// and such ...
interface SlideGestureListener {
    fun onSlideStart()
    fun onSlide(offset: Float)
    fun onSlideEnd()
}

enum class WeightClassification(){
    Underweight,
    Normal,
    Overweight,
    Obese,
    MorbidObese()
}
fun calculateFittyPoints(
    gender: Boolean,
    age: Int,
    weight: Int,
    height: Int,
    exerciseTime: Long,  // Exercise time in seconds
    caloriesBurned: Int
): Int {
    val basePoints = 10
    val genderMultiplier = if (gender) 1.1 else 1.0
    val ageFactor = when {
        age < 18 -> 1.2
        age < 30 -> 1.0
        age < 50 -> 0.8
        else -> 0.6
    }
    val bmi = weight / ((height / 100.0) * (height / 100.0))
    val bmiFactor = when {
        bmi < 18.5 -> 0.8
        bmi < 24.9 -> 1.0
        bmi < 29.9 -> 0.9
        else -> 0.7
    }

    // Calculate fitty points
    val fittyPoints = basePoints * genderMultiplier * ageFactor * bmiFactor * exerciseTime

    // Return the points as an integer
    return fittyPoints.toInt()
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
            subExerciseName = "Incline Dumbbell Press",
            description = "Lie on the table and push the dumbbells up.",
            videoUrl = "https://example.com/videos/incline_dumbbell_press.mp4",
            groupName = "Chest",
            exerciseIDs = 1,
            approximateCaloriesPerSecond = 0.12 // Example value
        ),
        SubExercise(
            subExerciseName = "Dumbbell Chest Fly",
            description = "Lie on the table and spread your arms with dumbbells.",
            videoUrl = "https://example.com/videos/dumbbell_chest_fly.mp4",
            groupName = "Chest",
            exerciseIDs = 1,
            approximateCaloriesPerSecond = 0.10 // Example value
        )
    )

    val legSubExercises = listOf(
        SubExercise(
            subExerciseName = "Squat",
            description = "Stand with your feet shoulder-width apart and squat down.",
            videoUrl = "https://example.com/videos/squat.mp4",
            groupName = "Legs",
            exerciseIDs = 2,
            approximateCaloriesPerSecond = 0.15 // Example value
        ),
        SubExercise(
            subExerciseName = "Lunge",
            description = "Step forward and lower your hips until both knees are bent.",
            videoUrl = "https://example.com/videos/lunge.mp4",
            groupName = "Legs",
            exerciseIDs = 2,
            approximateCaloriesPerSecond = 0.14 // Example value
        )
    )

    val absSubExercises = listOf(
        SubExercise(
            subExerciseName = "Crunch",
            description = "Lie on your back and lift your shoulders off the floor.",
            videoUrl = "https://example.com/videos/crunch.mp4",
            groupName = "Abs",
            exerciseIDs = 3,
            approximateCaloriesPerSecond = 0.08 // Example value
        ),
        SubExercise(
            subExerciseName = "Plank",
            description = "Hold your body in a straight line from head to heels.",
            videoUrl = "https://example.com/videos/plank.mp4",
            groupName = "Abs",
            exerciseIDs = 3,
            approximateCaloriesPerSecond = 0.05 // Example value
        )
    )

    chestExercises.addExercise(chestSubExercises)
    legExercises.addExercise(legSubExercises)
    absExercises.addExercise(absSubExercises)

    exercises.add(chestExercises)
    exercises.add(legExercises)
    exercises.add(absExercises)

    return exercises
}


fun List<Exercise>.getSubExercisesByIds(subExerciseIds: List<Int>): List<SubExercise> {
    val result = mutableListOf<SubExercise>()

    forEach { exercise ->
        exercise.subExercises.forEach { subExercise ->
            if (subExercise.subExerciseID in subExerciseIds) {
                result.add(subExercise)
            }
        }
    }

    return result
}
fun List<Exercise>.getSubExerciseById(subExerciseId: Int): SubExercise? {
    return flatMap { it.subExercises }.find { it.subExerciseID == subExerciseId }
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



@Composable
fun MyDropDownMenu(modifier:Modifier=Modifier) {

    var dropControl by remember { mutableStateOf(false) }
    var selectIndex by remember { mutableIntStateOf(0) }
    var countryList = listOf("Past 1 Day","Past 1 Week","Past 1 Month","Past 1 Year")

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedCard(modifier = Modifier.padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .wrapContentWidth()
                    .height(50.dp)
                    .padding(5.dp)
                    .clickable {
                        dropControl = true
                    }) {

                Text(text = countryList[selectIndex])
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24),
                    contentDescription = ""
                )

            }

            DropdownMenu(expanded = dropControl, onDismissRequest = { dropControl = false}) {

                countryList.forEachIndexed { index, strings ->
                    DropdownMenuItem(
                        text = { Text(text = strings) },
                        onClick = {
                            dropControl = false
                            selectIndex = index
                        })
                }

            }
        }
    }
}
@Composable
fun MySessionDropDownMenu(
    modifier: Modifier = Modifier,
    selectedInterval: String,
    onIntervalChanged: (String) -> Unit
) {
    var dropControl by remember { mutableStateOf(false) }
    val intervalList = listOf("Last 1 Day", "Last 7 Days", "Last 1 Month", "Last 1 Year")

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .clickable { dropControl = true }
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(5.dp)
            ) {
                Text(
                    text = selectedInterval, // Display current selected interval
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_arrow_drop_down_24),
                    contentDescription = "Dropdown Arrow",
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }

        DropdownMenu(
            expanded = dropControl,
            onDismissRequest = { dropControl = false }
        ) {
            intervalList.forEachIndexed { index, interval ->
                DropdownMenuItem(
                    onClick = {
                        dropControl = false
                        onIntervalChanged(interval) // Call callback with selected interval
                    },
                    text = { Text(text = interval) }
                )
            }
        }
    }
}

@Composable
fun MyGenderDropDownMenu(modifier: Modifier = Modifier, selectedGender: MutableState<Boolean>) {
    var dropControl by remember { mutableStateOf(false) }
    var selectIndex by remember { mutableStateOf(-1) }
    val genderList = listOf("Gender", "Male", "Female")

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedCard(modifier = Modifier.padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .wrapContentWidth()
                    .background(CharcoalGray)
                    .height(50.dp)
                    .padding(5.dp)
                    .clickable {
                        dropControl = true
                    }
            ) {
                Text(text = genderList[selectIndex + 1])
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24),
                    contentDescription = ""
                )
            }

            DropdownMenu(expanded = dropControl, onDismissRequest = { dropControl = false }) {
                genderList.forEachIndexed { index, gender ->
                    DropdownMenuItem(
                        text = { Text(text = gender) },
                        onClick = {
                            dropControl = false
                            selectIndex = index - 1
                            if (index == 2) {
                                selectedGender.value = true
                            } else if (index == 1) {
                                selectedGender.value = false
                            }
                        }
                    )
                }
            }
        }
    }
}

fun getRecommendedCaloriesTaken(age: Int): Int {
    return when (age) {
        in 2..3 -> 1200 // Average for toddlers
        in 4..8 -> 1600 // Average for young children
        in 9..13 -> 2200 // Average for preteens
        in 14..18 -> 2800 // Average for teenagers
        in 19..30 -> 2600 // Average for young adults
        in 31..50 -> 2400 // Average for middle-aged adults
        else -> 2000 // Average for older adults
    }
}

@Composable
fun WheelViewSelector(
    selectedAge: MutableState<Int>
){

    val ageList= (0..120).toList()
    WheelView(
        modifier = Modifier,
        itemSize = DpSize(60.dp,30.dp),
        selection = 0,
        itemCount = 120,
        selectorOption = SelectorOptions(color = CharcoalGray),
        rowOffset = 1,
        onFocusItem = { index ->
            selectedAge.value=index
        },
        content = {index->
                Text("${ageList.get(index)}",modifier=Modifier.background(Color.Transparent))

        })
}
@Composable
fun SleepWheelViewSelector(
    sleepAmount: MutableState<Int>
){

    val hourList= (0..24).toList()
    WheelView(
        modifier = Modifier,
        itemSize = DpSize(60.dp,30.dp),
        selection = 0,
        itemCount = 25,
        selectorOption = SelectorOptions(color = CharcoalGray),
        rowOffset = 1,
        onFocusItem = { index ->
            sleepAmount.value=index
        },
        content = {index->
            Text("${hourList.get(index)}",modifier=Modifier.background(Color.Transparent))

        })
}


@Composable
fun MyAgeDropDownMenu(modifier: Modifier = Modifier, selectedAge: Int, onClick: () -> Unit) {
    var dropControl by remember { mutableStateOf(false) }
    val ageDisplay = if (selectedAge == 0) "Enter Age" else "Age: $selectedAge"

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedCard(modifier = Modifier.padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(CharcoalGray)
                    .wrapContentWidth()
                    .height(50.dp)
                    .padding(5.dp)
                    .clickable {
                        dropControl = true
                        onClick()
                    }
            ) {
                Text(text = ageDisplay)
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24),
                    contentDescription = ""
                )
            }

            DropdownMenu(expanded = dropControl, onDismissRequest = { dropControl = false}) {


            }
        }
    }
}

fun formatTimeSeconds(totalSeconds: Long): String {
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "${minutes}:${String.format("%02d", seconds)}"
}
fun formatDate(date: Date): String {
    val sdf = SimpleDateFormat("d MMM HH:mm - yyyy", Locale.ENGLISH)
    return sdf.format(date)
}
fun Date.toLocalDateTime(): LocalDateTime {
    return Instant.ofEpochMilli(this.time).atZone(ZoneId.systemDefault()).toLocalDateTime()
}
fun calculateDailyRecommendedCaloriesToBurn(gender:Boolean,age:Int,weight:Int,height:Int):Double{
    var bmr:Double
    if(gender){
        bmr=447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age)
    }
    else{
       bmr= 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age)
    }
    val tdee=bmr*1.55
    return tdee*0.2
}
@Composable
fun formatDouble2DecimalPlaces(number: Double):String {
    val formattedNumber = String.format("%.2f", number)
    return formattedNumber
}
fun getRecommendedSleepHours(age: Int): Int {
    return when {
        age in 0..2 -> 12 // Infants (0-2 years) recommended sleep hours
        age in 3..5 -> 10 // Toddlers (3-5 years) recommended sleep hours
        age in 6..12 -> 9 // Children (6-12 years) recommended sleep hours
        age in 13..17 -> 8 // Teenagers (13-17 years) recommended sleep hours
        age in 18..64 -> 7 // Adults (18-64 years) recommended sleep hours
        age >= 65 -> 7 // Older adults (65 years and older) recommended sleep hours
        else -> 7 // Default recommendation for any age if not specified
    }
}
fun initiailizeProfileElements(sharedPrefManager:SharedPrefManager,navController: NavHostController):List<ProfilePageElement>{
    var userInformation:ProfilePageElement=ProfilePageElement("User Information",{navController.navigate(Screens.userInfoPage.screen)})
    var updateUserMeasurements:ProfilePageElement=ProfilePageElement("Update user measurements",{navController.navigate(Screens.updateUserMeasurements.screen)})
    var contactUs:ProfilePageElement=ProfilePageElement("Contact us",{})

    var appInfo:ProfilePageElement=ProfilePageElement("Application Information",{navController.navigate(Screens.ApplicationInfo.screen)})
    var logOut:ProfilePageElement=ProfilePageElement("Log Out",{ onLogOut(sharedPrefManager,navController) })

    val profilePageElements:List<ProfilePageElement> = listOf(userInformation,updateUserMeasurements,
        contactUs,appInfo, logOut)
    return profilePageElements
}
fun onLogOut(sharedPrefManager: SharedPrefManager,navController: NavController){
    sharedPrefManager.clearAllValues()
    navController.navigate(Screens.LoginPage.screen)
}
data class ProfilePageElement(
    val name:String,
    val onClick: () -> Unit
)