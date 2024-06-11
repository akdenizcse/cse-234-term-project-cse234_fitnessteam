package com.example.fitnesstrackerandplanner

import Exercise
import SubExercise
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun ExerciseInfoPage(subExercise: SubExercise?){
Box(modifier= Modifier.fillMaxSize().wrapContentSize(Alignment.TopStart)) {
    Column(modifier=Modifier.size(200.dp,300.dp)) {
        VideoPlayerExo()
    }
}
}

