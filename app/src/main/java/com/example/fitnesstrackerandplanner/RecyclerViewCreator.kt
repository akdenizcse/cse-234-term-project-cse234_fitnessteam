package com.example.fitnesstrackerandplanner

import Exercise
import ExerciseSession
import SubExercise
import android.content.Intent
import android.graphics.drawable.shapes.Shape
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.fitnesstrackerandplanner.ui.theme.CharcoalGray
import com.example.fitnesstrackerandplanner.ui.theme.DarkOrchid
import com.example.fitnesstrackerandplanner.ui.theme.DarkPeriwinkle
import com.example.fitnesstrackerandplanner.ui.theme.DarkPurple
import com.example.fitnesstrackerandplanner.ui.theme.DarkRecyclerPurple
import com.example.fitnesstrackerandplanner.ui.theme.DarkViolet
import com.example.fitnesstrackerandplanner.ui.theme.DeepNavyBlue
import com.example.fitnesstrackerandplanner.ui.theme.Eggshel
import com.example.fitnesstrackerandplanner.ui.theme.LightLavender
import com.example.fitnesstrackerandplanner.ui.theme.LightOrchid
import com.example.fitnesstrackerandplanner.ui.theme.MediumPurple
import com.example.fitnesstrackerandplanner.ui.theme.PaleLavender
import com.example.fitnesstrackerandplanner.ui.theme.RecyclerPurple
import com.example.fitnesstrackerandplanner.ui.theme.Thistle
import com.example.fitnesstrackerandplanner.ui.theme.VividRed
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun listItem(
    subTitle: String?,
    title: String,
    shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(16.dp),
    color: Color = RecyclerPurple,
    textColor: Color = Color.White,
    arrowColor: Color = Color.White,
    onClick: () -> Unit = {},
    subExerciseID: Int,
    onExerciseRemoved: (Int) -> Unit
) {
    val context = LocalContext.current
    val offsetX = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    Surface(
        color = color,
        border = BorderStroke(width=0.3.dp,brush=Brush.linearGradient(colors=listOf(Color.Magenta,Color.Cyan))),
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .offset { IntOffset(offsetX.value.roundToInt(), 0) }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        coroutineScope.launch {
                            if (offsetX.value >= 100) {
                                SharedPrefManager(context).removeSubExerciseFromSelectedGO(
                                    subExerciseID
                                )
                                onExerciseRemoved(subExerciseID)
                                Toast
                                    .makeText(
                                        context,
                                        "Successfully removed from the list",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            }
                            offsetX.animateTo(0f)  // Animate back to the original position
                        }
                    }
                ) { change, dragAmount ->
                    change.consume()
                    coroutineScope.launch {
                        offsetX.snapTo(offsetX.value + dragAmount.x)
                    }
                }
            }
            .clickable {
                onClick()
            },
        shape = shape,
        tonalElevation = 10.dp,
        shadowElevation = 15.dp
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    if (subTitle != null) {
                        Text(
                            text = subTitle,
                            color = textColor,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Text(
                        text = title,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 22.sp,
                        color = textColor
                    )
                }
                Icon(
                    Icons.Default.ArrowForward,
                    contentDescription = null,
                    Modifier
                        .padding(horizontal = 6.dp)
                        .clickable { onClick() },
                    tint = arrowColor
                )
            }
        }
    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun listSubExercise(
    subTitle: String?,
    title: String,
    shape: androidx.compose.ui.graphics.Shape= RoundedCornerShape(16.dp),
    color: Color = RecyclerPurple,
    textColor: Color = Color.White,
    arrowColor: Color = Color.White,
    onClick: () -> Unit = {},
    isSelected: Boolean,
    showCheckbox: Boolean,
    onLongClick: () -> Unit,
    onCheckedChange: (Boolean) -> Unit
) {
    val context = LocalContext.current
    var isLongPressed by remember { mutableStateOf(false) }

    Surface(
        color = color,
        border = BorderStroke(width=0.3.dp,brush=Brush.linearGradient(colors=listOf(Color.Magenta,Color.Cyan))),
        shape = shape,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .combinedClickable(
                onClick = { onClick() },
                onLongClick = {
                    isLongPressed = true
                    onLongClick()
                },
            )
            .background(color = if (isLongPressed) LightGray else color),
        ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    if (subTitle != null) {
                        Text(
                            text = subTitle,
                            color = textColor,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Text(
                        text = title,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 22.sp,
                        color = textColor
                    )
                }
                if (showCheckbox && (isSelected || isLongPressed)) {
                    Checkbox(
                        checked = isSelected,
                        onCheckedChange = { onCheckedChange(it) }
                    )
                } else {
                    Icon(
                        Icons.Default.ArrowForward,
                        contentDescription = null,
                        Modifier
                            .padding(horizontal = 6.dp)
                            .clickable { onClick() },
                        tint = arrowColor
                    )
                }
            }
        }
    }
}

