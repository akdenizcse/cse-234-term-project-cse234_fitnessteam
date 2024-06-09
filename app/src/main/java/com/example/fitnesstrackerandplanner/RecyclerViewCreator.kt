package com.example.fitnesstrackerandplanner

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
             shape:androidx.compose.ui.graphics.Shape,
             color:Color= RecyclerPurple,
             textColor:Color=Color.White,
             arrowColor: Color=Color.White


){


        Surface(
            color = color,
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .clickable { },
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
                                .clickable { }
                        ,tint=arrowColor)

                    }


                }
            }
}

@Composable
fun RecyclerView(
    names:List<String> =List(10){"$it"},
    greetingMessage:String?,
    icon: Painter?,
    subTitle: String?,
    shape: androidx.compose.ui.graphics.Shape = RectangleShape,
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
                arrowColor=arrowColor
            )

        }

    }
}