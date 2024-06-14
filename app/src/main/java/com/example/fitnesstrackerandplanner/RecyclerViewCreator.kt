package com.example.fitnesstrackerandplanner

import Exercise
import SubExercise
import android.content.Intent
import android.graphics.drawable.shapes.Shape
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.example.fitnesstrackerandplanner.ui.theme.LightLavender
import com.example.fitnesstrackerandplanner.ui.theme.LightOrchid
import com.example.fitnesstrackerandplanner.ui.theme.MediumPurple
import com.example.fitnesstrackerandplanner.ui.theme.PaleLavender
import com.example.fitnesstrackerandplanner.ui.theme.RecyclerPurple
import com.example.fitnesstrackerandplanner.ui.theme.Thistle

@Composable
fun listItem(subTitle:String?,
             title:String,
             shape:androidx.compose.ui.graphics.Shape= RoundedCornerShape(16.dp),
             color:Color= RecyclerPurple,
             textColor:Color=Color.White,
             arrowColor: Color=Color.White,
             onClick:()->Unit={},

             ){


        Surface(
            color = color,
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .clickable {
                   onClick()
                },
            shape = shape, tonalElevation = 10.dp, shadowElevation = 15.dp
        )
        {

                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth()
                ) {


                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                        )
                        {
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
                        Icon(Icons.Default.ArrowForward, contentDescription = null,
                            Modifier
                                .padding(horizontal = 6.dp)
                                .clickable {onClick() }
                        ,tint=arrowColor)

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
        shape = shape) {
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
    subExerciseIdList:List<Int>,
    greetingMessage:String?,
    icon: Painter?,
    subTitle: String?,
    shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(16.dp),
    color:Color= CharcoalGray,
    textColor: Color=Color.White,

    style: TextStyle = MaterialTheme.typography.bodyMedium,
    arrowColor:Color=Color.White,
    navController: NavHostController) {
    val context= LocalContext.current
    val sharedPrefManager by lazy{SharedPrefManager(context)}
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
        val allExercises=sharedPrefManager.getAllExercises()
        val selected=allExercises.getSubExercisesByIds(subExerciseIdList)
        items(items = selected) { subExercise ->

            listItem(title=subExercise.exerciseName,
                subTitle = subTitle,
                shape = shape,
                color = color,
                textColor = textColor,
                arrowColor=arrowColor,
                onClick = {navController.navigate(Screens.ExerciseInfoPage(subExerciseID = subExercise.subExerciseID).screen)},


                )

        }

    }
}

@Composable
fun RecyclerView(
    names:List<String> =List(10){"$it"},
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
        items(items = names) { name ->
            listItem(title=name,
                subTitle = subTitle,
                shape = shape,
                color = color,
                textColor = textColor,
                arrowColor=arrowColor,
                onClick = {},


            )

        }

    }
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
                    title = item.name,
                    subTitle = subTitle,
                    shape = shape,
                    color = color,
                    textColor = textColor,
                    arrowColor = arrowColor,
                    onClick = {navController.navigate(Screens.SubExerciseDetail(item.exerciseID).screen)}

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
    shape: androidx.compose.ui.graphics.Shape = RectangleShape,
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
                title = item.exerciseName,
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

