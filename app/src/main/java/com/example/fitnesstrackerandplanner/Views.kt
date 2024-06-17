package com.example.fitnesstrackerandplanner

import android.graphics.Paint
import android.graphics.Rect
import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitnesstrackerandplanner.ui.theme.ButtonPurple
import com.example.fitnesstrackerandplanner.ui.theme.Gold
import com.example.fitnesstrackerandplanner.ui.theme.Orange
import com.example.fitnesstrackerandplanner.ui.theme.gray
import com.example.fitnesstrackerandplanner.ui.theme.white
import kotlin.math.abs
import kotlin.math.roundToInt

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


@Composable
fun GradientOnClickSurface(onClick: () -> Unit,content:@Composable ()->Unit) {
    var isClicked by remember { mutableStateOf(false) }

    val gradientBrush = remember {
        Brush.linearGradient(
            colors = listOf(Color.Red, Color.Blue),
            start = Offset(0f, 0f),
            end = Offset(1000f, 1000f) // Adjust this to fit your design
        )
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = if (isClicked) gradientBrush else Brush.verticalGradient(listOf(Color.LightGray, Color.LightGray)),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable {
                isClicked = !isClicked
                onClick()
            }
        , content = content)
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AnimatedButton(
    onClick: ()->Unit,
    modifier: Modifier = Modifier,
    text: String="DO",
    color: Color = ButtonPurple,
    fontSize: TextUnit =15.sp,
    fontWeight: FontWeight = FontWeight.Medium,
    textColor: Color = Color.White,
    innerHeight: Dp =40.dp,
    innerWidth: Dp =120.dp,
    buttonWidth: Dp =85.dp,
    buttonHeight: Dp =40.dp,
    border:BorderStroke=BorderStroke(0.3.dp, brush = Brush.linearGradient(colors=listOf(Gold,Color.Cyan,Color.Magenta)))


) {
    val selected = remember { mutableStateOf(false) }
    val scale = animateFloatAsState(if (selected.value) {
        1.3f
    } else 1f)

    Column(
        Modifier
            .height(innerHeight)
            .width(innerWidth), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onClick,
            border = border,
            colors= ButtonDefaults.buttonColors(color),
            modifier = modifier.size(buttonWidth,buttonHeight)
                .scale(scale.value)
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            onClick()
                            selected.value = true
                        }

                        MotionEvent.ACTION_UP -> {
                            selected.value = false
                        }
                    }
                    true
                }
        ) {
            Text(text = text, fontSize = fontSize,
                color = textColor,modifier=modifier.fillMaxWidth()
                , textAlign = TextAlign.Center,)
        }
    }
}



