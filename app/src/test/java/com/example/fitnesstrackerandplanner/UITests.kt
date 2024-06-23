package com.example.fitnesstrackerandplanner

import android.content.SharedPreferences
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.robolectric.android.DeviceConfig

lateinit var navController:NavHostController
@RunWith(AndroidJUnit4::class) // Ensure you use the JUnit 4 runner
class UITests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun configurationOverrideTest() {
        // Set up the desired configuration
        val forcedWidth = 1280.dp
        val forcedHeight = 800.dp

        val navController = mock(NavHostController::class.java)

        // Use composeTestRule to set the content with the overridden configuration
        composeTestRule.setContent {
            androidx.compose.ui.platform.LocalDensity.current.apply {
                val density = this.density
                val fontScale = this.fontScale

                androidx.compose.ui.platform.LocalConfiguration.current.apply {
                    screenWidthDp = with(density) { forcedWidth.toPx().toInt() / density }.toInt()
                    screenHeightDp = with(density) { forcedHeight.toPx().toInt() / density }.toInt()
                }
            }
            Home(navController)
        }

         composeTestRule.onNodeWithText("Weight").assertExists()
    }
}