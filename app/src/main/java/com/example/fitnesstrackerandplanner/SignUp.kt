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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.fitnesstrackerandplanner.ui.theme.Beige
import com.example.fitnesstrackerandplanner.ui.theme.PurpleGrey40
import com.example.fitnesstrackerandplanner.ui.theme.SurfaceGreen
import java.util.Calendar
//dont let to user to sign up with same email adddress and redirect to login page
@Composable
fun SignUp(db:SQLiteDatabase,navigationController:NavHostController,dbHelper: DatabaseHelper){
    var first_name by remember { mutableStateOf("") }
    var last_name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var user_name by remember { mutableStateOf("") }
    var password1 by remember{ mutableStateOf("") }
    var password2 by remember{ mutableStateOf("") }
    var isBlankField by remember {mutableStateOf(false)}
    var passwordsDoNotMatch by remember { mutableStateOf(false) }
    val thisContext = LocalContext.current
    val sharedPrefManager by lazy{SharedPrefManager(thisContext)}
    var blank_field by remember{mutableStateOf("")}
    Surface(color = SurfaceGreen, modifier = Modifier.fillMaxSize()){
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
            OutlinedTextField(value =password1 , colors = TextFieldDefaults.colors(Beige),
                onValueChange ={password1=it},
                label={ Text("Password") },
                modifier = Modifier,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation= PasswordVisualTransformation()

            )
            OutlinedTextField(value =password2 , colors = TextFieldDefaults.colors(Beige),
                onValueChange ={password2=it},
                label={ Text("Password") },
                modifier = Modifier,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation= PasswordVisualTransformation()

            )

            if(isBlankField)
                Text(text = "Can not leave the $blank_field field blank", color = Color.Red)
            else if (passwordsDoNotMatch) {
                 Text(text = "Passwords do not match", color = Color.Red)
             }

            val fields = mutableMapOf<String, String>()
            fields["first name"] = first_name
            fields["last name"] = last_name
            fields["email"] = email
            fields["username"] = user_name
            fields["password"] = password1
            fields["password"] = password2
            OutlinedButton(
                onClick = {
                    if(!(password1.length==0 || password2.length==0||
                        user_name.length==0||first_name.length==0||last_name.length==0)){
                    if (password1==password2) {
                        val result = dbHelper.addUser(
                            db = db,
                            context = thisContext,
                            firstName = first_name,
                            lastName = last_name,
                            email = email,
                            userName = user_name,
                            password = password1

                        )
                        if (result == 0) {
                            sharedPrefManager.saveCurrentUser(user_name)
                            navigationController.navigate(Screens.Home.screen)
                        } else {

                        }
                    }
                    else{
                        passwordsDoNotMatch=true
                    }
                    }
                    else{
                        isBlankField=true
                        for(s in fields){
                            if(s.toPair().second.isEmpty())
                                blank_field=s.toPair().first
                        }
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