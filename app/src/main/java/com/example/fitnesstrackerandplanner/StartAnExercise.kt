package com.example.fitnesstrackerandplanner

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitnesstrackerandplanner.ui.theme.PurpleGrey40

@Composable
fun StartAnExercise(modifier:Modifier=Modifier,navController:NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = PurpleGrey40)

    )
    {
        /* Image(
            painter=bluetooth_logo,
            contentDescription= null,
            modifier=Modifier
                .padding(15.dp)
                .size(28.dp)
                .align(Alignment.TopEnd)
                )
*/
        LazyColumn(verticalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            horizontalAlignment =  Alignment.Start,

        ){
            item {
                Text(
                    text = "Exercise 1",
                    fontSize = 30.sp,
                    color = Color.Magenta,
                    modifier = Modifier.clickable { navController.navigate(Screens.ExercisePage1.screen) },
                    textAlign = TextAlign.Start

                )
            }


            item {
                Text(
                    text = "Exercise 2",
                    fontSize = 30.sp,
                    color = Color.Magenta,
                    modifier = Modifier.clickable { navController.navigate(Screens.ExercisePage2.screen) },
                    textAlign = TextAlign.Start
                )
            }

            item {
                Text(
                    text = "Exercise 3",
                    fontSize = 30.sp,
                    color = Color.Magenta,
                    modifier = Modifier.clickable { navController.navigate(Screens.ExercisePage3.screen) },
                    textAlign = TextAlign.Start
                )
            }
            item {
                Text(
                    text = "Exercise 4",
                    fontSize = 30.sp,
                    color = Color.Magenta,
                    modifier = Modifier.clickable { navController.navigate(Screens.ExercisePage4.screen) },
                    textAlign = TextAlign.Start
                )
            }
            item{
                Text(
                    text = "Exercise 5",
                    fontSize = 30.sp,
                    color = Color.Magenta,
                    modifier = Modifier.clickable { navController.navigate(Screens.ExercisePage4.screen) },
                    textAlign = TextAlign.Start
                )




        }




        }

    }
}
@Composable
fun initiateStartAnExercise(navController: NavController){

    StartAnExercise(Modifier,navController)


}
