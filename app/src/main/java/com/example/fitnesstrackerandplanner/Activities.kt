package com.example.fitnesstrackerandplanner

import ExerciseSession
import FirebaseHelper
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.fitnesstrackerandplanner.ui.theme.DeepNavyBlue
import com.example.fitnesstrackerandplanner.ui.theme.PurpleGrey40
import com.example.fitnesstrackerandplanner.ui.theme.SurfaceGreen
import java.time.LocalDateTime

@Composable
fun Activities() {
    val selectedInterval = remember { mutableStateOf("Last 1 Day") }
    val exerciseSessions = remember { mutableStateOf<List<ExerciseSession>>(emptyList()) }
    val context = LocalContext.current
    val sharedPrefManager = SharedPrefManager(context)
    val firebaseHelper = FirebaseHelper()

    LaunchedEffect(selectedInterval.value) { // Relaunch when selectedInterval changes
        val fetchedSessions = sharedPrefManager.getCurrentUsername()
            ?.let { firebaseHelper.fetchExerciseSessions(it) }

        val filteredSessions = fetchedSessions?.filter { session ->
            when (selectedInterval.value) {
                "Last 1 Day" -> session.startTime.toLocalDateTime().isAfter(LocalDateTime.now().minusDays(1)) // Today's sessions are excluded
                "Last 7 Days" -> session.startTime.toLocalDateTime().isAfter(LocalDateTime.now().minusDays(7))
                "Last 1 Month" -> session.startTime.toLocalDateTime().isAfter(LocalDateTime.now().minusMonths(1))
                "Last 1 Year" -> session.startTime.toLocalDateTime().isAfter(LocalDateTime.now().minusYears(1))
                else -> true
            }
        }?.sortedByDescending { it.startTime }

        if (filteredSessions != null) {
            exerciseSessions.value = filteredSessions
        } else {
            Toast.makeText(context, "Filtered sessions are null!", Toast.LENGTH_SHORT).show()
        }

        // Check for filtered results and show a toast if empty
        if (filteredSessions.isNullOrEmpty()) {
            Toast.makeText(context, "No exercises found for the selected interval!", Toast.LENGTH_SHORT).show()
        }
    }

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
            MySessionDropDownMenu(
                modifier = Modifier.align(Alignment.Start),
                selectedInterval = selectedInterval.value, // Pass selectedInterval state
                onIntervalChanged = { newInterval ->
                    selectedInterval.value = newInterval
                }
            )

            Text(
                text = "Exercises for ${selectedInterval.value}",
                fontSize = 30.sp,
                color = Color.White
            )


            ExerciseSessionRecyclerView(exerciseSessions = exerciseSessions.value)
        }
    }
}


