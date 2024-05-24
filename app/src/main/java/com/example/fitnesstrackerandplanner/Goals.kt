package com.example.fitnesstrackerandplanner
import android.database.sqlite.SQLiteDatabase
import android.icu.util.Calendar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnesstrackerandplanner.ui.theme.DarkRecyclerPurple
import com.example.fitnesstrackerandplanner.ui.theme.SurfaceGreen

@Composable
fun Goals(db: SQLiteDatabase?,dbHelper:DatabaseHelper) {
    //TODO:Saate göre karşılama olacak
    //TODO: Bugün yapacağı aktiviteleri checkbox ile seçecek
    //TODO:Seçtikten sonra GO ekranında koyduğu egzersizler gözükecek
    //TODO:Aşağıda start tuşu ile başlayacak
    //TODO:Başladıktan sonra ise kalori yakma vs database'e kaydedilecek
    //TODO:Sonra bizim tanımladığımız bir puan ile günlük aktivite skoru verilecek

    val hour by lazy{ Calendar.getInstance().get(Calendar.HOUR_OF_DAY) }
    val context= LocalContext.current
    val sharedPrefManager by lazy{SharedPrefManager(context)}
    val userName=sharedPrefManager.getCurrentUser()
    val isDayTime:Boolean= hour in 6..18
    val greeting:String=if(isDayTime)"Good Morning, $userName!" else "Good Evening, $userName!"
    val icon= if(isDayTime) painterResource(id = R.drawable.icons8_sunny_48)
            else painterResource(id = R.drawable.icons8_moon_48)

    Surface(color=SurfaceGreen, modifier = Modifier.fillMaxSize()
    ) {
        val exerciseTypes:List<String> = mutableListOf("Abs","Arm","Legs","Shoulders","Belly","Chest","Back","Neck")
        RecyclerView(exerciseTypes,
            greetingMessage = greeting,
            icon=icon,
            subTitle = "Exercise Type",
            shape= RoundedCornerShape(15.dp),
            color= DarkRecyclerPurple)

    }


    }






@Preview
@Composable
fun GoalsPreview() {
   listItem( "1","2", RectangleShape)
}