@Composable
fun GoRecycler(
    subExerciseIdList: List<Int>,
    greetingMessage: String?,
    icon: Painter?,
    subTitle: String?,
    navController: NavHostController,
    onExerciseRemoved: (Int) -> Unit,
    onTotalCaloriesChanged: (Double) -> Unit
) {
    val context = LocalContext.current
    val sharedPrefManager by lazy { SharedPrefManager(context) }
    val allExercises = sharedPrefManager.getAllExercises()

    // Calculate selected sub-exercises each time the subExerciseIdList changes
    val selectedSubExercises = remember(subExerciseIdList) {
        sharedPrefManager.getAllExercises().getSubExercisesByIds(subExerciseIdList)
    }
    var newTotalCaloriesBurned = 0.0

    for (subex in subExerciseIdList) {
        val subExercise = allExercises.getSubExerciseById(subex)
        if (subExercise != null) {
            newTotalCaloriesBurned += subExercise.approximateCaloriesPerSecond * 600
        } else {
            newTotalCaloriesBurned+=27
            Log.e("YourTag", "SubExercise with ID $subex not found in allExercises")
        }
    }




    onTotalCaloriesChanged(newTotalCaloriesBurned)


    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        if (greetingMessage != null) {
            item {
                Surface(
                    color = Color.Transparent,
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                ) {
                    Row {
                        Text(
                            text = greetingMessage,
                            fontSize = 25.sp,
                            modifier = Modifier.weight(3f),
                            color = Color.Black,
                            fontWeight = FontWeight.ExtraBold,
                        )
                        if (icon != null) {
                            Image(
                                painter = icon,
                                contentDescription = null,
                                modifier = Modifier
                                    .weight(1f)
                                    .size(65.dp)
                            )
                        }
                    }
                }
            }
        }
        item{
            Text("Start an exercise!", fontSize = 32.sp, style=TextStyle(
                brush=Brush.linearGradient(colors=listOf(Color.Cyan,Color.Magenta))
            ),maxLines=1, fontWeight = FontWeight.ExtraBold,
                modifier=Modifier.fillMaxWidth().padding(vertical=6.dp, horizontal = 4.dp),
                textAlign=TextAlign.Center )
        }

        items(items = selectedSubExercises, key = { it.subExerciseID }) { subExercise ->
            listItem(
                title = subExercise.subExerciseName,
                subTitle = subTitle,
                color = CharcoalGray,
                textColor = Color.White,
                arrowColor = Color.White,
                onClick = { navController.navigate(Screens.ExerciseInfoPage(subExerciseID = subExercise.subExerciseID).screen) },
                subExerciseID = subExercise.subExerciseID,
                onExerciseRemoved = onExerciseRemoved
            )
        }

        item {
            Column(
                modifier = Modifier.padding(vertical = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    Text(
                        text = "Total selected exercises ",
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 15.dp),
                        fontSize = 28.sp
                    )
                    Text(
                        text = "${subExerciseIdList.size}",
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 15.dp),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        style=TextStyle(
                            brush=Brush.linearGradient(colors=listOf(Color.Cyan,Color.Magenta))
                        )
                    )
                }
                Row(modifier=Modifier.padding(horizontal=4.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.fire),
                        contentDescription = null,
                        modifier = Modifier.size(35.dp)
                    )
                    Text(
                        "Upon ${subExerciseIdList.size * 10} minutes of exercise 10 minutes each, you will approximately burn ${newTotalCaloriesBurned.toInt()} calories",
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 15.dp),
                        fontSize = 20.sp
                    )
                }
                Row(modifier=Modifier.padding(vertical=15.dp, horizontal = 4.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.info),
                        contentDescription = null,
                        modifier = Modifier.size(35.dp)
                    )
                    Text(
                        "To drop an exercise from the list simply drag it out of the screen horizontally!",
                        color = Eggshel,
                        modifier = Modifier.padding(horizontal = 15.dp),
                        fontSize = 20.sp
                    )
                }


            }
        }
    }
}


