package com.example.fitnesstrackerandplanner

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.fitnesstrackerandplanner.ui.theme.Cerulean
import com.example.fitnesstrackerandplanner.ui.theme.DeepNavyBlue
import com.example.fitnesstrackerandplanner.ui.theme.LightBlue
import com.example.fitnesstrackerandplanner.ui.theme.RecyclerPurple

@Composable
fun PostExercisePage(
    caloriesBurned: Int,
    minutes: Long,
    seconds: Long,
    subExerciseID:Int,
    navController: NavHostController
) {
    val fireimg=painterResource(R.drawable.fire)
    val clockimg=painterResource(R.drawable.watch)
    val infoimg= painterResource(id = R.drawable.info)
    // Use the received arguments as needed in this Composable
    Surface(
        color = DeepNavyBlue,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val gradientColors = listOf(Cyan,LightBlue , RecyclerPurple /*...*/)

            Text(
                text = "Exercise Report",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = gradientColors
                    )
                ),
                modifier = Modifier.padding(8.dp)
            )
            Row() {
                Image(
                    painter=fireimg,
                    contentDescription=null,
                    modifier=Modifier.size(30.dp)
                )
                Text(
                    text = "Calories Burned: $caloriesBurned kcal",
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.padding(8.dp)
                )


            }
            Row() {
                Image(
                    painter=clockimg,
                    contentDescription = null
                )
                Text(
                    text = "Time Elapsed: ${formatTime(minutes, seconds)}",
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Button(
                onClick = {
                    navController.navigate(Screens.StartAnExercise.screen)
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Cerulean,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Back to Exercises",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

