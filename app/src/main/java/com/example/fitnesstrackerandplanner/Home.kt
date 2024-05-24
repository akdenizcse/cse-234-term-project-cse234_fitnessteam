package com.example.fitnesstrackerandplanner

import android.database.sqlite.SQLiteDatabase
import android.icu.util.Calendar
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.DefaultStrokeLineWidth
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitnesstrackerandplanner.ui.theme.*
//TODO:Add a diet "ADD" Button onClick action.
@Composable
fun Home(db: SQLiteDatabase?) {
    val hour by lazy{ Calendar.getInstance().get(Calendar.HOUR_OF_DAY) }
    val context= LocalContext.current
    val sharedPrefManager by lazy{SharedPrefManager(context)}
    val userName=sharedPrefManager.getCurrentUser()
    val isDayTime:Boolean= hour in 6..18
    val greeting:String=if(isDayTime)"Good Morning, $userName!" else "Good Evening, $userName!"

    //Initializing icons
    val icon= if(isDayTime) painterResource(id = R.drawable.icons8_sunny_48)
    else painterResource(id = R.drawable.icons8_moon_48)

    val waterDropLogo= painterResource(id = R.drawable.water_drop)
    val fireLogo= painterResource(id = R.drawable.fire)
    val nightLogo= painterResource(id = R.drawable.nightlogo)
    val foodLogo= painterResource(id = R.drawable.foodlogo)
    val dumbellLogo= painterResource(id = R.drawable.gymdumbell)
    val waterProgress = remember { mutableStateOf(0f) }
    val caloriesBurntProgress = remember { mutableStateOf(0f) }
    val hoursSleptProgress = remember { mutableStateOf(0f) }
    val dietProgress = remember { mutableStateOf(0f) }
    Surface(color= SurfaceGreen,modifier=Modifier.fillMaxSize()) {
        LazyColumn {

            item {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Row(Modifier.padding(15.dp)) {
                Text(
                    text = "$greeting",
                    fontSize = 25.sp,
                    modifier = Modifier.weight(3f),
                    color = Color.DarkGray,
                    fontWeight = FontWeight.ExtraBold
                )
                Image(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .weight(1f)
                        .size(65.dp)
                )
            }

                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Surface(
                                modifier = Modifier.size(350.dp, 100.dp),
                                color = LightRecyclerPurple,
                                shape = RoundedCornerShape(16.dp)

                            ) {
                                Column() {
                                    Text(
                                        "Height: 166cm", modifier = Modifier.padding(horizontal=8.dp)
                                            .weight(1f),
                                        fontWeight = FontWeight.Bold,
                                        color=Color.Black

                                    )
                                    Text(
                                        "Weight: 52kg",
                                        modifier = Modifier.weight(1f).padding(horizontal=8.dp),
                                        fontWeight = FontWeight.Bold,
                                        color=Color.Black
                                    )
                                    Row() {
                                        Text(
                                            "Age: 21",
                                            modifier = Modifier.weight(1f)
                                                .padding(horizontal = 8.dp),
                                            fontWeight = FontWeight.Bold,
                                            color=Color.Black
                                        )
                                        Text("BMI:22.9",
                                            modifier = Modifier
                                                .align(Alignment.Bottom)
                                                .padding(8.dp),
                                            fontWeight = FontWeight.Bold,
                                            color=Color.Black

                                        )

                                    }
                                }
                            }
                            Surface(
                                modifier = Modifier.size(350.dp, 125.dp),
                                color = RecyclerLayering,
                                shape = RoundedCornerShape(16.dp)

                            ) {
                                Column() {
                                    Text(
                                        "You have completed %X percent of your daily goals,congratulations!",
                                        fontWeight = FontWeight.Medium,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .padding(vertical = 10.dp,horizontal=8.dp)
                                            .weight(1f),
                                        color=Color.Black
                                    )

                                    ProgressBar(
                                        progress = 0.6f, modifier = Modifier
                                            .size(250.dp, 15.dp)
                                            .align(Alignment.CenterHorizontally)
                                            .weight(0.35f)
                                            .padding(bottom=8.dp),
                                        color = Color.Magenta
                                           ,
                                        trackColor = Color.White,
                                        strokeCap = StrokeCap.Round
                                    )

                                }


                            }
                            Column() {
                                Text(
                                    "Daily Objectives",
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .padding(vertical = 5.dp),
                                    color = Purple500,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                                    //DAILY WATER TRACKING
                                    Surface(
                                        modifier = Modifier.size(350.dp, 75.dp),
                                        color = RecyclerPurple,
                                        shape = RoundedCornerShape(16.dp)

                                    ) {
                                        Row(
                                            modifier = Modifier,
                                            horizontalArrangement = Arrangement.SpaceEvenly,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painter = waterDropLogo,
                                                contentDescription = null,
                                                modifier = Modifier.size(60.dp, 60.dp).weight(1f)
                                            )
                                            Text(
                                                text = "Drink Water\nX/2.5L",
                                                fontWeight = FontWeight.Medium,
                                                fontSize = 16.sp,
                                                color = Pink40,
                                                modifier=Modifier.weight(1f)
                                            )
                                            OutlinedButton(
                                                onClick = { if (waterProgress.value < 1f) waterProgress.value += 0.1f },
                                                modifier = Modifier.size(40.dp, 30.dp).weight(0.8f),
                                                shape = ButtonDefaults.outlinedShape,
                                                colors = ButtonDefaults.buttonColors(containerColor = ButtonPurple, contentColor = Color.Black)
                                            ) {
                                                Text(
                                                    text = "DRINK",
                                                    color = Color.White,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize=10.sp
                                                )
                                            }
                                            Column(modifier=Modifier.weight(0.6f)) {
                                                CircularProgressBar(
                                                    progress = waterProgress.value,
                                                    strokeCap = StrokeCap.Round,
                                                    modifier = Modifier.size(60.dp)
                                                        .padding(start = 8.dp, end = 5.dp,top=5.dp)
                                                )
                                                Text(text = "${(waterProgress.value * 10).toInt()}/10"
                                                    ,fontSize=12.sp,
                                                    modifier=Modifier.padding(start=17.dp,bottom=2.dp)
                                                , fontWeight = FontWeight.Medium)

                                            }
                                        }
                                    }

                                    //CALORIES BURNT TRACKER

                                    Surface(
                                        modifier = Modifier.size(350.dp, 75.dp),
                                        color = RecyclerPurple,
                                        shape = RoundedCornerShape(16.dp)

                                    ) {
                                        Row(
                                            modifier = Modifier,
                                            horizontalArrangement = Arrangement.SpaceEvenly,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painter = fireLogo,
                                                contentDescription = null,
                                                modifier = Modifier.size(60.dp, 60.dp).weight(1f)
                                            )
                                            Text(
                                                text = "Calories burnt\nX calories",
                                                fontWeight = FontWeight.Medium,
                                                fontSize = 16.sp,
                                                color = Pink40,
                                                modifier=Modifier.weight(1f)
                                            )
                                            OutlinedButton(
                                                onClick = { if (caloriesBurntProgress.value < 1f) caloriesBurntProgress.value += 0.1f },
                                                modifier = Modifier.size(40.dp, 30.dp).weight(0.8f),
                                                shape = ButtonDefaults.outlinedShape,
                                                colors = ButtonDefaults.buttonColors(containerColor = ButtonPurple, contentColor = Color.White)
                                            ) {
                                                Text(
                                                    text = "DO",
                                                    color = Color.White,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize=12.sp
                                                )
                                            }
                                            Column(modifier=Modifier.weight(0.6f)) {

                                                CircularProgressBar(
                                                    progress = caloriesBurntProgress.value,
                                                    strokeCap = StrokeCap.Round,
                                                    modifier = Modifier.size(60.dp).weight(0.6f)
                                                        .padding(start = 8.dp, end = 5.dp,top=5.dp)
                                                )
                                                Text(
                                                    text = "${(caloriesBurntProgress.value * 10).toInt()}/10",
                                                    fontSize = 12.sp,
                                                    modifier = Modifier.padding(
                                                        start = 17.dp,
                                                        bottom = 2.dp
                                                    ),
                                                    fontWeight = FontWeight.Medium
                                                )
                                            }
                                        }
                                    }


                                    //HOURS SLEPT TRACKER
                                    Surface(
                                        modifier = Modifier.size(350.dp, 75.dp),
                                        color = RecyclerPurple,
                                        shape = RoundedCornerShape(16.dp)

                                    ) {
                                        Row(
                                            modifier = Modifier,
                                            horizontalArrangement = Arrangement.SpaceEvenly,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painter = nightLogo,
                                                contentDescription = null,
                                                modifier = Modifier.size(60.dp, 60.dp).weight(1f)
                                            )
                                            Text(
                                                text = "Hours slept\nX hours",
                                                fontWeight = FontWeight.Medium,
                                                fontSize = 16.sp,
                                                color = Pink40,
                                                modifier=Modifier.weight(1f)
                                            )
                                            OutlinedButton(
                                                onClick = { if (hoursSleptProgress.value < 1f) hoursSleptProgress.value += 0.1f },
                                                modifier = Modifier.size(40.dp, 30.dp).weight(0.8f),
                                                shape = ButtonDefaults.outlinedShape,
                                                colors = ButtonDefaults.buttonColors(containerColor = ButtonPurple, contentColor = Color.White)
                                            ) {
                                                Text(
                                                    text = "DO",
                                                    color = Color.White,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize=12.sp
                                                )
                                            }
                                            Column(modifier=Modifier.weight(0.6f)) {
                                                CircularProgressBar(
                                                    progress = hoursSleptProgress.value,
                                                    strokeCap = StrokeCap.Round,
                                                    modifier = Modifier.size(60.dp).weight(0.6f)
                                                        .padding(start = 8.dp, end = 5.dp,top=5.dp)
                                                )
                                                Text(
                                                    text = "${(hoursSleptProgress.value * 10).toInt()}/10",
                                                    fontSize = 12.sp,
                                                    modifier = Modifier.padding(
                                                        start = 17.dp,
                                                        bottom = 2.dp
                                                    ),
                                                    fontWeight = FontWeight.Medium
                                                )
                                            }
                                            }
                                    }



                                }
                                Column(verticalArrangement = Arrangement.spacedBy(5.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "Daily Diet",
                                        fontWeight = FontWeight.Bold,
                                        color = Purple500,

                                        fontSize = 18.sp
                                    )
                                    Surface(
                                        modifier = Modifier.size(350.dp, 75.dp),
                                        color = RecyclerPurple,
                                        shape = RoundedCornerShape(16.dp)

                                    ) {
                                        Row(
                                            modifier = Modifier,
                                            horizontalArrangement = Arrangement.SpaceEvenly,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painter = foodLogo,
                                                contentDescription = null,
                                                modifier = Modifier.size(60.dp, 60.dp).weight(1f)
                                            )
                                            Text(
                                                text = "Add a diet",
                                                fontWeight = FontWeight.Medium,
                                                fontSize = 16.sp,
                                                color = Pink40,
                                                modifier=Modifier.weight(1f)
                                            )
                                            OutlinedButton(
                                                onClick = { if (dietProgress.value < 1f) dietProgress.value += 0.1f },
                                                modifier = Modifier.size(40.dp, 30.dp).weight(0.8f),
                                                shape = ButtonDefaults.outlinedShape,
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = ButtonPurple,
                                                    contentColor = Color.White
                                                )
                                            ) {
                                                Text(
                                                    text = "ADD",
                                                    color = Color.White,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize=12.sp
                                                )
                                            }
                                            Column(modifier=Modifier.weight(0.6f)) {
                                                CircularProgressBar(
                                                    progress = dietProgress.value,
                                                    strokeCap = StrokeCap.Round,
                                                    color = Orange,
                                                    modifier = Modifier.size(60.dp).weight(0.6f)
                                                        .padding(start = 8.dp, end = 5.dp,top=5.dp)
                                                )
                                                Text(
                                                    text = "${(dietProgress.value * 10).toInt()}/10",
                                                    fontSize = 12.sp,
                                                    modifier = Modifier.padding(
                                                        start = 17.dp,
                                                        bottom = 2.dp
                                                    ),
                                                    fontWeight = FontWeight.Medium
                                                )
                                            }
                                        }
                                    }
                                }
                            }
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