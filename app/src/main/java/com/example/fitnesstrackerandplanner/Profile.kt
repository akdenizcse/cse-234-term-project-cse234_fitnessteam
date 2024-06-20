package com.example.fitnesstrackerandplanner
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.fitnesstrackerandplanner.ui.theme.*
import androidx.compose.material3.Surface
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@Composable
fun Profile(navController: NavHostController) {
//TODO:CONTACT US PAGE WITH INTENT EMAILS
    val context= LocalContext.current
    val sharedPrefManager=SharedPrefManager(context)


    val options= initiailizeProfileElements(sharedPrefManager,navController)
    Surface(modifier=Modifier.fillMaxSize(),color= DeepNavyBlue){

            RecyclerView(profilePageElements = options, greetingMessage ="Fitness and Health Tracker\nver1", icon = null, subTitle = null,
                shape= RoundedCornerShape(20.dp)
            )

    }

}

