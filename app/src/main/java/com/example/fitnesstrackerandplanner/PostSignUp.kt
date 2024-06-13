package com.example.fitnesstrackerandplanner

import FirebaseHelper
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.fitnesstrackerandplanner.ui.theme.Cerulean
import com.example.fitnesstrackerandplanner.ui.theme.DeepNavyBlue
import com.example.fitnesstrackerandplanner.ui.theme.Eggshel
//TODO:After clicking CONFIRM the height and weight info will be logged to the database
@Composable
fun PostSignUp(navController: NavHostController?){
    var height by remember {mutableStateOf("145")}
    var weight by remember{mutableStateOf("125")}
    val firebaseHelper by lazy { FirebaseHelper() }
    val context= LocalContext.current
    val sharedPrefManager by lazy{SharedPrefManager(context)}
    val currentUser=sharedPrefManager.getCurrentUsername()
    Surface(modifier= Modifier.fillMaxSize(),color= DeepNavyBlue){

        Column(verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(id = R.string.welcomeText),
                color = Color.White,
                fontSize = 35.sp,
                fontWeight = FontWeight.ExtraBold, modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style= TextStyle(
                    brush= Brush.linearGradient(
                        colors=listOf(Color.Magenta,Color.Cyan)
                    )
                )
            )
            Spacer(modifier=Modifier.size(30.dp))

            Text(text=stringResource(id=R.string.heightWeightSelectInfo),
                color = Color.White,
                textAlign = TextAlign.Start, fontSize = 15.sp,
                modifier=Modifier.padding(start=8.dp,end=5.dp),
                lineHeight=18.sp,
                style= TextStyle(fontStyle= FontStyle.Italic)
            )
            Spacer(modifier=Modifier.size(50.dp))
            Text("Select your height and weight", fontSize = 25.sp,
                modifier=Modifier.align(Alignment.Start).padding(start=10.dp),
                color = Eggshel)

            Picker(
                (75..215).toList(),
                onValueChanged = { height = it.toString() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                showAmount = 10
            )
            Picker(
                (0..250).toList(),
                onValueChanged = { weight = it.toString() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                showAmount = 10
            )
            Box() {
                Rechtangle(
                    modifier = Modifier
                        .height(150.dp)
                        .width(50.dp)
                        .absoluteOffset(0.dp, -160.dp)
                )
            }

            Column(verticalArrangement=Arrangement.spacedBy(5.dp)) {

                Text(text="Your measurements are",color=Color.White,
                    fontWeight=FontWeight.ExtraBold, fontSize = 25.sp)
                Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {

                    Text("Height ", color = Color.White, fontSize = 20.sp)
                    Text(text ="$height cm" , fontSize = 20.sp, color = Cerulean)
                    Text("Weight ", color = Color.White, fontSize = 20.sp)
                    Text(text = "$weight kg", fontSize = 20.sp, color = Cerulean)
                }
            }


            AnimatedButton(
                onClick = {if(navController!=null){
                    if (currentUser != null) {
                        firebaseHelper.setHeightWeight(
                            height =height.toInt(),
                            weight = weight.toInt(),
                            userName =currentUser) { successful ->
                            if (successful) {
                                Toast.makeText(context,
                                    "Height and weight updated successfully!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                sharedPrefManager.saveCurrentUserHeight(height.toInt())
                                sharedPrefManager.saveCurrentUserWeight(weight.toInt())
                                navController.navigate(Screens.Home.screen)
                            }
                            else{
                                Toast.makeText(context, "Failed to update height and weight!", Toast.LENGTH_SHORT).show()
                                Log.e("Firebase Helper","Failed to set height and weight!")
                            }
                        }

                        }
                    }

                          },
                text = "CONFIRM", color = Cerulean, buttonWidth = 200.dp, buttonHeight = 50.dp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier=Modifier.size(80.dp))


        }



    }
}
@Preview
@Composable
fun PostSignUpPreview(){
    PostSignUp(navController =null)
}