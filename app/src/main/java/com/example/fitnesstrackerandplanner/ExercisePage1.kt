package com.example.fitnesstrackerandplanner

import FirebaseHelper
import SubExercise
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.health.connect.client.records.ExerciseSessionRecord
import androidx.navigation.NavHostController
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
fun ExercisePage1(subExercise: SubExercise, exerciseName: String = "Value", navController: NavHostController) {
    var caloriesBurned by remember { mutableStateOf(0.0) }
    val caloriesBurntPerSecond = subExercise.approximateCaloriesPerSecond
    val fireLogo = painterResource(id = R.drawable.fire)
    val stoppedLogo = painterResource(id = R.drawable.stopped)
    val resumedLogo = painterResource(id = R.drawable.resumed)
    val watchIcon = painterResource(id = R.drawable.watch)
    val context = LocalContext.current
    val startTime = remember { ZonedDateTime.now() }
    var startTimeDisp by remember { mutableStateOf(System.currentTimeMillis()) }
    var elapsedTime by remember { mutableStateOf(0L) }
    val healthConnectManager = HealthConnectManager(context)
    var isButtonClicked by remember { mutableStateOf(false) }
    var isStopped by remember { mutableStateOf(false) }
    var showStatusLogo by remember { mutableStateOf(false) }
    var statusLogo by remember { mutableStateOf(stoppedLogo) }
    var showDialog by remember { mutableStateOf(false) }
    val sharedPrefManager by lazy { SharedPrefManager(context) }
    val blinkAnimation = remember { Animatable(1f) }
    val userName = sharedPrefManager!!.getCurrentUsername()

    LaunchedEffect(Unit) {
        while (true) {
            if (!isStopped) {
                val currentTime = System.currentTimeMillis()
                elapsedTime = currentTime - startTimeDisp
                caloriesBurned += caloriesBurntPerSecond
            }
            delay(1000)
        }
    }

    LaunchedEffect(isStopped) {
        showStatusLogo = true
        statusLogo = if (isStopped) stoppedLogo else resumedLogo

        if (isStopped) {
            while (isStopped) {
                blinkAnimation.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = 500, easing = LinearEasing)
                )
                blinkAnimation.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 500, easing = LinearEasing)
                )
            }
        } else {
            delay(1500)
            showStatusLogo = false
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
            verticalArrangement = Arrangement.Top
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
                    text = "%.2f".format(caloriesBurned),
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
                    modifier = Modifier.size(80.dp)
                ) {
                    Image(
                        painter = fireLogo,
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(5.dp)
                            .size(60.dp)
                    )
                }
            }
            Text(
                text = exerciseName,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.padding(25.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(5.dp)
            ) {
                Icon(
                    painter = watchIcon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Time Elapsed: ${formatTime(minutes, seconds)}",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        isStopped = !isStopped
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .padding(end = 8.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isStopped) Color.Gray else Cerulean,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = if (isStopped) "Resume" else "Stop",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }
            Button(
                onClick = {
                    showDialog = true
                },
                modifier = Modifier
                    .size(120.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "End",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }

        if (showStatusLogo) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Image(
                    painter = statusLogo,
                    contentDescription = if (isStopped) "Stopped" else "Resumed",
                    modifier = Modifier
                        .size(80.dp)
                        .alpha(blinkAnimation.value)
                )
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(text = "End Exercise")
            },
            text = {
                Text(text = "Are you sure you want to end the exercise?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        sharedPrefManager.removeSubExerciseFromSelectedGO(subExerciseId = subExercise.subExerciseID)
                        navController.navigate(
                            Screens.PostExercisePage(subExercise.subExerciseID, caloriesBurned, minutes, seconds).screen
                        )

                        showDialog = false
                        isButtonClicked = true
                    }
                ) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false }
                ) {
                    Text(text = "No")
                }
            }
        )
    }

    LaunchedEffect(isButtonClicked) {
        if (isButtonClicked) {
            val endTime = ZonedDateTime.now()
            val firebaseHelper = FirebaseHelper()
            if (userName != null) {
                firebaseHelper.logExerciseSession(
                    userName = userName ,
                    subExerciseID = subExercise.subExerciseID,
                    caloriesBurned = caloriesBurned,
                    exerciseTime = elapsedTime,
                    fittyHealthPointsGained = 0,
                    startTime = startTime,
                    endTime = endTime
                ) { success ->
                    if (success) {
                        Toast.makeText(context, "Exercise session logged successfully!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Failed to log exercise session.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}



fun formatTime(minutes: Long, seconds: Long): String {
    return String.format("%02d:%02d", minutes, seconds)
}
