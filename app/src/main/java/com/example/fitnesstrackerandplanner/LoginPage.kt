package com.example.fitnesstrackerandplanner

import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.fitnesstrackerandplanner.ui.theme.Beige
import com.example.fitnesstrackerandplanner.ui.theme.FitnessTrackerAndPlannerTheme
import com.example.fitnesstrackerandplanner.ui.theme.PurpleGrey40


@Composable
fun LoginPage(navigationController: NavHostController, db: SQLiteDatabase?){
    val img= painterResource(id = R.drawable.dumbell_splash)
    var user_name by remember {mutableStateOf("")}
    var password by remember{mutableStateOf("")}
    var loginError by remember { mutableStateOf(false) }
    val currentContext= LocalContext.current
    Surface(color = PurpleGrey40, modifier = Modifier.fillMaxSize()){
        Column(modifier=Modifier, horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Image(
                painter=img,contentDescription=null,modifier=Modifier.align(Alignment.CenterHorizontally)
            )
            OutlinedTextField(value =user_name , colors = TextFieldDefaults.colors(Beige),
                onValueChange ={user_name=it},
                label={Text("Username")},
                modifier = Modifier,
                maxLines=1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            OutlinedTextField(value =password , colors = TextFieldDefaults.colors(Beige),
                onValueChange ={password=it},
                label={Text("Password")},
                modifier = Modifier,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),

            )

            if (loginError) {
                Text(
                    text = "Invalid username or password",
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }


            OutlinedButton(
                onClick ={
                    if(db!=null){
                   // if(isValidLogin(db,user_name,password)) {
                        navigationController.navigate(Screens.Home.screen)
                    //}
                    //else{
                      //  loginError = true
                    //}
                }
                         }
                ,colors=ButtonDefaults.buttonColors(Beige),
                modifier=Modifier
                    .padding(15.dp))
            {
                Text("LOGIN",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 25.sp,
                    modifier = Modifier)
            }
            Text(text="Not a member? Sign up!",
                color= Color.Cyan,
                fontWeight = FontWeight.Bold,
                modifier=Modifier.clickable {
                    navigationController.navigate(Screens.SignInPage.screen){
                        popUpTo(0)
                    }
                }
                )



        }

    }


}
fun isValidLogin(db: SQLiteDatabase?, username: String, password: String): Boolean {
    if (db == null) return false

    val projection = arrayOf(DatabaseHelper.COLUMN_USER_NAME)
    val selection = "${DatabaseHelper.COLUMN_USER_NAME} = ? AND ${DatabaseHelper.COLUMN_USER_PASSWORD} = ?"
    val selectionArgs = arrayOf(username, password)

    val cursor = db.query(
        DatabaseHelper.TABLE_USER,
        projection,
        selection,
        selectionArgs,
        null,
        null,
        null
    )

    val isValid = cursor.count > 0
    cursor.close()
    return isValid
}


