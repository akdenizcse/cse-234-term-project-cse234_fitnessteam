package com.example.fitnesstrackerandplanner

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
fun StartAnExercise() {
    val navController=rememberNavController()
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
        Column(verticalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            horizontalAlignment =  Alignment.Start,
        ){
            Text(
                text ="Exercise 1",
                fontSize = 30.sp,
                color = Color.Magenta,
                modifier=Modifier.clickable{ navController.navigate("Exercise1Page")},
                textAlign = TextAlign.Start

            )
            Text(
                text ="Exercise 2",
                fontSize = 30.sp,
                color = Color.Magenta,
                modifier=Modifier.clickable{ navController.navigate("Exercise1Page") },
                textAlign = TextAlign.Start

            )
            Text(
                text ="Exercise 3",
                fontSize = 30.sp,
                color = Color.Magenta,
                modifier=Modifier.clickable{ navController.navigate("Exercise1Page") },
                textAlign = TextAlign.Start


            )
            Text(
                text ="Exercise 4",
                fontSize = 30.sp,
                color = Color.Magenta,
                modifier=Modifier.clickable{  navController.navigate("Exercise1Page")},
                textAlign = TextAlign.Start


            )//TODO:Include navHost logic
            /*NavHost(navController = navController, startDestination = Screens.StartAnExercise.screen) {

                  //  composable(Screens.Activites.screen){ Exercise1() }
                  //  composable(Screens.Activites.screen){ Exercise2() }
                  //  composable(Screens.Activites.screen){ Exercise3() }
                  //  composable(Screens.Activites.screen){ Exercise4() }

            }*/



        }




    }
}
@Preview
@Composable
fun StartAnExercisePreview() {
    StartAnExercise()
}
