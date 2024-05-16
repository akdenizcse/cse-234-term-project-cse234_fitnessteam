package com.example.fitnesstrackerandplanner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.fitnesstrackerandplanner.ui.theme.FitnessTrackerAndPlannerTheme

@Composable
fun ExercisePage3(){
    Box(
        modifier= Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        Text(text="ExercisePage3",modifier=Modifier.align(Alignment.Center),fontSize=45.sp)
    }
}

@Preview
@Composable
fun ExercisePage3Preview(){
    FitnessTrackerAndPlannerTheme {
        ExercisePage3()
    }
}