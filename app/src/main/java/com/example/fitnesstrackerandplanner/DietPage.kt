package com.example.fitnesstrackerandplanner

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitnesstrackerandplanner.ui.theme.CharcoalGray
import com.example.fitnesstrackerandplanner.ui.theme.DeepNavyBlue
import com.example.fitnesstrackerandplanner.ui.theme.Eggshel
import com.example.fitnesstrackerandplanner.ui.theme.SignificantlyDarkerPowderBlue

// Diet.kt
data class Diet(
    val name: String,
    val description: String,
    val icon: Int // Drawable resource ID for the icon
)

// SampleDiets.kt
val sampleDiets = listOf(
    Diet("Keto", "A high-fat, low-carb diet.", R.drawable.fire),
    Diet("Vegan", "A plant-based diet.", R.drawable.fire),
    Diet("Paleo", "A diet based on foods similar to what might have been eaten during the Paleolithic era.", R.drawable.fire),
    Diet("Mediterranean", "A diet inspired by the eating habits of Greece, Southern Italy, and Spain.", R.drawable.fire)
)

// DietItem.kt
@Composable
fun DietItem(diet: Diet) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors=CardDefaults.cardColors(containerColor = CharcoalGray),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = diet.icon),
                contentDescription = diet.name,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = diet.name, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.White)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = diet.description, fontSize = 16.sp,color=Eggshel)
            }
        }
    }
}

// DietList.kt
@Composable
fun DietList(diets: List<Diet>) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavyBlue)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item{
            Text(
                text="DIETS", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold,
                style= TextStyle(brush = Brush.linearGradient(colors = listOf(Color.Cyan,Color.Magenta)))
            )
        }
        items(diets) { diet ->
            DietItem(diet = diet)
        }
    }
}

// DietPage.kt
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DietPage() {
    DietList(diets = sampleDiets)
}
@Preview
@Composable
fun PreviewDietPage(){
    DietPage()
}