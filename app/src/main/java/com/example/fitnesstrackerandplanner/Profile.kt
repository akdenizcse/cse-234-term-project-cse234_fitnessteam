package com.example.fitnesstrackerandplanner
import android.media.Image
import androidx.compose.foundation.Image
import android.database.sqlite.SQLiteDatabase
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitnesstrackerandplanner.ui.theme.*
import com.example.fitnesstrackerandplanner.utilityMethods
import androidx.compose.material.icons.Icons

@Composable
fun Profile(db: SQLiteDatabase?) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PurpleGrey40)
        ) {

        Column (modifier=Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally


        ){


        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .height(280.dp)
                .fillMaxWidth()
                .padding(25.dp),

            colors = CardDefaults.cardColors(
                containerColor = PastelPink
            )

        ) {
            Column(
                modifier=Modifier.fillMaxWidth(),


                ) {
                Row(modifier=Modifier.weight(2f)) {
                    Text(
                        text = "İrem KARAKAPLAN",
                        modifier = Modifier
                            .padding(top = 20.dp, start = 10.dp)
                            .weight(0.2f),
                        textAlign = TextAlign.Start,
                    )

                    Column(modifier=Modifier.padding(end=15.dp)) {
                        Row(modifier=Modifier) {
                            val img=painterResource(R.drawable.scale_logo)
                            Image(painter=img,contentDescription=null,modifier=Modifier.padding(15.dp).size(23.dp,23.dp).fillMaxSize())
                            Text(
                                text = "Weight:${50} kg",
                                modifier = Modifier.padding(top = 15.dp)
                            )
                        }
                        Row(modifier=Modifier) {
                            val img=painterResource(R.drawable.height_logo)
                            Image(painter=img,contentDescription=null,modifier=Modifier.padding(15.dp).size(30.dp,25.dp).fillMaxSize())

                            Text(
                                text = "Height:${167}",
                                modifier = Modifier.padding(top = 15.dp)
                            )
                        }
                    }

                }
                Text(
                    text = "Age:20",
                    modifier = Modifier
                        .padding(start = 10.dp,)
                        .weight(1f),
                    textAlign = TextAlign.Start,
                )
                Text(
                    text="BMI: ${utilityMethods.calculateBMI(52,166)}",
                    modifier = Modifier
                        .padding(start = 10.dp,)

                        .weight(1f),
                    textAlign = TextAlign.Start,
                )



            }
        }

    }}


}

@Preview
@Composable
fun ProfilePreview() {
    Profile(null)

}