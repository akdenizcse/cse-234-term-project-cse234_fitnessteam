import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitnesstrackerandplanner.AnimatedButton
import com.example.fitnesstrackerandplanner.R
import com.example.fitnesstrackerandplanner.SharedPrefManager
import com.example.fitnesstrackerandplanner.ui.theme.Cerulean
import com.example.fitnesstrackerandplanner.ui.theme.CharcoalGray
import com.example.fitnesstrackerandplanner.ui.theme.DeepNavyBlue
import com.example.fitnesstrackerandplanner.ui.theme.Eggshel
import com.example.fitnesstrackerandplanner.ui.theme.SignificantlyDarkerPowderBlue

// Diet.kt
//TODO: MAKE THIS PAGE MORE VISUALLY APPEALING AND SHAREDPREFMANAGER LOGIC AND NOTIFICATIONS ACCORDINGLY

data class Food(
    val name: String,
    val calories: Int
)

data class Diet(
    val name: String,
    val description: String,
    val icon: Int, // Drawable resource ID for the icon
    val foods: List<Food>
)

val sampleDiets = listOf(
    Diet("Keto", "A high-fat, low-carb diet.", R.drawable.keto, listOf(
        Food("Avocado", 234),
        Food("Bacon", 42),
        Food("Cheese", 113),
        Food("Eggs", 78),
        Food("Butter", 102),
        Food("Salmon", 208),
        Food("Beef", 250),
        Food("Pork Chops", 300),
        Food("Chicken Thighs", 209),
        Food("Coconut Oil", 121),
        Food("MCT Oil", 100),
        Food("Almonds", 164),
        Food("Walnuts", 185),
        Food("Brazil Nuts", 184),
        Food("Macadamia Nuts", 204),
        Food("Olive Oil", 119),
        Food("Heavy Cream", 52)
    )),
    Diet("Vegan", "A plant-based diet.", R.drawable.vegan, listOf(
        Food("Tofu", 76),
        Food("Lentils", 116),
        Food("Quinoa", 120),
        Food("Chickpeas", 164),
        Food("Black Beans", 132),
        Food("Brown Rice", 218),
        Food("Oatmeal", 154),
        Food("Almond Milk", 60),
        Food("Soy Milk", 80),
        Food("Tempeh", 195),
        Food("Seitan", 104),
        Food("Peanut Butter", 188),
        Food("Spinach", 23),
        Food("Kale", 33),
        Food("Broccoli", 55),
        Food("Cashews", 157),
        Food("Nutritional Yeast", 60)
    )),
    Diet("Paleo", "A very primal diet.", R.drawable.paleo, listOf(
        Food("Chicken Breast", 165),
        Food("Sweet Potato", 103),
        Food("Almonds", 164),
        Food("Blueberries", 57),
        Food("Strawberries", 32),
        Food("Apple", 95),
        Food("Pork Ribs", 297),
        Food("Turkey", 150),
        Food("Grass-Fed Butter", 102),
        Food("Coconut Milk", 552),
        Food("Honey", 64),
        Food("Zucchini", 33),
        Food("Carrots", 25),
        Food("Bell Peppers", 24),
        Food("Brussels Sprouts", 38),
        Food("Kale", 33),
        Food("Walnuts", 185)
    )),
    Diet("Mediterranean", "A diet based on the eating habits of Mediterranean people.", R.drawable.mediterranean, listOf(
        Food("Olive Oil", 119),
        Food("Tomato", 22),
        Food("Feta Cheese", 75),
        Food("Chickpeas", 164),
        Food("Cucumber", 16),
        Food("Spinach", 23),
        Food("Garlic", 4),
        Food("Onion", 44),
        Food("Red Wine", 125),
        Food("Yogurt", 150),
        Food("Salmon", 208),
        Food("Tuna", 132),
        Food("Whole Grain Bread", 69),
        Food("Oranges", 62),
        Food("Grapes", 104),
        Food("Eggplant", 25),
        Food("Lemon", 17)
    )),
    Diet("Low-Carb", "A low-carb diet.", R.drawable.lowcarb, listOf(
        Food("Eggs", 78),
        Food("Chicken Breast", 165),
        Food("Beef", 250),
        Food("Broccoli", 55),
        Food("Cauliflower", 25),
        Food("Cheese", 113),
        Food("Zucchini", 33),
        Food("Green Beans", 31),
        Food("Spinach", 23),
        Food("Avocado", 234),
        Food("Almonds", 164),
        Food("Walnuts", 185),
        Food("Pecans", 201),
        Food("Salmon", 208),
        Food("Tuna", 132),
        Food("Bell Peppers", 24),
        Food("Eggplant", 25)
    )),
    Diet("High-Protein", "A diet high in protein.", R.drawable.highprotein, listOf(
        Food("Chicken Breast", 165),
        Food("Turkey Breast", 135),
        Food("Eggs", 78),
        Food("Greek Yogurt", 100),
        Food("Cottage Cheese", 206),
        Food("Whey Protein", 120),
        Food("Beef", 250),
        Food("Pork Chops", 300),
        Food("Salmon", 208),
        Food("Tuna", 132),
        Food("Lentils", 116),
        Food("Black Beans", 132),
        Food("Chickpeas", 164),
        Food("Quinoa", 120),
        Food("Almonds", 164),
        Food("Edamame", 188),
        Food("Hemp Seeds", 166)
    )),
    Diet("Low-Fat", "A diet low in fat.", R.drawable.lowfat, listOf(
        Food("Chicken Breast", 165),
        Food("Turkey Breast", 135),
        Food("Egg Whites", 17),
        Food("Greek Yogurt", 100),
        Food("Cottage Cheese", 206),
        Food("Tilapia", 129),
        Food("Cod", 82),
        Food("Shrimp", 99),
        Food("Lentils", 116),
        Food("Black Beans", 132),
        Food("Chickpeas", 164),
        Food("Quinoa", 120),
        Food("Brown Rice", 218),
        Food("Oatmeal", 154),
        Food("Whole Grain Bread", 69),
        Food("Sweet Potato", 103),
        Food("Apple", 95)
    ))
)


