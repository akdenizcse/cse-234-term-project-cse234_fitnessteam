    package com.example.fitnesstrackerandplanner

    import FirebaseHelper
    import android.widget.Toast
    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.Row
    import androidx.compose.foundation.layout.Spacer
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.layout.size
    import androidx.compose.foundation.text.KeyboardOptions
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.filled.ArrowBack
    import androidx.compose.material3.ButtonDefaults
    import androidx.compose.material3.Icon
    import androidx.compose.material3.IconButton
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
    import androidx.compose.ui.text.input.ImeAction
    import androidx.compose.ui.text.input.KeyboardType
    import androidx.compose.ui.text.input.PasswordVisualTransformation
    import androidx.compose.ui.unit.dp
    import androidx.navigation.NavHostController
    import com.example.fitnesstrackerandplanner.ui.theme.Cerulean
    import com.example.fitnesstrackerandplanner.ui.theme.DeepNavyBlue
    import com.example.fitnesstrackerandplanner.ui.theme.LightPaletteGray
    import com.example.fitnesstrackerandplanner.ui.theme.Taupe
    import com.example.fitnesstrackerandplanner.ui.theme.darkGray
    import com.example.fitnesstrackerandplanner.ui.theme.focusedDarkGray
    import java.time.LocalDateTime

    //dont let to user to sign up with same email adddress and redirect to login page
    @Composable
    fun SignUp(navigationController: NavHostController) {
        var first_name by remember { mutableStateOf("") }
        var last_name by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var user_name by remember { mutableStateOf("") }
        var password1 by remember { mutableStateOf("") }
        var password2 by remember { mutableStateOf("") }
        var isBlankField by remember { mutableStateOf(false) }
        var passwordsDoNotMatch by remember { mutableStateOf(false) }
        var emailOrUsernameTaken by remember { mutableStateOf(false) }
        var isSimplePassword by remember { mutableStateOf(false) }
        var dateOfBirth: LocalDateTime by remember{ mutableStateOf(LocalDateTime.now()) }
        val thisContext = LocalContext.current
        val sharedPrefManager by lazy { SharedPrefManager(thisContext) }
        val firebaseHelper by lazy { FirebaseHelper() }

        Surface(color = DeepNavyBlue, modifier = Modifier.fillMaxSize()) {
            Column {
                // Back Button Row
                Spacer(modifier=Modifier.size(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = {
                        navigationController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null,
                            modifier=Modifier.size(50.dp,80.dp), tint = Color.White)
                    }
                }

                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = first_name,
                        onValueChange = { first_name = it },
                        label = { Text("First Name") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = darkGray,
                            focusedContainerColor = focusedDarkGray,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Taupe,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = LightPaletteGray
                        ),
                                    keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        )
                    )
                    OutlinedTextField(
                        value = last_name,
                        onValueChange = { last_name = it },
                        label = { Text("Last Name") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = darkGray,
                            focusedContainerColor = focusedDarkGray,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Taupe,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = LightPaletteGray
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        )
                    )
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = darkGray,
                            focusedContainerColor = focusedDarkGray,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Taupe,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = LightPaletteGray
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )
                    OutlinedTextField(
                        value = user_name,
                        onValueChange = { user_name = it },
                        label = { Text("Username") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = darkGray,
                            focusedContainerColor = focusedDarkGray,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Taupe,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = LightPaletteGray
                        ),
                        maxLines = 1
                    )
                    OutlinedTextField(
                        value = password1,
                        onValueChange = { password1 = it },
                        label = { Text("Password") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = darkGray,
                            focusedContainerColor = focusedDarkGray,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Taupe,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = LightPaletteGray
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = PasswordVisualTransformation()
                    )
                    OutlinedTextField(
                        value = password2,
                        onValueChange = { password2 = it },
                        label = { Text("Confirm Password") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = darkGray,
                            focusedContainerColor = focusedDarkGray,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Taupe,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = LightPaletteGray
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = PasswordVisualTransformation()
                    )
                    MyWheelDatePicker(dateOfBirth = remember { mutableStateOf(dateOfBirth) })
                    if (isBlankField) {
                        Text(text = "All fields are required!", color = Color.Red)
                    }
                    if (passwordsDoNotMatch) {
                        Text(text = "Passwords do not match!", color = Color.Red)
                    }
                    if (isSimplePassword) {
                        Text(text = "Password length must be greater than 6!", color = Color.Red)

                    }

                    if (emailOrUsernameTaken) {
                        Text(text = "Email or username taken!", color = Color.Red)

                    }

                    OutlinedButton(
                        onClick = {
                            isBlankField = false
                            passwordsDoNotMatch = false
                            emailOrUsernameTaken = false
                            isSimplePassword = false
                            fun fieldsAreValid(): Boolean {
                                if (!password1.equals(password2)) {
                                    passwordsDoNotMatch = true
                                    return false
                                }
                                if (!(password1.length > 6)) {
                                    isSimplePassword = true
                                    return false
                                } else {
                                    isBlankField = !(first_name.isNotBlank() &&
                                            last_name.isNotBlank() &&
                                            email.isNotBlank() &&
                                            user_name.isNotBlank() &&
                                            password1.isNotBlank() &&
                                            password2.isNotBlank()
                                            )
                                    return !isBlankField
                                }
                            }
                            if (fieldsAreValid()) {
                                firebaseHelper.addUser(
                                    thisContext,
                                    first_name, last_name, email, user_name, password1,dateOfBirth
                                ) { isSuccessfull ->
                                    if (isSuccessfull) {
                                        sharedPrefManager.saveCurrentUserFirstName(first_name)
                                        sharedPrefManager.saveCurrentUsername(user_name)
                                        navigationController.navigate(Screens.PostSignUp.screen)
                                    } else {
                                        Toast.makeText(
                                            thisContext,
                                            "User can not be added",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        emailOrUsernameTaken = true
                                    }
                                }
                            } else {

                            }
                        },
                        colors = ButtonDefaults.buttonColors(Cerulean),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "SIGN UP", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }





