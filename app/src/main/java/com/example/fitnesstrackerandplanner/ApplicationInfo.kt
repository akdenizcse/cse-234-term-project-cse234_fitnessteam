// ApplicationInformationPage.kt
package com.example.fitnesstrackerandplanner

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.fitnesstrackerandplanner.ui.theme.DeepNavyBlue
import com.example.fitnesstrackerandplanner.ui.theme.CharcoalGray
import com.example.fitnesstrackerandplanner.ui.theme.Gold

@Composable
fun ApplicationInformationPage() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().background(DeepNavyBlue)) {
        Surface(
            modifier = Modifier
                .background(DeepNavyBlue)
                .wrapContentSize()
                .padding(16.dp),
            color = DeepNavyBlue,
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(CharcoalGray, shape = MaterialTheme.shapes.medium)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.info),
                    contentDescription = "App Info",
                    modifier = Modifier.size(48.dp).padding(top=6.dp)
                )

                Spacer(modifier = Modifier.padding(16.dp))

                Text(
                    text = "Fittie v1",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    style= TextStyle(brush=Brush.linearGradient(colors=listOf(Color.Cyan,Color.Magenta,Gold)))
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = "Written by",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.padding(4.dp))

                Text(
                    text = "İrem Karakaplan\nBaha Tütüncüoğlu\nDeniz Eren Arıcı",
                    fontSize = 18.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }
    }
}
