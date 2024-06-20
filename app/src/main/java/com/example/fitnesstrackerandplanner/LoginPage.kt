import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.fitnesstrackerandplanner.AnimatedButton
import com.example.fitnesstrackerandplanner.R
import com.example.fitnesstrackerandplanner.Screens
import com.example.fitnesstrackerandplanner.SharedPrefManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.example.fitnesstrackerandplanner.ui.theme.*

@Composable
fun LoginPage(navigationController: NavHostController) {
    val img = painterResource(id = R.drawable.dumbell_splash)
    var user_name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf(false) }
    val currentContext = LocalContext.current
    val firebaseHelper by lazy { FirebaseHelper() }
    val sharedPrefManager by lazy { SharedPrefManager(currentContext) }
    var isValidUser by remember { mutableStateOf<Boolean?>(null) }

    Surface(color = DeepNavyBlue, modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            Image(
                painter = img, contentDescription = null, modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            OutlinedTextField(
                value = user_name,
                leadingIcon = {Icon(imageVector = Icons.Outlined.AccountCircle, contentDescription = null
                ,tint= Eggshel)},
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = darkGray,
                    focusedContainerColor = focusedDarkGray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Taupe,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = LightPaletteGray

                ),
                onValueChange = { user_name = it },
                label = { Text("Username") },
                modifier = Modifier,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            OutlinedTextField(
                value = password,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = darkGray,
                    focusedContainerColor = focusedDarkGray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Taupe,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = LightPaletteGray
                ),
                leadingIcon = {Icon(Icons.Default.Lock, contentDescription = null,
                    tint= Eggshel)},
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )

            if (loginError) {
                Text(
                    text = "Invalid username or password",
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            Spacer(modifier=Modifier.padding(6.dp))

            AnimatedButton(
                onClick = {

                    firebaseHelper.loginAuth(user_name,password){isValid->
                        if(isValid){
                            sharedPrefManager.saveCurrentUsername(user_name)
                            navigationController.navigate(Screens.Home.screen)


                        }
                        else{
                            Toast.makeText(currentContext,"User could not be found!",Toast.LENGTH_SHORT).show()

                        }
                    }

                },
                color = Cerulean,
                fontSize = 25.sp,
                fontWeight = FontWeight.ExtraBold,
                buttonWidth = 300.dp,
                buttonHeight = 50.dp,


                text="LOGIN"

                )

            Spacer(modifier=Modifier.padding(3.dp))

            Text(
                text = "Not a member? Sign up!",
                color = DarkPeriwinkle,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    navigationController.navigate(Screens.SignInPage.screen)
                }
            )
        }

    }
}



