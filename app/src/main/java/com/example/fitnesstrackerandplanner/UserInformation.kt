package com.example.fitnesstrackerandplanner
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitnesstrackerandplanner.AnimatedButton
import com.example.fitnesstrackerandplanner.SharedPrefManager
import com.example.fitnesstrackerandplanner.ui.theme.CharcoalGray
import com.example.fitnesstrackerandplanner.ui.theme.DeepNavyBlue
import com.example.fitnesstrackerandplanner.ui.theme.Eggshel

@Composable
fun UserInformationPage() {
    val context = LocalContext.current
    val sharedPrefManager = SharedPrefManager(context)
    val userName = sharedPrefManager.getCurrentUsername()

    val height = remember { mutableStateOf(0) }
    val weight = remember { mutableStateOf(0) }
    val age = remember { mutableStateOf(0) }
    val gender = remember { mutableStateOf(false) }

    LaunchedEffect(userName) {
        height.value = sharedPrefManager.getCurrentUserHeight()
        weight.value = sharedPrefManager.getCurrentUserWeight()
        age.value = sharedPrefManager.getCurrentUserAge()
        gender.value = sharedPrefManager.getCurrentUserGender()
    }

        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "User Information",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                style= TextStyle(brush=Brush.linearGradient(colors=listOf(Color.Magenta,Color.Cyan)))
            )

            Spacer(modifier = Modifier.padding(8.dp))
            Card(
                modifier = Modifier.size(350.dp,200.dp), shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CharcoalGray),
                border = BorderStroke(
                    width = 0.3.dp,
                    brush = Brush.linearGradient(colors = listOf(Color.Cyan, Color.Magenta))
                )
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                    modifier=Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Height:${height.value }cm",
                        modifier = Modifier.padding(start = 9.dp, bottom = 12.dp),
                        color = Color.White,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "Weight:${weight.value}kg",
                        modifier = Modifier.padding(start = 9.dp, bottom = 12.dp),
                        color = Color.White,
                        fontSize = 18.sp

                    )
                    Text(
                        text = "Age:${age.value}",
                        modifier = Modifier.padding(start = 9.dp, bottom = 12.dp),
                        color = Color.White,
                        fontSize = 18.sp

                    )
                    Text(
                        text = "BMI:${
                            calculateBMI(
                                weight.value.toShort(),
                                height.value.toShort()
                            )
                        }",
                        modifier = Modifier.padding(start = 9.dp, bottom = 12.dp),
                        color = Color.White,
                        fontSize = 18.sp

                    )

                }
            }
        }


}

