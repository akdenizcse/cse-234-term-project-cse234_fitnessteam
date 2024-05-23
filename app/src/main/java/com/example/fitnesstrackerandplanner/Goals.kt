package com.example.fitnesstrackerandplanner
import android.database.sqlite.SQLiteDatabase
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.icu.util.Calendar
import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitnesstrackerandplanner.ui.theme.Beige
import com.example.fitnesstrackerandplanner.ui.theme.DarkPeriwinkle
import com.example.fitnesstrackerandplanner.ui.theme.DarkPurple
import com.example.fitnesstrackerandplanner.ui.theme.Orange
import com.example.fitnesstrackerandplanner.ui.theme.PurpleGrey40
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
        RecyclerView(exerciseTypes,greetingMessage = greeting,icon)

    }


    }
@Composable
fun listItem(name:String){


    Surface(color= DarkPeriwinkle,
        modifier=Modifier
            .padding(vertical=4.dp, horizontal = 8.dp)
    )
    {
        Column(modifier= Modifier
            .padding(24.dp)
            .fillMaxWidth()) {


            Row(){
                Column(modifier=Modifier
                    .weight(1f)
                )
                {
                    Text(text="Exercise type")
                    Text(text=name, fontWeight =FontWeight.ExtraBold  )

                }

        }


        }
    }



}

@Composable
fun RecyclerView(names:List<String> =List(10){"$it"},greetingMessage:String,icon: Painter){
    LazyColumn(modifier=Modifier.padding(vertical = 4.dp)) {
        item {
            Surface(color= Color.Transparent,
                modifier=Modifier
                    .padding(vertical=4.dp, horizontal = 8.dp)
            ){
                Row {
                    Text(
                        text = "$greetingMessage",
                        fontSize = 25.sp,
                        modifier=Modifier.weight(3f),
                        color=Color.White,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Image(
                        painter = icon,
                        contentDescription = null,
                        modifier=Modifier.weight(1f).size(65.dp)

                    )

                }
            }
        }
        items(items=names){
            name->listItem(name)

        }

    }

}



@Preview
@Composable
fun GoalsPreview() {
   listItem(name = "1")
}