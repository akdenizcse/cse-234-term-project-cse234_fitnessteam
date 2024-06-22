import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.input.TextFieldValue
import com.example.fitnesstrackerandplanner.ui.theme.Cerulean
import com.example.fitnesstrackerandplanner.ui.theme.DeepNavyBlue
import com.example.fitnesstrackerandplanner.ui.theme.LightPaletteGray
import com.example.fitnesstrackerandplanner.ui.theme.Taupe
import com.example.fitnesstrackerandplanner.ui.theme.darkGray
import com.example.fitnesstrackerandplanner.ui.theme.focusedDarkGray

@Composable
fun ContactPage() {
    Surface(
        color = DeepNavyBlue,
        modifier = Modifier.fillMaxSize()
    ) {
        ContactForm()
    }
}

@Composable
fun ContactForm() {
    val senderEmail = remember { mutableStateOf(TextFieldValue()) }
    val emailSubject = remember { mutableStateOf(TextFieldValue()) }
    val emailBody = remember { mutableStateOf(TextFieldValue()) }

    val ctx = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = senderEmail.value,
            onValueChange = { senderEmail.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            label = { Text("Enter sender email address") },
            textStyle = TextStyle(color = Color.White),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = darkGray,
                focusedContainerColor = focusedDarkGray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Taupe,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = LightPaletteGray

            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = emailSubject.value,
            onValueChange = { emailSubject.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            label = { Text("Enter email subject") },
            textStyle = TextStyle(color = Color.White),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = darkGray,
                focusedContainerColor = focusedDarkGray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Taupe,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = LightPaletteGray

            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = emailBody.value,
            onValueChange = { emailBody.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(120.dp),
            label = { Text("Enter email body") },
            textStyle = TextStyle(color = Color.White),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = darkGray,
                focusedContainerColor = focusedDarkGray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Taupe,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = LightPaletteGray

            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                val i = Intent(Intent.ACTION_SEND)

                val emailAddress = arrayOf(senderEmail.value.text)
                i.putExtra(Intent.EXTRA_EMAIL,emailAddress)
                i.putExtra(Intent.EXTRA_SUBJECT,emailSubject.value.text)
                i.putExtra(Intent.EXTRA_TEXT,emailBody.value.text)
                i.setType("message/rfc822")
                ctx.startActivity(Intent.createChooser(i,"Choose an Email client : "))


            },
            colors = ButtonDefaults.buttonColors(containerColor = Cerulean),
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Send Email",
                modifier = Modifier.padding(10.dp),
                color = Color.White,
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewContactPage() {
    ContactPage()
}
