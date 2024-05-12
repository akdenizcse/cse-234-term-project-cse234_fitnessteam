package com.example.fitnesstrackerandplanner

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitnesstrackerandplanner.ui.theme.*
@Composable
fun Home() {
    var bluetooth_logo = painterResource(id = R.drawable.bluetooth_wave_svgrepo_com)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = PurpleGrey40)

    )
    {
       /* Image(
            painter=bluetooth_logo,
            contentDescription= null,
            modifier=Modifier
                .padding(15.dp)
                .size(28.dp)
                .align(Alignment.TopEnd),

        )*/
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
           horizontalAlignment =  Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){

            Text(
                text ="Home page",
                fontSize = 30.sp,
                color = Color.Magenta
                )

        }
    }
}
@Preview
@Composable
fun HomePreview() {
    Home()
}