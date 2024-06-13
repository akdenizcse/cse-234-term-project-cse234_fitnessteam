package com.example.fitnesstrackerandplanner

import Exercise
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.fitnesstrackerandplanner.ui.theme.DeepNavyBlue

@Composable
fun SubExercisePage(exercise:Exercise,navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DeepNavyBlue)

    )
    {
        /* Image(
            painter=bluetooth_logo,
            contentDescription= null,
            modifier=Modifier
                .padding(15.dp)
                .size(28.dp)
                .align(Alignment.TopEnd),)
*/
      SubExerciseRecyclerView(exercise = exercise, icon = null, subTitle =null, navController =navController  )


        }
    }

