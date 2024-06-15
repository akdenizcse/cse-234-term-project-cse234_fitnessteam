package com.example.fitnesstrackerandplanner

import Exercise
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.fitnesstrackerandplanner.ui.theme.CharcoalGray
import com.example.fitnesstrackerandplanner.ui.theme.DeepNavyBlue
import com.example.fitnesstrackerandplanner.ui.theme.RoyalRed
import com.example.fitnesstrackerandplanner.ui.theme.VividRed

@Composable
fun StartAnExercise(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPrefManager by lazy { SharedPrefManager(context) }
    var confirmedSubExerciseList by remember { mutableStateOf(sharedPrefManager.getSelectedExercisesGO()) }
    var totalCaloriesBurned by remember { mutableStateOf(0.0) }

    Surface(color = DeepNavyBlue, modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.padding(top = 10.dp))
        if (confirmedSubExerciseList.isNotEmpty()) {
            GoRecycler(
                subExerciseIdList = confirmedSubExerciseList,
                greetingMessage = null,
                icon = null,
                subTitle = "Exercise",
                navController = navController,
                onExerciseRemoved = { subExerciseId ->
                    confirmedSubExerciseList = confirmedSubExerciseList.filter { it != subExerciseId }
                },
                onTotalCaloriesChanged = { newTotalCalories ->
                    totalCaloriesBurned = newTotalCalories
                }
            )
        } else {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.info),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.emptyGoRecyclerView),
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    InstructionItem(number = 1, instruction = "Navigate to Goals page")
                    InstructionItem(number = 2, instruction = "Select an exercise group")
                    InstructionItem(number = 3, instruction = "Long click on a sub exercise")
                    InstructionItem(number = 4, instruction = "Confirm selection")
                    Spacer(modifier=Modifier.size(15.dp))
                    Text(
                        text = "Go to Goals page below",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                    )
                    Spacer(modifier=Modifier.size(15.dp))

                    AnimatedButton(
                        onClick = { navController.navigate(Screens.Goals.screen)},
                        text="VISIT", buttonHeight = 60.dp, buttonWidth = 100.dp)
                }
            }
        }
    }
}

@Composable
fun InstructionItem(number: Int, instruction: String) {
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = "$number.",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.width(24.dp)
        )
        Text(
            text = instruction,
            color = Color.White,
            fontSize = 18.sp
        )
    }
}