@Composable
fun DietItem(diet: Diet, caloriesTaken: MutableState<Int>, selectedFoods: MutableState<List<Food>>) {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val sharedPrefManager = remember { SharedPrefManager(context) }
    val firebaseHelper=FirebaseHelper()
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CharcoalGray),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.clickable { expanded = !expanded }) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.width(400.dp)) {
                Image(
                    painter = painterResource(id = diet.icon),
                    contentDescription = diet.name,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier=Modifier.width(250.dp)) {
                    Text(
                        text = diet.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = diet.description,
                        fontSize = 16.sp,
                        color = Eggshel,
                        modifier = Modifier.padding(end = 18.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = if (expanded) "Collapse" else "Expand",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Column {
                    diet.foods.forEach { food ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 24.dp, end = 24.dp), // Adding start and end padding to food rows
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${food.name} (${food.calories} kcal)",
                                color = Color.White,
                                modifier = Modifier.weight(1f) // Making text take available space
                            )
                            AnimatedButton(
                                onClick = {
                                    caloriesTaken.value += food.calories
                                    sharedPrefManager.getCurrentUsername()
                                        ?.let { firebaseHelper.updateCalorieData(it, calorieTaken = caloriesTaken.value) {
                                            success->
                                            if(success){
                                                Log.d("DietPageFirestoreHelper","Successfully updated daily calories taken value")
                                            }
                                            else{
                                                Log.e("DietPageFirestoreHelper","Failed to update daily calories taken value")
                                            }
                                        } }
                                    selectedFoods.value = selectedFoods.value.toMutableList().also { it.add(food) }
                                },
                                text = "Add",
                                color = Cerulean,
                                innerHeight = 30.dp,
                                innerWidth = 80.dp,
                                buttonWidth = 80.dp,
                                buttonHeight = 30.dp
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun DietList(diets: List<Diet>, caloriesTaken: MutableState<Int>, selectedFoods: MutableState<List<Food>>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavyBlue)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "DIETS",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                style = TextStyle(brush = Brush.linearGradient(colors = listOf(Color.Cyan, Color.Magenta)))
            )
        }
        items(diets) { diet ->
            DietItem(diet = diet, caloriesTaken = caloriesTaken, selectedFoods = selectedFoods)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DietPage() {
    val selectedFoods = remember { mutableStateOf<List<Food>>(emptyList()) }
    val caloriesTaken = remember { mutableStateOf(0) }

    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DeepNavyBlue)
                    .padding(16.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    item {
                        Text(
                            text = "DIETS",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            style = TextStyle(brush = Brush.linearGradient(colors = listOf(Color.Cyan, Color.Magenta))),
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                    items(sampleDiets) { diet ->
                        DietItem(diet = diet, caloriesTaken = caloriesTaken, selectedFoods = selectedFoods)
                    }
                    // Section to display selected foods and total calories
                    if (selectedFoods.value.isNotEmpty()) {
                        item{
                        Spacer(modifier = Modifier.height(16.dp))
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = CharcoalGray),
                            elevation = CardDefaults.elevatedCardElevation(8.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = "Selected Foods",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = Color.White,
                                    modifier=Modifier.align(Alignment.CenterHorizontally),
                                    style=TextStyle(brush = Brush.linearGradient(colors=listOf(Color.Magenta,Color.Cyan)))
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                selectedFoods.value.forEach { food ->
                                    Text(
                                        text = "${food.name}: ${food.calories} kcal",
                                        color = Color.White,
                                        fontSize = 16.sp,
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Total Calories: ${caloriesTaken.value} kcal",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                        }
                }


            }
        }
    )
}
@Preview
@Composable
fun PreviewDietPage() {
    DietPage()
}
