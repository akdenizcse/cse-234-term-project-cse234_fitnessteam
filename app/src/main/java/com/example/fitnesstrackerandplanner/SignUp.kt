package com.example.fitnesstrackerandplanner

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.fitnesstrackerandplanner.ui.theme.Beige
import com.example.fitnesstrackerandplanner.ui.theme.PurpleGrey40
import java.util.Calendar

@Composable
fun SignUp(db:SQLiteDatabase,navigationController:NavHostController,dbHelper: DatabaseHelper){
    var first_name by remember { mutableStateOf("") }
    var last_name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var user_name by remember { mutableStateOf("") }
    var password by remember{ mutableStateOf("") }
    val thisContext = LocalContext.current
    val sharedPrefManager by lazy{SharedPrefManager(thisContext)}

    Surface(color = PurpleGrey40, modifier = Modifier.fillMaxSize()){
        Column(modifier= Modifier, horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            OutlinedTextField(value =first_name , colors = TextFieldDefaults.colors(Beige),
                onValueChange ={first_name=it},
                label={ Text("First Name") },
                modifier = Modifier,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            OutlinedTextField(value =last_name , colors = TextFieldDefaults.colors(Beige),
                onValueChange ={last_name=it },
                label={ Text("Last Name") },
                modifier = Modifier,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            OutlinedTextField(value =email , colors = TextFieldDefaults.colors(Beige),
                onValueChange ={email=it},
                label={ Text("Mail address") },
                modifier = Modifier,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )



            OutlinedTextField(value =user_name , colors = TextFieldDefaults.colors(Beige),
                onValueChange ={user_name=it},
                label={ Text("Username") },
                modifier = Modifier,
                maxLines=1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            OutlinedTextField(value =password , colors = TextFieldDefaults.colors(Beige),
                onValueChange ={password=it},
                label={ Text("Password") },
                modifier = Modifier,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            OutlinedButton(
                onClick = {
                   val result=dbHelper.addUser(db=db, context = thisContext,firstName=first_name,lastName=last_name,email=email,userName=user_name,password=password)
                    if(result==0) {
                        sharedPrefManager.saveCurrentUser(user_name)
                        navigationController.navigate(Screens.Home.screen)
                    }
                    else{

                    }
                },colors= ButtonDefaults.buttonColors(Beige),
                modifier= Modifier
                    .padding(15.dp))
            {
                Text("SIGN UP",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 25.sp,
                    modifier = Modifier
                )
            }





        }

    }
}