@Composable
fun RecyclerView(
    profilePageElements:List<ProfilePageElement>,
    greetingMessage:String?,
    icon: Painter?,
    subTitle: String?,
    shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(16.dp),
    color:Color= CharcoalGray,
    textColor: Color=Color.White,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    arrowColor:Color=Color.White) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        if (greetingMessage != null ) {
            item {
                Surface(
                    color = Color.Transparent,
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                ) {
                    Row {
                        Text(
                            text = "$greetingMessage",
                            fontSize = 25.sp,
                            modifier = Modifier.weight(3f).padding(top=6.dp),
                            color = Color.Black,
                            fontWeight = FontWeight.ExtraBold,
                            style=TextStyle(brush=Brush.linearGradient(colors=listOf(Color.Cyan,Color.Magenta)))
                        )
                        if(icon!=null) {
                            Image(
                                painter = icon,
                                contentDescription = null,
                                modifier = Modifier
                                    .weight(1f)
                                    .size(65.dp)
                            )
                        }
                    }
                }
            }
        }
        items(items = profilePageElements) { profilePageElement:ProfilePageElement ->
            listItem(title=profilePageElement.name,
                subTitle = subTitle,
                shape = shape,
                color = color,
                textColor = textColor,
                arrowColor=arrowColor,
                onClick = profilePageElement.onClick,
                onExerciseRemoved = {},
                subExerciseID = 0


            )

        }

    }
}


@Composable
fun ExerciseSessionRecyclerView(exerciseSessions: List<ExerciseSession>) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = exerciseSessions) { session ->
            ExerciseSessionItem(session = session)
        }
    }
}

@Composable
fun ExerciseSessionItem(session: ExerciseSession) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CharcoalGray),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .size(380.dp, 200.dp)
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .fillMaxWidth(),
        border = BorderStroke(0.4.dp, Brush.linearGradient(colors = listOf(Color.Magenta, Color.Cyan)))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Exercise name
            val context = LocalContext.current
            val sharedPrefManager = SharedPrefManager(context)
            val allExercises = sharedPrefManager.getAllExercises()
            val exerciseName = allExercises.getSubExerciseById(subExerciseId = session.subExerciseID)?.subExerciseName
                ?: "Unknown Exercise"

            Text(
                text = exerciseName,
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            // Calories burned
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fire),
                    contentDescription = "Calories Burned",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Calories Burned: %.2f".format(session.caloriesBurned),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // Exercise Date
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.info),
                    contentDescription = "Exercise Date",
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Exercise Date: ${formatDate(session.startTime)}",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }

            // Exercise Duration
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.info),
                    contentDescription = "Exercise Duration",
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Exercise Duration: ${formatTimeSeconds(session.exerciseTime)}",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }

            // Fitty Points Gained
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.info),
                    contentDescription = "Fitty Points Gained",
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Fitty Points Gained: ${session.fittyHealthPointsGained}",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}


fun getSubExerciseName(subExerciseID: Int): String {
    return "SubExercise $subExerciseID"
}

