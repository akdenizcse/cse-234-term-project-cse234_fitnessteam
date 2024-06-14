package com.example.fitnesstrackerandplanner
import Exercise
import SubExercise
import android.icu.util.Calendar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.fitnesstrackerandplanner.ui.theme.CharcoalGray
import com.example.fitnesstrackerandplanner.ui.theme.DarkRecyclerPurple
import com.example.fitnesstrackerandplanner.ui.theme.DeepNavyBlue
import com.example.fitnesstrackerandplanner.ui.theme.SurfaceGreen


@Composable
fun Goals(navController: NavHostController,exList:List<Exercise>) {
    //TODO:Saate göre karşılama olacak --> YAPILDI
    //TODO: Bugün yapacağı aktiviteleri checkbox ile seçecek
    //TODO:Seçtikten sonra GO ekranında koyduğu egzersizler gözükecek
    //TODO:Aşağıda start tuşu ile başlayacak
    //TODO:Başladıktan sonra ise kalori yakma vs database'e kaydedilecek
    //TODO:Sonra bizim tanımladığımız bir puan ile günlük aktivite skoru verilecek

    val hour by lazy{ Calendar.getInstance().get(Calendar.HOUR_OF_DAY) }
    val context= LocalContext.current
    val sharedPrefManager by lazy{SharedPrefManager(context)}
    val userName=sharedPrefManager.getCurrentUsername()
    val isDayTime:Boolean= hour in 6..18
    val greeting:String=if(isDayTime)"Good Morning, $userName!" else "Good Evening, $userName!"
    val icon= if(isDayTime) painterResource(id = R.drawable.icons8_sunny_48)
            else painterResource(id = R.drawable.icons8_moon_48)




    Surface(color= DeepNavyBlue, modifier = Modifier.fillMaxSize()
    ) {
        val exerciseTypes:List<String> = mutableListOf("Abs","Arm","Legs","Shoulders","Belly","Chest","Back","Neck")

        ExerciseRecyclerView(exList, // bunu normal recycler çevir düzelir
            greetingMessage = greeting,
            icon=icon,
            subTitle = "Exercise Type",
            shape= RoundedCornerShape(15.dp),
            color= CharcoalGray,
            style= TextStyle(
                brush= Brush.linearGradient(
                    colors=listOf(Color.Magenta, Color.Cyan)
                )
            ),
            navController = navController


            )

    }


    }





