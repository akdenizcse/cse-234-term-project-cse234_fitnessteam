package com.example.fitnesstrackerandplanner

import FirebaseHelper
import LoginPage
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.DisplayMode.Companion.Picker
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fitnesstrackerandplanner.ui.theme.*
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
//TODO: Add gradient text coloring, draggable picker,pop up menu
class MainActivity : ComponentActivity() {
    val firebaseHelper=FirebaseHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        val db= Firebase.firestore
        setContent {
            val context= LocalContext.current
            val sharedPrefManager by lazy{SharedPrefManager(context)}
            val userName= sharedPrefManager.getCurrentUsername()

            if (userName != null) {
                firebaseHelper.fetchHeight(userName) { value ->
                    if (value != null) {
                        sharedPrefManager.saveCurrentUserHeight(value)
                    } else {

                    }

                    // Once height is fetched, fetch weight
                    firebaseHelper.fetchWeight(userName) { value ->
                        if (value != null) {
                            sharedPrefManager.saveCurrentUserWeight(value)
                        } else {
                            // Handle case where weight is not found or an error occurs
                        }
                    }
                }
            }else{
            }

            val navigationController = rememberNavController()
            val firebaseHelper by remember { mutableStateOf(FirebaseHelper()) }

            FitnessTrackerAndPlannerTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    BottomAppBar(navigationController, firebaseHelper)
                }
            }
        }
    }
}

