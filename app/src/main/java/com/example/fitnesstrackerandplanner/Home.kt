package com.example.fitnesstrackerandplanner

import android.database.sqlite.SQLiteDatabase
import android.icu.util.Calendar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitnesstrackerandplanner.ui.theme.*

@Composable
fun Home(db: SQLiteDatabase?) {
    val hour by lazy{ Calendar.getInstance().get(Calendar.HOUR_OF_DAY) }
    val context= LocalContext.current
    val sharedPrefManager by lazy{SharedPrefManager(context)}
    val userName=sharedPrefManager.getCurrentUser()
    val isDayTime:Boolean= hour in 6..18
    val greeting:String=if(isDayTime)"Good Morning, $userName!" else "Good Evening, $userName!"
    val icon= if(isDayTime) painterResource(id = R.drawable.icons8_sunny_48)
    else painterResource(id = R.drawable.icons8_moon_48)
    Surface(color= SurfaceGreen,modifier=Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Row(Modifier.padding(15.dp)) {
                Text(
                    text = "$greeting",
                    fontSize = 25.sp,
                    modifier = Modifier.weight(3f),
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold
                )
                Image(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                        .size(65.dp)

                )
            }

                    Column(modifier=Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

                        Surface(modifier = Modifier.size(350.dp, 100.dp), color = RecyclerPurple) {
                                Column(){
                            Text("Height: userHeight",modifier= Modifier

                                .weight(1f), fontWeight = FontWeight.Bold)
                            Text("Weight: userWeight",modifier=Modifier.weight(1f), fontWeight = FontWeight.Bold)
                            Text("Age: userAge",modifier=Modifier.weight(1f), fontWeight = FontWeight.Bold)
                        }
                                }
                        Surface(modifier = Modifier.size(350.dp, 125.dp), color = RecyclerLayering) {
                            Column() {
                                Text("You have completed %X percent of your daily goals,congratulations!",
                                    fontWeight = FontWeight.Medium,
                                    textAlign = TextAlign.Center,
                                    modifier=Modifier
                                        .padding(top=10.dp)
                                        .weight(1f))

                                ProgressBar(progress=0.6f,modifier=Modifier
                                    .size(250.dp,15.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .weight(0.35f)
                                    ,color=Color.Magenta,
                                    trackColor = Color.White,
                                    strokeCap = StrokeCap.Round)
                                Text("BMI=userBMI",modifier=Modifier.align(Alignment.End))

                            }

                        }
                    }




        }
    }
}





@Preview
@Composable
fun HomePreview() {
    Home(null)
}