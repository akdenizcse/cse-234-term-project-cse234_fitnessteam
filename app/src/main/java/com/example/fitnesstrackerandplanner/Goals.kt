package com.example.fitnesstrackerandplanner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.fitnesstrackerandplanner.ui.theme.PurpleGrey40

@Composable
fun Goals() {
    //TODO:Saate göre karşılama olacak
    //TODO: Bugün yapacağı aktiviteleri checkbox ile seçecek
    //TODO:Seçtikten sonra GO ekranında koyduğu egzersizler gözükecek
    //TODO:Aşağıda start tuşu ile başlayacak
    //TODO:Başladıktan sonra ise kalori yakma vs database'e kaydedilecek
    //TODO:Sonra bizim tanımladığımız bir puan ile günlük aktivite skoru verilecek


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color= PurpleGrey40)

    )
    {
        /* Image(
            painter=bluetooth_logo,
            contentDescription= null,
            modifier=Modifier
                .padding(15.dp)
                .size(28.dp)
                .align(Alignment.TopEnd),)
*/
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            horizontalAlignment =  Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(
                text ="Goals page",
                fontSize = 30.sp,
                color = Color.Magenta
            )

        }
    }
}
@Preview
@Composable
fun GoalsPreview() {
    Goals()
}