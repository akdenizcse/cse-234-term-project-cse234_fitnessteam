package com.example.fitnesstrackerandplanner

import FirebaseHelper
import android.database.sqlite.SQLiteDatabase
import android.icu.util.Calendar
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.DefaultStrokeLineWidth
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.fitnesstrackerandplanner.ui.theme.*
import kotlinx.coroutines.launch
//TODO: ADD NOTIFICATIONS AND CLOCK AND INTENTS TO SEND FEEDBACKS
@Composable
fun Home(navController:NavHostController) {
    val hour by lazy { Calendar.getInstance().get(Calendar.HOUR_OF_DAY) }
    val context = LocalContext.current
    val sharedPrefManager by lazy { SharedPrefManager(context) }
    val userName = sharedPrefManager.getCurrentUsername()
    val firebaseHelper by lazy { FirebaseHelper() }
    var userAge by remember{ mutableStateOf(0) }
    val isDayTime: Boolean = hour in 6..18
    val greeting: String = if (isDayTime) "Good Morning, $userName!" else "Good Evening, $userName!"
    var showSleepDialog by remember { mutableStateOf(false) }

    val icon = if (isDayTime) painterResource(id = R.drawable.icons8_sunny_48)
    else painterResource(id = R.drawable.icons8_moon_48)

    val waterDropLogo = painterResource(id = R.drawable.water_drop)
    val fireLogo = painterResource(id = R.drawable.fire)
    val nightLogo = painterResource(id = R.drawable.nightlogo)
    val foodLogo = painterResource(id = R.drawable.foodlogo)
    val dumbellLogo = painterResource(id = R.drawable.gymdumbell)

    var waterProgress = remember { mutableStateOf(0f) }
    val hoursSlept = remember { mutableStateOf(0) }
    val height = sharedPrefManager.getCurrentUserHeight()
    val weight = sharedPrefManager.getCurrentUserWeight()
    var userGender by remember { mutableStateOf(false) }
    var genderString by remember{mutableStateOf("")}
    var waterDrank by remember { mutableStateOf(0.0f) }
    var totalHealthPoints by remember { mutableStateOf(0.0f) }
    var tempTotalHealthPoints=0.0f
    var dailyCaloriesBurned:Double by remember{mutableStateOf(0.0)}
    var dailyRecommendedCaloriesBurned by remember { mutableStateOf((0.0)) }
    var dailyCaloriesConsumed by remember{ mutableStateOf(0) }
    val recommmendedCaloriesConsumed= getRecommendedCaloriesTaken(userAge)
    if(waterDrank>=2.5f){
        tempTotalHealthPoints+=0.25f
    }
    if(dailyCaloriesBurned>=dailyRecommendedCaloriesBurned)
        tempTotalHealthPoints=0.25f
    if(hoursSlept.value >= getRecommendedSleepHours(userAge)){
        tempTotalHealthPoints+=0.25f
    }
    if(dailyCaloriesConsumed>=recommmendedCaloriesConsumed) {
        tempTotalHealthPoints+=0.25f
    }
        totalHealthPoints=tempTotalHealthPoints

    LaunchedEffect(Unit) {
        if (userName != null) {
            firebaseHelper.deleteOldWaterData(userName) { success ->
                if (!success) {
                    Log.w("Home", "Failed to delete old water data")
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        if (userName != null) {
            firebaseHelper.deleteOldSleepData(userName) { success ->
                if (!success) {
                    Log.w("Home", "Failed to delete old sleep data")
                }
            }
        }
    }
    LaunchedEffect(userName) {
        if (!userName.isNullOrEmpty()) {
            val waterConsumed = firebaseHelper.fetchWaterConsumption(userName)
            Log.d("HomeFireStore","Fetched water consumption ${waterConsumed}")
            waterDrank = waterConsumed ?: 0.0f
        }
    }
    LaunchedEffect(userName) {
        if (userName != null) {
           firebaseHelper.fetchSleepData(userName){fetchedData->
               Log.d("HomeFireStore","Fetched hours slept ${fetchedData}")
               hoursSlept.value=fetchedData.toInt()

           }


        }
        }

    LaunchedEffect(userName) {
        if (!userName.isNullOrEmpty()) {
            firebaseHelper.fetchAge(userName){ value->
                Log.d("HomeFireStore","Fetched age ${value}")
                if (value != null) {
                    userAge=value
                    sharedPrefManager.saveCurrentUserAge(userAge)
                }
            }
        }
    }

    LaunchedEffect(userName) {
        if (!userName.isNullOrEmpty()) {
            firebaseHelper.fetchGender(userName){ value->
                Log.d("HomeFireStore","Fetched gender ${value}")
                if (value != null) {
                    userGender=value
                    if(userGender==true){
                        genderString="Female"
                    }
                    else{
                        genderString="Male"
                    }
                    sharedPrefManager.saveCurrentUserGender(userGender)
                }
            }
        }
    }
    LaunchedEffect(userName) {
        if (!userName.isNullOrEmpty()) {
           firebaseHelper.fetchDailyBurnedCalories(userName){cal->
               Log.d("HomeFireStore","Fetched calories ${cal}")
               if (cal != null) {
                    dailyCaloriesBurned=cal
                   sharedPrefManager.saveCurrentUserDailyCaloriesBurned(dailyCaloriesBurned)
               }

           }
        }
    }
    LaunchedEffect(Unit) {
        dailyRecommendedCaloriesBurned= calculateDailyRecommendedCaloriesToBurn(userGender,userAge,weight,height)
    }

    LaunchedEffect(userName) {
        if (userName != null) {
            firebaseHelper.fetchCalorieData(userName) { value ->
                  dailyCaloriesConsumed=value.toInt()
                sharedPrefManager.addCurrentUserCaloriesConsumed(dailyCaloriesConsumed)
            }
        }
    }
    LaunchedEffect(Unit){
        if(userName!=null){
            firebaseHelper.deleteOldCalorieData(userName){success->
                if(success){
                    Log.d("HomeFireStore","Successfully deleted calorie data after 1 day")
                }
                else{
                    Log.e("HomeFireStore","Could not delete calorie data after 1 day, servers might be busy.")
                }
            }
        }
    }
    LaunchedEffect(userName) {
        if (userName != null) {
            firebaseHelper.fetchHeight(userName) { value ->
                if (value != null) {
                    sharedPrefManager.saveCurrentUserHeight(value)
                } else {
                    Log.e(
                        "FirebaseHelper",
                        "Height can not be fetched, probably such user is not signed"
                    )
                }

                // Once height is fetched, fetch weight
                firebaseHelper.fetchWeight(userName) { value ->
                    if (value != null) {
                        sharedPrefManager.saveCurrentUsername(userName)
                        Toast.makeText(context, "Successfully logged in!", Toast.LENGTH_SHORT)
                            .show()
                        sharedPrefManager.saveCurrentUserWeight(value)
                    } else {
                        Log.e(
                            "FirebaseHelper",
                            "Weight can not be fetched, probably such user is not signed"
                        )
                    }
                }
            }
        }
    }

    Surface(color = DeepNavyBlue, modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            item {
                Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(Modifier.padding(15.dp)) {
                        Text(
                            text = greeting,
                            fontSize = 25.sp,
                            modifier = Modifier.weight(3f),
                            color = Color.DarkGray,
                            fontWeight = FontWeight.ExtraBold,
                            style = TextStyle(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color.Magenta, Color.Cyan)
                                )
                            )
                        )
                        Image(
                            painter = icon,
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .weight(1f)
                                .size(65.dp)
                        )
                    }

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            modifier = Modifier.size(350.dp, 100.dp),
                            colors = CardDefaults.cardColors(containerColor = CharcoalGray),
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.elevatedCardElevation(12.dp),
                            border = BorderStroke(0.3.dp, Brush.linearGradient(
                                colors = listOf(Color.Magenta, Color.Cyan)
                            ))
                        ) {
                            Column(
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Height: ${height} cm",
                                        modifier = Modifier.weight(1f),
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "Gender: $genderString",
                                        modifier = Modifier.padding(start = 8.dp),
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                                Text(
                                    text = "Weight: ${weight} kg",
                                    modifier = Modifier.padding(top = 8.dp),
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Age: $userAge",
                                        modifier = Modifier.weight(1f),
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "BMI: ${calculateBMI(weightKg = weight.toShort(), heightCm = height.toShort())}",
                                        modifier = Modifier.padding(start = 8.dp),
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }
                        }

                        Spacer(modifier=Modifier.size(2.dp))
                        Surface(
                            modifier = Modifier.size(350.dp, 125.dp),
                            color = Silver,
                            shape = RoundedCornerShape(16.dp),
                            tonalElevation = 8.dp,
                            border = BorderStroke(
                                width=0.4.dp,brush=Brush.linearGradient(
                                    colors=listOf(Color.Cyan,Color.Magenta)
                                )
                                )
                            )
                         {
                            Column {
                                Text(
                                    text = if (totalHealthPoints != 1f) "You have completed ${(totalHealthPoints * 100).toInt()}% of your daily goals, congratulations!"
                                    else "You have completed all of your daily goals! Keep it up.",
                                    fontWeight = FontWeight.Medium,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .padding(vertical = 10.dp, horizontal = 8.dp)
                                        .weight(1f),
                                    color = Color.Black,
                                    lineHeight = 30.sp
                                )
                                ProgressBar(
                                    progress = totalHealthPoints, modifier = Modifier
                                        .size(250.dp, 15.dp)
                                        .align(Alignment.CenterHorizontally)
                                        .weight(0.35f)
                                        .padding(bottom = 8.dp),
                                    color = when (totalHealthPoints) {
                                        in 0f..0.25f -> VividRed
                                        in 0.25f..0.5f -> Orange
                                        in 0.5f..0.75f -> Gold
                                        else -> Color.Green
                                    },
                                    trackColor = DeepNavyBlue,
                                    strokeCap = StrokeCap.Round
                                )
                            }
                        }

                        Text(
                            text = "FitAI",
                            fontWeight = FontWeight.Bold,
                            color = Purple500,
                            fontSize = 18.sp
                        )
                      AnimatedAIBorderCard(navController = navController)


                    }

                    Column {
                        Text(
                            "Daily Objectives",
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(vertical = 5.dp),
                            color = Purple500,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            // DAILY WATER TRACKING
                            Surface(
                                modifier = Modifier.size(350.dp, 75.dp),
                                color = CharcoalGray,
                                shape = RoundedCornerShape(16.dp),
                                tonalElevation = 8.dp,
                                border = BorderStroke(0.3.dp, Brush.linearGradient(
                                    colors = listOf(Color.Magenta, Color.Cyan)
                                )
                                )
                            ) {
                                Row(
                                    modifier = Modifier,
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = waterDropLogo,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(60.dp, 60.dp)
                                            .weight(1f)
                                    )
                                    Text(
                                        text = "Drink Water\n${waterDrank}/2.5L",
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 16.sp,
                                        color = Eggshel,
                                        modifier = Modifier.weight(1f)
                                    )
                                    AnimatedButton(
                                        onClick = {
                                            if (waterDrank < 10f) {
                                                if (waterDrank < 2.5f) {
                                                    waterDrank += 0.25f
                                                    waterProgress.value += 0.2f
                                                }
                                                if (waterDrank == 2.5f) {
                                                    waterDrank += 0.25f
                                                } else {
                                                    waterDrank += 0.25f
                                                }
                                                if (userName != null) {
                                                    firebaseHelper.updateWaterConsumption(userName, waterDrank) { success ->
                                                        if (!success) {
                                                            Toast.makeText(context, "Failed to update water data", Toast.LENGTH_SHORT).show()
                                                        }
                                                    }
                                                }
                                            } else {
                                                Toast.makeText(context, "That is unhealthy to drink that much water", Toast.LENGTH_SHORT).show()
                                            }
                                        },
                                        modifier = Modifier.weight(0.8f),
                                        color = Cerulean,
                                        text = "DRINK",
                                        fontSize = 12.5.sp
                                    )
                                    Column(modifier = Modifier.weight(0.6f)) {
                                        if (waterDrank < 2.5f) {
                                            CircularProgressBar(
                                                progress = waterProgress.value,
                                                strokeCap = StrokeCap.Round,
                                                modifier = Modifier
                                                    .size(60.dp)
                                                    .weight(0.6f)
                                                    .padding(start = 8.dp, end = 5.dp, top = 5.dp)
                                            )
                                            Text(
                                                text = "${(waterProgress.value * 100).toInt()}%",
                                                fontSize = 12.sp,
                                                modifier = Modifier.padding(start = 17.dp, bottom = 2.dp),
                                                fontWeight = FontWeight.Medium,
                                                color = Color.White
                                            )
                                        } else {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = null,
                                                tint = Color.Green,
                                                modifier = Modifier.align(Alignment.CenterHorizontally)
                                            )
                                        }
                                    }
                                }
                            }

                            // CALORIES BURNT TRACKER
                            Surface(
                                modifier = Modifier.size(350.dp, 75.dp),
                                color = CharcoalGray,
                                shape = RoundedCornerShape(16.dp),
                                tonalElevation = 8.dp,
                                border = BorderStroke(0.3.dp, Brush.linearGradient(
                                    colors = listOf(Color.Magenta, Color.Cyan)
                                )
                                )
                            ) {
                                Row(
                                    modifier = Modifier,
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = fireLogo,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(60.dp, 60.dp)
                                            .weight(1f)
                                    )
                                    Text(
                                        text = "Calories burnt\n${formatDouble2DecimalPlaces(number = dailyCaloriesBurned)}/${formatDouble2DecimalPlaces(dailyRecommendedCaloriesBurned)} cals",
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 14.sp,
                                        color = Eggshel,
                                        modifier = Modifier.weight(1f)
                                    )
                                    AnimatedButton(
                                        onClick = {navController.navigate(Screens.Goals.screen)},
                                        modifier = Modifier.weight(0.8f),
                                        text = "DO",
                                        color = Cerulean
                                    )

                                    Column(modifier = Modifier.weight(0.6f)) {
                                        if(((dailyCaloriesBurned/dailyRecommendedCaloriesBurned) *100).toInt()<100){
                                        CircularProgressBar(
                                            progress = (dailyCaloriesBurned.toFloat()/dailyRecommendedCaloriesBurned.toFloat()),
                                            strokeCap = StrokeCap.Round,
                                            modifier = Modifier
                                                .size(60.dp)
                                                .weight(0.6f)
                                                .padding(start = 8.dp, end = 5.dp, top = 5.dp)
                                        )
                                        Text(
                                            text = "${((dailyCaloriesBurned.toFloat()/dailyRecommendedCaloriesBurned) *100).toInt()}%",
                                            fontSize = 12.sp,
                                            modifier = Modifier.padding(start = 13.dp, bottom = 2.dp,end=1
                                                .dp),
                                            fontWeight = FontWeight.Medium,
                                            color = Color.White
                                        )
                                            }
                                        else{
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = null,
                                                tint = Color.Green,
                                                modifier = Modifier.align(Alignment.CenterHorizontally)
                                            )
                                        }
                                    }
                                }
                            }

                            // HOURS SLEPT TRACKER
                            Surface(
                                modifier = Modifier.size(350.dp, 75.dp),
                                color = CharcoalGray,
                                shape = RoundedCornerShape(16.dp),
                                tonalElevation = 8.dp,
                                border = BorderStroke(0.3.dp, Brush.linearGradient(
                                    colors = listOf(Color.Magenta, Color.Cyan)
                                )
                                )
                            ) {
                                Row(
                                    modifier = Modifier,
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = nightLogo,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(60.dp, 60.dp)
                                            .weight(1f)
                                    )
                                    Text(
                                        text = "Hours slept\n"+hoursSlept.value+"/${getRecommendedSleepHours(userAge)} hours",
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 16.sp,
                                        color = Eggshel,
                                        modifier = Modifier.weight(1f)
                                    )
                                    AnimatedButton(
                                        onClick = {showSleepDialog=true },
                                        modifier = Modifier.weight(0.8f),
                                        text = "DO",
                                        color = Cerulean
                                    )
                                    Column(modifier = Modifier.weight(0.6f)) {
                                        if(hoursSlept.value< getRecommendedSleepHours(userAge)){
                                        CircularProgressBar(
                                            progress =(hoursSlept.value.toFloat()/getRecommendedSleepHours(userAge)) ,
                                            strokeCap = StrokeCap.Round,
                                            modifier = Modifier
                                                .size(60.dp)
                                                .weight(0.6f)
                                                .padding(start = 8.dp, end = 5.dp, top = 5.dp)
                                        )
                                        Text(
                                            text = "${((hoursSlept.value/getRecommendedSleepHours(userAge).toFloat())*10).toInt()*10}%",
                                            fontSize = 12.sp,
                                            modifier = Modifier.padding(start = 17.dp, bottom = 2.dp),
                                            fontWeight = FontWeight.Medium,
                                            color = Color.White
                                        )
                                            }
                                        else{
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = null,
                                                tint = Color.Green,
                                                modifier = Modifier.align(Alignment.CenterHorizontally)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Column(verticalArrangement = Arrangement.spacedBy(5.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Daily Diet",
                                fontWeight = FontWeight.Bold,
                                color = Purple500,
                                fontSize = 18.sp
                            )
                            Surface(
                                modifier = Modifier.size(350.dp, 75.dp),
                                color = CharcoalGray,
                                shape = RoundedCornerShape(16.dp),
                                tonalElevation = 8.dp,
                                border = BorderStroke(0.3.dp, Brush.linearGradient(
                                    colors = listOf(Color.Magenta, Color.Cyan)
                                )
                                )
                            ) {
                                Row(
                                    modifier = Modifier,
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = foodLogo,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(60.dp, 60.dp)
                                            .weight(1f)
                                    )
                                    Text(
                                        text = "Diet progress ${dailyCaloriesConsumed}"+"/$recommmendedCaloriesConsumed kcals",
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 16.sp,
                                        color = Eggshel,
                                        modifier = Modifier.weight(1f)
                                    )
                                    AnimatedButton(
                                        onClick = { navController.navigate(Screens.DietPage.screen)},
                                        modifier = Modifier.weight(0.8f),
                                        color = Cerulean,
                                        text = "ADD"
                                    )
                                    Column(modifier = Modifier.weight(0.6f)) {
                                        if(dailyCaloriesConsumed<recommmendedCaloriesConsumed) {
                                            CircularProgressBar(
                                                progress = ((dailyCaloriesConsumed / recommmendedCaloriesConsumed.toFloat())),
                                                strokeCap = StrokeCap.Round,
                                                color = Orange,
                                                modifier = Modifier
                                                    .size(60.dp)
                                                    .weight(0.6f)
                                                    .padding(start = 8.dp, end = 5.dp, top = 5.dp)
                                            )
                                            Text(
                                                text = "${((dailyCaloriesConsumed / recommmendedCaloriesConsumed.toFloat()) * 100).toInt()}%",
                                                fontSize = 12.sp,
                                                modifier = Modifier.padding(
                                                    start = 17.dp,
                                                    bottom = 2.dp
                                                ),
                                                fontWeight = FontWeight.Medium,
                                                color = Color.White
                                            )
                                        }
                                        else{
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = null,
                                                tint = Color.Green,
                                                modifier = Modifier.align(Alignment.CenterHorizontally)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (showSleepDialog) {
                    Dialog(onDismissRequest = { showSleepDialog = false }) {
                        Surface(
                            shape = MaterialTheme.shapes.medium,
                            tonalElevation = 8.dp,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(16.dp)
                            ) {
                                SleepWheelViewSelector(hoursSlept)
                                if (userName != null) {
                                    firebaseHelper.updateSleepData(userName,hoursSlept.value.toFloat()){success->
                                        if(success){
                                            Log.d("HomeFireStore","Successfully updated sleep data for user $userName" +
                                                    ",with value $hoursSlept")
                                        }else{
                                            Log.d("HomeFireStore","Could not update daily hours slept value")
                                        }

                                    }
                                }
                                OutlinedButton(
                                    onClick = { showSleepDialog = false },
                                    colors = ButtonDefaults.buttonColors(Cerulean),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = "Confirm", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}