@Composable
fun DraggablePicker() {
    var day by remember{
        mutableStateOf("16")
    }
    var month by remember {
        mutableStateOf("Jul")
    }
    var year by remember {
        mutableStateOf("1980")
    }
    Box(
        modifier = Modifier
            .background(gray)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Picker(
                list = (1..31).toList(),
                onValueChanged = {
                    day = it.toString()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                showAmount = 10
            )
            Picker(
                listOf(
                    "Jan",
                    "Feb",
                    "Mar",
                    "Apr",
                    "May",
                    "Jun",
                    "Jul",
                    "Aug",
                    "Sep",
                    "Oct",
                    "Nov",
                    "Dec"
                ),
                onValueChanged = {
                    month = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                showAmount = 6
            )
            Picker(
                list = (1950..2010).toList(),
                onValueChanged = {
                    year = it.toString()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                showAmount = 8
            )
        }
        Rechtangle(
            modifier = Modifier
                .height(260.dp)
                .width(50.dp)
        )
        Text(
            "Date of Birth: $month $day $year",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 130.dp),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp
        )
    }
}

@Composable
fun <T>Picker(
    list:List<T>,
    showAmount:Int = 10,
    modifier: Modifier = Modifier,
    style:PickerStyle = PickerStyle(),
    onValueChanged:(T)->Unit
) {

    val listCount by remember {
        mutableStateOf(list.size)
    }

    val correctionValue by remember {
        if(list.size%2 == 0){
            mutableStateOf(1)
        }else{
            mutableStateOf(0)
        }
    }

    var dragStartedX by remember {
        mutableStateOf(0f)
    }

    var currentDragX by remember {
        mutableStateOf(0f)
    }

    var oldX by remember {
        mutableStateOf(0f)
    }

    Canvas(
        modifier = modifier
            .pointerInput(true){
                detectDragGestures(
                    onDragStart = { offset ->
                        dragStartedX = offset.x
                    },
                    onDragEnd = {
                        val spacePerItem = size.width/showAmount
                        val rest = currentDragX % spacePerItem

                        val roundUp = abs(rest/spacePerItem).roundToInt() == 1
                        val newX = if(roundUp){
                            if(rest<0){
                                currentDragX + abs(rest) - spacePerItem
                            }else{
                                currentDragX - rest + spacePerItem
                            }
                        }else{
                            if(rest < 0){
                                currentDragX + abs(rest)
                            }else{
                                currentDragX - rest
                            }
                        }
                        currentDragX = newX.coerceIn(
                            minimumValue = -(listCount/2f)*spacePerItem,
                            maximumValue = (listCount/2f-correctionValue)*spacePerItem
                        )
                        val index = (listCount/2)+(currentDragX/spacePerItem).toInt()
                        onValueChanged(list[index])
                        oldX = currentDragX
                    },
                    onDrag = {change,_ ->
                        val changeX = change.position.x
                        val newX = oldX + (dragStartedX-changeX)
                        val spacePerItem = size.width/showAmount
                        currentDragX = newX.coerceIn(
                            minimumValue = -(listCount/2f)*spacePerItem,
                            maximumValue = (listCount/2f-correctionValue)*spacePerItem
                        )
                        val index = (listCount/2)+(currentDragX/spacePerItem).toInt()
                        onValueChanged(list[index])
                    }
                )
            }
    ){

        val top = 0f
        val bot = size.height

        drawContext.canvas.nativeCanvas.apply {
            drawRect(
                Rect(-2000,top.toInt(),size.width.toInt()+2000,bot.toInt()),
                Paint().apply {
                    color = white.copy(alpha = 0.8f).toArgb()
                    setShadowLayer(
                        30f,
                        0f,
                        0f,
                        android.graphics.Color.argb(50,0,0,0)
                    )
                }
            )
        }
        val spaceForEachItem = size.width/showAmount
        for(i in 0 until listCount){
            val currentX = i * spaceForEachItem - currentDragX -
                    ((listCount-1+correctionValue - showAmount)/2*spaceForEachItem)

            val lineStart = Offset(
                x = currentX ,
                y = 0f
            )

            val lineEnd = Offset(
                x = currentX,
                y = style.lineLength
            )

            drawLine(
                color = style.lineColor,
                strokeWidth = 1.5.dp.toPx(),
                start = lineStart,
                end = lineEnd
            )

            drawContext.canvas.nativeCanvas.apply {
                val y = style.lineLength + 5.dp.toPx() + style.textSize.toPx()

                drawText(
                    list[i].toString(),
                    currentX,
                    y,
                    Paint().apply {
                        textSize = style.textSize.toPx()
                        textAlign = Paint.Align.CENTER
                        isFakeBoldText = true
                    }
                )
            }

        }


    }

}
@Composable
fun Rechtangle(
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
    ){
        drawRoundRect(
            size = Size(size.width,size.height),
            style = Stroke(width = 10f, join = StrokeJoin.Round),
            color = Orange,
            cornerRadius = CornerRadius(25f,25f)
        )
    }
}

data class PickerStyle(
    val lineColor:Color = Orange,
    val lineLength:Float = 45f,
    val textSize:TextUnit = 16.sp
)


@Preview
@Composable
fun PreviewProgressBar() {
    ProgressBar(progress = 0.5f,color= Color.Green,modifier= Modifier, strokeCap = StrokeCap.Round, trackColor = Color.LightGray)
}