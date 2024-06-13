package com.example.fitnesstrackerandplanner

import Exercise
import SubExercise
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.fitnesstrackerandplanner.ui.theme.DeepNavyBlue

@Composable
fun ExerciseInfoPage(subExercise: SubExercise?){
Surface(color = DeepNavyBlue, modifier= Modifier.fillMaxSize()) {
    Column(modifier=Modifier.size(200.dp,300.dp)) {
        Spacer(modifier=Modifier.size(15.dp))
        VideoPlayerExo()
        Text(text="This is "+subExercise!!.description,
            color= Color.White)


    }

}
}

