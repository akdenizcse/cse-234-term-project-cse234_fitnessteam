import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitnesstrackerandplanner.AnimatedButton
import com.example.fitnesstrackerandplanner.SharedPrefManager
import com.example.fitnesstrackerandplanner.ui.theme.CharcoalGray
import com.example.fitnesstrackerandplanner.ui.theme.DeepNavyBlue
import com.example.fitnesstrackerandplanner.ui.theme.Eggshel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UpdateHeightWeightScreen(onUpdateSuccess: () -> Unit) {
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    val context = LocalContext.current
    val firebaseHelper = FirebaseHelper()
    val sharedPrefManager = SharedPrefManager(context)
    val username = sharedPrefManager.getCurrentUsername()
    val showToast = { message: String ->
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    var isSuccessful by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavyBlue)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = height,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = CharcoalGray,
                focusedContainerColor = CharcoalGray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.Gray
            ),
            onValueChange = { height = it },
            label = { Text("Height (cm)") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = weight,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = CharcoalGray,
                focusedContainerColor = CharcoalGray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.Gray
            ),
            onValueChange = { weight = it },
            label = { Text("Weight (kg)") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier=Modifier.size(15.dp))
        AnimatedButton(
            text = "Confirm",
            buttonWidth = 300.dp,
            buttonHeight = 60.dp,
            onClick = {
                if (username != null && weight != "" && height != "") {
                    firebaseHelper.setHeightWeight(
                        username,
                        height.toInt(),
                        weight.toInt()
                    ) { success ->
                        if (success) {
                            isSuccessful = true
                            Log.d(
                                "UpdateMeasurementsScreen",
                                "Successfully updated user measurements with values $weight weight, $height" +
                                        "height"
                            )
                        } else {
                            Log.e("UpdateMeasurementsScreen", "Error updating user measurements.")
                        }
                    }
                }
            }
        )
        if (isSuccessful)
                Row(modifier=Modifier.padding(top=35.dp)) {
                    Icon(imageVector=Icons.Default.Check,tint=Color.Green, contentDescription = null)
                    Text("Successfully updated height and weight!", fontWeight = FontWeight.Bold,
                        fontSize=14.sp, color = Color.Green,modifier=Modifier.padding(start=6.dp,end=6.dp))

            }

    }
}

