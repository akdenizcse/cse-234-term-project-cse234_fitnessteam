package com.example.fitnesstrackerandplanner

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun ProgressBar(progress: Float, modifier: Modifier = Modifier, color: Color, trackColor: Color, strokeCap: StrokeCap) {
    LinearProgressIndicator(progress = progress,modifier=modifier,color=color,trackColor=trackColor,strokeCap=strokeCap)
}
@Composable
fun CircularProgressBar(progress: Float,
                        modifier: Modifier = Modifier,
                        color: Color = Color.Magenta,
                        strokeCap: StrokeCap,
                        trackColor: Color = Color.White
) {
    CircularProgressIndicator(
        progress=progress,
        modifier=modifier,
        color=color,
        strokeCap = strokeCap,
        trackColor=trackColor

    )
}
@Preview
@Composable
fun PreviewProgressBar() {
    ProgressBar(progress = 0.5f,color= Color.Green,modifier= Modifier, strokeCap = StrokeCap.Round, trackColor = Color.LightGray)
}