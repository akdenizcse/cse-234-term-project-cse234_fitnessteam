package com.example.fitnesstrackerandplanner

import Exercise
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.fitnesstrackerandplanner.ViewModels.SubExerciseViewModel
import com.example.fitnesstrackerandplanner.ui.theme.DeepNavyBlue

@Composable
fun SubExercisePage(exercise: Exercise, navController: NavHostController) {
    val context = LocalContext.current
    val sharedPrefManager = SharedPrefManager(context)
    val viewModel: SubExerciseViewModel = viewModel()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DeepNavyBlue)
    ) {
        val selectedSubExercises = remember { mutableStateListOf(*sharedPrefManager.getSelectedExercises().toTypedArray()) }
        var isConfirmButton by remember { mutableStateOf(selectedSubExercises.isNotEmpty()) }

        SubExerciseRecyclerView(
            exercise = exercise,
            icon = null,
            subTitle = null,
            navController = navController,
            onSelectionChanged = { selectedExercises ->
                viewModel.clearSubExercises()
                selectedExercises.forEach { viewModel.addSubExercise(it) }
                sharedPrefManager.saveSelectedExercises(viewModel.selectedSubExercises)
                isConfirmButton = selectedExercises.isNotEmpty()
                sharedPrefManager.saveSelectedExercisesGO(viewModel.selectedSubExercises)
            },
            selectedSubExercises = selectedSubExercises,
            isConfirmButton = isConfirmButton,

        )
    }
}