@Composable
fun ExerciseRecyclerView(
    exercise: List<Exercise> ,
    greetingMessage:String?,
    icon: Painter?,
    subTitle: String?,
    shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(16.dp),
    color:Color= CharcoalGray,
    textColor: Color=Color.White,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    arrowColor:Color=Color.White,
    navController: NavHostController) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        if (greetingMessage != null ) {
            item {

                Surface(
                    color = Color.Transparent,
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                ) {
                    Row {
                        Text(
                            text = "$greetingMessage",
                            fontSize = 25.sp,
                            modifier = Modifier.weight(3f),
                            color = Color.Black,
                            fontWeight = FontWeight.ExtraBold,
                            style=style
                        )
                        if(icon!=null) {
                            Image(
                                painter = icon,
                                contentDescription = null,
                                modifier = Modifier
                                    .weight(1f)
                                    .size(65.dp)
                            )
                        }
                    }
                }
            }
        }
            items(exercise) { item ->
                listItem(
                    title = item.exerciseName,
                    subTitle = subTitle,
                    shape = shape,
                    color = color,
                    textColor = textColor,
                    arrowColor = arrowColor,
                    onClick = {navController.navigate(Screens.SubExerciseDetail(item.exerciseID).screen)},
                    subExerciseID = -1,
                    onExerciseRemoved = {}
                )
            }




    }
}

@Composable
fun SubExerciseRecyclerView(
    exercise: Exercise,
    greetingMessage: String? = null,
    icon: Painter? = null,
    subTitle: String? = null,
    shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(16.dp),
    color: Color = CharcoalGray,
    textColor: Color = Color.White,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    arrowColor: Color = Color.White,
    navController: NavHostController,
    isConfirmButton: Boolean = false,
    selectedSubExercises: SnapshotStateList<Int>,
    onSelectionChanged: (List<Int>) -> Unit
) {
    val context = LocalContext.current
    var showSelection by remember { mutableStateOf(true) }
    var isConfirmVisible by remember { mutableStateOf(isConfirmButton) }
    val tempSelectedSubExercises = remember { mutableStateListOf<Int>() }

    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        if (greetingMessage != null) {
            item {
                Surface(
                    color = Color.Transparent,
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                ) {
                    Row {
                        Text(
                            text = "$greetingMessage",
                            fontSize = 25.sp,
                            modifier = Modifier.weight(3f),
                            color = Color.Black,
                            fontWeight = FontWeight.ExtraBold,
                            style = style
                        )
                        if (icon != null) {
                            Image(
                                painter = icon,
                                contentDescription = null,
                                modifier = Modifier
                                    .weight(1f)
                                    .size(65.dp)
                            )
                        }
                    }
                }
            }
        }

        items(exercise.subExercises) { item ->
            listSubExercise(
                title = item.subExerciseName,
                subTitle = subTitle,
                shape = shape,
                color = color,
                textColor = textColor,
                arrowColor = arrowColor,
                isSelected = tempSelectedSubExercises.contains(item.subExerciseID),
                showCheckbox = showSelection,
                onLongClick = {
                    if (!tempSelectedSubExercises.contains(item.subExerciseID)) {
                        tempSelectedSubExercises.add(item.subExerciseID)
                        isConfirmVisible = tempSelectedSubExercises.isNotEmpty()
                    }
                },
                onCheckedChange = { isSelected ->
                    if (isSelected) {
                        tempSelectedSubExercises.add(item.subExerciseID)
                    } else {
                        tempSelectedSubExercises.remove(item.subExerciseID)
                    }
                    isConfirmVisible = tempSelectedSubExercises.isNotEmpty()
                }
            )
        }

        if (isConfirmVisible && showSelection) {
            item {
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        modifier = Modifier.padding(end = 10.dp),
                        onClick = {
                            selectedSubExercises.clear()
                            selectedSubExercises.addAll(tempSelectedSubExercises)
                            onSelectionChanged(selectedSubExercises)
                            Toast.makeText(context, "Successfully added!", Toast.LENGTH_SHORT).show()
                            showSelection = false  // Hide checkboxes and button
                            isConfirmVisible = false // Hide confirm button
                        }
                    ) {
                        Text("Confirm Selection")
                    }
                }
            }
        }

        if (!showSelection) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.Green,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Successfully added",
                        color = Color.Green,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
    }
}

