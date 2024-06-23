import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.fitnesstrackerandplanner.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


@RunWith(AndroidJUnit4::class)
@org.robolectric.annotation.Config(sdk = [28], manifest = Config.NONE)
class ContactPageTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testContactPage() {
        composeTestRule.setContent {
            ContactPage()
        }

        // Enter sender email
        composeTestRule.onNodeWithText("Enter sender email address")
            .performTextInput("test@example.com")

        // Enter email subject
        composeTestRule.onNodeWithText("Enter email subject")
            .performTextInput("Test Subject")

        // Enter email body
        composeTestRule.onNodeWithText("Enter email body")
            .performTextInput("This is a test email body.")

        // Click the send email button
        composeTestRule.onNodeWithText("Send Email")
            .performClick()
    }
}