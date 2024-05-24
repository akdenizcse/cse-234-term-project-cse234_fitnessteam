package com.example.fitnesstrackerandplanner

import android.database.sqlite.SQLiteDatabase
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fitnesstrackerandplanner.ui.theme.SurfaceGreen

@Composable
fun StartAnExercise(db:SQLiteDatabase?,modifier:Modifier=Modifier,navController:NavController) {
        val exerciseList=listOf("Bench Press","Lateral Pull","Jogging")

    Surface(modifier=Modifier.fillMaxSize(),color= SurfaceGreen) {
        RecyclerView(greetingMessage = null, icon = null, subTitle = null, names = exerciseList,shape= RoundedCornerShape(20.dp))

    }



        }





@Composable
fun initiateStartAnExercise(db: SQLiteDatabase?, navController: NavController){

    StartAnExercise(db,Modifier,navController)


}
