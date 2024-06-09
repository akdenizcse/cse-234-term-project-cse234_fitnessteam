package com.example.fitnesstrackerandplanner

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fitnesstrackerandplanner.ui.theme.CharcoalGray
import com.example.fitnesstrackerandplanner.ui.theme.DeepNavyBlue

@Composable
fun StartAnExercise() {
        val exerciseList=listOf("Bench Press","Lateral Pull","Jogging")

    Surface(modifier=Modifier.fillMaxSize(),color= DeepNavyBlue) {
        RecyclerView(greetingMessage = null,
            icon = null,
            subTitle = null,
            names = exerciseList,
            shape= RoundedCornerShape(20.dp),
            color= CharcoalGray,
            textColor= Color.White


        )

    }



        }





@Composable
fun initiateStartAnExercise( navController: NavController){

    StartAnExercise()


}
