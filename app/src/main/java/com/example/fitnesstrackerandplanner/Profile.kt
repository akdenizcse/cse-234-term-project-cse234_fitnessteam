package com.example.fitnesstrackerandplanner

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitnesstrackerandplanner.ui.theme.PurpleGrey40

@Composable
fun Profile() {
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
                .align(Alignment.TopEnd))
*/
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment =  Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)

        ){
            //aynı satır içinde fotoğraf+yazı

            Row() {
                //Image()
                Text(
                    text = "Set weight",
                    fontSize = 30.sp,
                    color = Color.Magenta,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { },

                    )
            //aynı satır içinde fotoğraf+yazı
            }
            Row() {
                //Image()
                Text(
                    text = "Set height",
                    fontSize = 30.sp,
                    color = Color.Magenta,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                )
            }

            Text(
                text ="<--------------->",
                fontSize = 30.sp,
                color = Color.Magenta,
                modifier=Modifier
                    .fillMaxWidth()
                    .clickable {  }
            )

        }
    }
}
@Preview
@Composable
fun ProfilePreview() {
    Profile()
}