@Composable
fun BottomAppBar(navigationController: NavHostController, firebaseHelper: FirebaseHelper) {
    val context = LocalContext.current
    val exList by lazy{initializeExercises()}
    var currentRoute by remember { mutableStateOf(Screens.LoginPage.screen) }
    val selected = remember { mutableStateOf(Icons.Default.Home) }

    Scaffold(
        bottomBar = {
            if (currentRoute != Screens.SignInPage.screen && currentRoute != Screens.LoginPage.screen) {
                NavigationBar(containerColor = SignificantlyDarkerPowderBlue,modifier = Modifier.height(75.dp)) {
                    IconButton(
                        onClick = {
                            selected.value = Icons.Default.Home
                            navigationController.navigate(Screens.Home.screen) {
                                popUpTo(0)
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(90.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .height(90.dp)
                                .padding(top = 15.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(26.dp)
                                    .weight(1f),
                                tint = if (selected.value == Icons.Default.Home) Color.White else Color.DarkGray
                            )
                            Text(
                                text = "Home",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1.5f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    IconButton(
                        onClick = {
                            selected.value = Icons.Default.Star
                            navigationController.navigate(Screens.Goals.screen) {
                                popUpTo(0)
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(90.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally
                            ,modifier = Modifier
                                .height(90.dp)
                                .padding(top = 15.dp)) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(26.dp)
                                    .weight(1f),
                                tint = if (selected.value == Icons.Default.Star) Color.White else Color.DarkGray
                            )
                            Text("Goals",modifier=Modifier.weight(1.5f))
                        }
                    }

                    val fabShape = RoundedCornerShape(50)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(6.dp),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        FloatingActionButton(
                            contentColor = PurpleGrey40,
                            containerColor = Color.White,
                            elevation = FloatingActionButtonDefaults.elevation(35.dp),
                            shape = fabShape,
                            onClick = {
                                Toast.makeText(context, "GO!", Toast.LENGTH_SHORT).show()
                                navigationController.navigate(Screens.StartAnExercise.screen) {
                                    popUpTo(0)
                                }
                            }
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = null,
                                tint = if (selected.value == Icons.Default.Add) Pink40 else PurpleGrey40
                            )
                        }
                    }

                    IconButton(
                        onClick = {
                            selected.value = Icons.Default.Favorite
                            navigationController.navigate(Screens.Activities.screen) {
                                popUpTo(0)
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(90.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier
                            .height(90.dp)
                            .padding(top = 15.dp)) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(26.dp)
                                    .weight(1f),
                                tint = if (selected.value == Icons.Default.Favorite) Color.White else Color.DarkGray
                            )
                            Text(text = "Activities", fontSize = 15.sp,modifier= Modifier
                                .weight(1.5f)
                                .fillMaxWidth(),
                                textAlign = TextAlign.Center, softWrap = false,)
                        }
                    }
                    IconButton(
                        onClick = {
                            selected.value = Icons.Default.AccountCircle
                            navigationController.navigate(Screens.Profile.screen) {
                                popUpTo(0)
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(90.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier
                            .height(90.dp)
                            .padding(top = 15.dp)) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(26.dp)
                                    .weight(1f),
                                tint = if (selected.value == Icons.Default.AccountCircle) Color.White else Color.DarkGray
                            )
                            Text(text = "Profile",modifier=Modifier.weight(1.5f))
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navigationController,
            startDestination = Screens.LoginPage.screen,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screens.LoginPage.screen) {
                LoginPage(navigationController = navigationController)
                currentRoute = Screens.LoginPage.screen
            }
            composable(Screens.Activities.screen) {
                Activities()
                currentRoute = Screens.Activities.screen
            }
            composable(Screens.Home.screen) {
                Home()
                currentRoute = Screens.Home.screen
            }
            composable(Screens.Goals.screen) {
                Goals(navController = navigationController, exList = exList)
                currentRoute = Screens.Goals.screen
            }
            composable(Screens.Profile.screen) {
                Profile()
                currentRoute = Screens.Profile.screen
            }
            composable(Screens.StartAnExercise.screen) {
                initiateStartAnExercise(navController = navigationController)
                currentRoute = Screens.StartAnExercise.screen
            }
            composable(Screens.ExercisePage1.screen) {
                ExercisePage1()
                currentRoute = Screens.ExercisePage1.screen
            }
            composable(Screens.ExercisePage2.screen) {
                ExercisePage2()
                currentRoute = Screens.ExercisePage2.screen
            }
            composable(Screens.ExercisePage3.screen) {
                ExercisePage3()
                currentRoute = Screens.ExercisePage3.screen
            }
            composable(Screens.ExercisePage4.screen) {
                ExercisePage4()
                currentRoute = Screens.ExercisePage4.screen
            }
            composable(Screens.SignInPage.screen) {
                SignUp(navigationController = navigationController)
                currentRoute = Screens.SignInPage.screen
            }
            composable(Screens.PostSignUp.screen){
                PostSignUp(navController = navigationController)
                currentRoute=Screens.PostSignUp.screen
            }
            composable(
                route = Screens.SubExerciseDetail.routeWithArgs,
                arguments = listOf(navArgument("exerciseID") { type = NavType.IntType })
            ) { backStackEntry ->
                val exerciseID = backStackEntry.arguments?.getInt("exerciseID") ?: 0
                SubExercisePage(navController = navigationController, exercise = getExerciseByID(exList,exerciseID))
                currentRoute = Screens.SubExerciseDetail(exerciseID).screen
            }
            composable(
                route = Screens.ExercisePage.routeWithArgs,
                arguments = listOf(navArgument("exerciseName") { type = NavType.StringType })
            ) { backStackEntry ->
                val exerciseName = backStackEntry.arguments?.getString("exerciseName") ?: ""
                ExercisePage1(
                    exerciseName = exerciseName
                )
            }

            // Main Exercise Type screens

            // Sub-Exercise Detail screens




        }
    }
}
@Preview
@Composable
fun previewBottomAppbar(){
    Scaffold(
        bottomBar = {
                NavigationBar(containerColor = LimeGreen, modifier = Modifier.height(75.dp)) {
                    IconButton(
                        onClick = {

                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(90.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .height(90.dp)
                                .padding(top = 15.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(26.dp)
                                    .weight(1f),
                            )
                            Text(
                                text = "Home",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    IconButton(
                        onClick = {


                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(90.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .height(90.dp)
                                .padding(top = 15.dp),) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(26.dp)
                                    .weight(1f),
                            )
                            Text("Goals",modifier= Modifier
                                .fillMaxWidth()
                                .weight(1f), textAlign = TextAlign.Center)
                        }
                    }

                    val fabShape = RoundedCornerShape(50)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(6.dp),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        FloatingActionButton(
                            contentColor = PurpleGrey40,
                            containerColor = Color.White,
                            elevation = FloatingActionButtonDefaults.elevation(55.dp),
                            shape = fabShape,
                            onClick = {

                            }
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = null,
                            )
                        }
                    }

                    IconButton(
                        onClick = {

                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(90.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .height(90.dp)
                                .padding(top = 15.dp),) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(26.dp)
                                    .weight(1f),
                            )
                            Text(text = "Activities", fontSize = 13.sp,modifier= Modifier
                                .fillMaxWidth()
                                .weight(1f), textAlign = TextAlign.Center)
                        }
                    }

                    IconButton(
                        onClick = {

                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(90.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .height(90.dp)
                                .padding(top = 15.dp),) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(26.dp)
                                    .weight(1f),
                            )
                            Text(text = "Profile",modifier= Modifier
                                .fillMaxWidth()
                                .weight(1f), textAlign = TextAlign.Center)
                        }
                    }
                }
            }

    ){

    }
}
