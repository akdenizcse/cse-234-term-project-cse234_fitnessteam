package com.example.fitnesstrackerandplanner

import Exercise
import SubExercise
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.fitnesstrackerandplanner.ui.theme.DeepNavyBlue
import com.example.fitnesstrackerandplanner.ui.theme.Eggshel
import com.example.fitnesstrackerandplanner.ui.theme.RoyalRed
import com.example.fitnesstrackerandplanner.ui.theme.VividRed

@Composable
fun ExerciseInfoPage(subExercise: SubExercise?,navController: NavHostController){
    val fireimg= painterResource(id = R.drawable.fire)
    val targetimg= painterResource(id = R.drawable.goals)
Surface(color = DeepNavyBlue, modifier= Modifier.fillMaxSize()) {
    Column(
        modifier = Modifier.size(200.dp, 300.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = {
            navController.popBackStack()
        },
            modifier=Modifier.padding(top=6.dp,start=4.dp).align(Alignment.Start)) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null,
                modifier=Modifier.size(50.dp,80.dp), tint = Color.White)
        }
        Spacer(modifier = Modifier.size(height = 25.dp, width = 0.dp))
        Text(
            text = subExercise!!.subExerciseName,
            modifier = Modifier.align(Alignment.CenterHorizontally), fontSize = 32.sp, fontWeight = FontWeight.ExtraBold,
            color = Color.White

        )

        Spacer(modifier = Modifier.size(25.dp))
        Box(modifier = Modifier.padding(horizontal = 18.dp)) {
            YoutubePlayer(
                youtubeVideoId = subExercise.videoUrl
                , lifecycleOwner = LocalLifecycleOwner.current
            )

        }
        Spacer(modifier = Modifier.size(15.dp))
        Row(modifier=Modifier.align(Alignment.Start)) {
            Image(painter = targetimg, contentDescription = null, modifier = Modifier
                .size(30.dp)
                .padding(start = 6.dp))

            Text(
                text = subExercise.description,
                color = Color.White,
                modifier = Modifier.padding(start = 6.dp)
            )
        }
            Row(modifier = Modifier
                .padding(vertical = 30.dp)
                .align(Alignment.Start)) {
                Image(painter = fireimg, contentDescription = null, modifier = Modifier.padding(start = 4.dp))
                Text(
                    text = "Upon 60m of this exercise you will burn" +
                            " ${(subExercise.approximateCaloriesPerSecond * 3600).toInt()} kcals!",
                    color = Color.White,
                    modifier = Modifier.padding(start = 6.dp)
                )
            }
        Row(modifier = Modifier
            .padding(bottom = 30.dp)
            .align(Alignment.Start)){
            //TODO:Add targeted muscle groups with icon next to it.
            Image(painter = targetimg, contentDescription = null, modifier = Modifier
                .size(30.dp)
                .padding(start = 6.dp))

            Text(
                text = buildAnnotatedString {
                                            append("Targeted muscle group\t ")
                    withStyle(
                        SpanStyle(fontWeight = FontWeight.ExtraBold,
                            brush=Brush.linearGradient(
                                colors=listOf(VividRed, RoyalRed)
                            )
                        )
                    ){
                        append("${subExercise.exerciseName}")
                    }
                },
                modifier = Modifier.padding(start = 6.dp),
                color = Color.White
            )
        }

            AnimatedButton(
                onClick = { navController.navigate(Screens.ExercisePage(subExerciseID = subExercise.subExerciseID).screen) },
                text = "START",
                buttonWidth = 115.dp,
                buttonHeight = 60.dp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )


        }

    }
}


