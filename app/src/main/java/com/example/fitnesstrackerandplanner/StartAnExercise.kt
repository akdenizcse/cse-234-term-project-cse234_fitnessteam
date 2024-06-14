package com.example.fitnesstrackerandplanner

import Exercise
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.fitnesstrackerandplanner.ui.theme.CharcoalGray
import com.example.fitnesstrackerandplanner.ui.theme.DeepNavyBlue

@Composable
fun StartAnExercise(navController: NavHostController) {
    val context= LocalContext.current
    val sharedPrefManager by lazy{SharedPrefManager(context)}
    val confirmedSubExerciseList=sharedPrefManager.getSelectedExercisesGO()
    Surface(color = DeepNavyBlue,modifier=Modifier.fillMaxSize()) {
        GoRecycler(
            subExerciseIdList = confirmedSubExerciseList,
            greetingMessage = null,
            icon = null,
            subTitle = "Exercise",
            navController = navController
        )
    }
    }









