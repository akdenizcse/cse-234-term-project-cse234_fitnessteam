package com.example.fitnesstrackerandplanner

import android.database.sqlite.SQLiteDatabase
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.fitnesstrackerandplanner.ui.theme.DeepNavyBlue
import com.example.fitnesstrackerandplanner.ui.theme.PurpleGrey40
import com.example.fitnesstrackerandplanner.ui.theme.SurfaceGreen

@Composable
fun Activities() {
    val selectedInterval = remember { mutableStateOf("Last 1 Day") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DeepNavyBlue)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            MyDropDownMenu(modifier=Modifier.align(Alignment.Start))






            Text(
                text = "Exercises for ${selectedInterval.value}",
                fontSize = 30.sp,
                color = Color.White
            )
        }
    }
}
@Preview
@Composable
fun ActivitiesPreview() {
    Activities()
}