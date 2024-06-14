package com.example.fitnesstrackerandplanner

import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.health.connect.client.records.ExerciseSessionRecord
import androidx.navigation.compose.rememberNavController
import com.example.fitnesstrackerandplanner.ui.theme.Cerulean
import com.example.fitnesstrackerandplanner.ui.theme.DeepNavyBlue
import com.example.fitnesstrackerandplanner.ui.theme.FitnessTrackerAndPlannerTheme
import com.example.fitnesstrackerandplanner.ui.theme.SurfaceGreen
import com.example.healthconnect.codelab.data.HealthConnectManager
import kotlinx.coroutines.delay
import java.time.Instant
import java.time.ZonedDateTime
import java.util.concurrent.TimeUnit

@Composable
fun ExercisePage1(exerciseName: String = "Value") {
    val caloriesBurned = 25
    val fireLogo = painterResource(id = R.drawable.fire)
    val context = LocalContext.current
    var startTime = remember { ZonedDateTime.now() }
    var startTimeDisp by remember { mutableStateOf(System.currentTimeMillis()) }
    var elapsedTime by remember { mutableStateOf(0L) }
    val navController = rememberNavController()
    val healthConnectManager = HealthConnectManager(context)
    var isButtonClicked by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (true) {
            val currentTime = System.currentTimeMillis()
            elapsedTime = (currentTime - startTimeDisp)
            delay(1000)
        }
    }

    val minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime) % 60

    Surface(
        color = DeepNavyBlue,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Calories Burned",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(5.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(5.dp)
            ) {
                Text(
                    text = "$caloriesBurned",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                )
                Text(
                    text = " kcal",
                    color = Color.White,
                    fontSize = 18.sp
                )
                Box(
                    modifier = Modifier.size(50.dp)
                ) {
                    Icon(
                        painter = fireLogo,
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(5.dp)
                    )
                }
            }
            Text(
                text = exerciseName,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(25.dp)
            )
            Text(
                text = "Time Elapsed: ${formatTime(minutes, seconds)}",
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier.padding(5.dp)
            )
            Button(
                onClick = {
                    val endTime = ZonedDateTime.now()
                    navController.popBackStack()
                    isButtonClicked = true


                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Cerulean)
            ) {
                Text(
                    text = "End Exercise",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }


    LaunchedEffect(isButtonClicked) {
        if (isButtonClicked) {
            val endTime = ZonedDateTime.now()
            navController.popBackStack()
            with(healthConnectManager) {
                try {
                    writeExerciseSession(startTime, endTime)
                    Toast.makeText(context,"Succesfully ended the exercise!",Toast.LENGTH_SHORT).show()
                }catch (e: Exception) {
                    Log.e("ExercisePage1", "Error writing to Health Connect", e)
                }

            }
        }
    }
}



@Composable
fun ExercisePage2(){
    Box(
        modifier= Modifier
            .fillMaxSize()
            .background(color = SurfaceGreen)
    ){
        Text(text="ExercisePage2",modifier=Modifier.align(Alignment.Center),fontSize=45.sp)
    }
}
@Composable
fun ExercisePage3(){
    Box(
        modifier= Modifier
            .fillMaxSize()
            .background(color = SurfaceGreen)
    ){
        Text(text="ExercisePage3",modifier=Modifier.align(Alignment.Center),fontSize=45.sp)
    }
}
@Composable
fun ExercisePage4(){
    Box(
        modifier= Modifier
            .fillMaxSize()
            .background(color = SurfaceGreen)
    ){
        Text(text="ExercisePage4",modifier=Modifier.align(Alignment.Center),fontSize=45.sp)
    }
}



@Preview
@Composable
fun ExercisePage1Preview(){
    FitnessTrackerAndPlannerTheme {
        ExercisePage1("Lateral pull")
    }
}

fun formatTime(minutes: Long, seconds: Long): String {
    return String.format("%02d:%02d", minutes, seconds)
}