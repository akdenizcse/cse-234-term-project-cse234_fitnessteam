package com.example.fitnesstrackerandplanner

import DietPage
import Exercise
import FirebaseHelper
import LoginPage
import SubExercise
import UpdateHeightWeightScreen
import android.annotation.SuppressLint
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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fitnesstrackerandplanner.ui.theme.*
import com.example.healthconnect.codelab.data.HealthConnectManager
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//TODO:Hours slept DO'ya basınca popup açsın ve oradan seçebilsin
//TODO: exercisesession database kaydetme ve çekme
//TODO:Activitiesi exercisesession ile doldurma
//TODO: Age datasını kaydolurken alma ve kaydetme

class MainActivity : ComponentActivity() {

    private lateinit var sharedPrefManager: SharedPrefManager
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        var fetchedExercises:List<Exercise>

        super.onCreate(savedInstanceState)
        sharedPrefManager = SharedPrefManager(this)
        val firebaseHelper = FirebaseHelper()
        sharedPrefManager.clearSelectedExercisesGO()
        sharedPrefManager.removeCurrentUserCaloriesConsumed()
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                fetchedExercises = firebaseHelper.initializeExercises()
                sharedPrefManager.saveAllExercises(fetchedExercises)
                Log.d("MainActivityFirebaseHelper", "Fetched exercises -- ${fetchedExercises.getOrNull(0)?.exerciseName ?: "No exercises"}")
            } catch (e: Exception) {
                Log.e("MainActivityFirebaseHelper", "Error initializing exercises", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(applicationContext, "Error fetching exercises", Toast.LENGTH_LONG).show()
                }
            }
        }



        installSplashScreen()

        setContent {
            val context = LocalContext.current
            val healthConnectManager: HealthConnectManager by lazy { HealthConnectManager(context) }
            val navigationController = rememberNavController()

            FitnessTrackerAndPlannerTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    BottomAppBar(navigationController, firebaseHelper)
                    // ExerciseInfoPage(subExercise = null)
                }
            }
        }
    }


    override fun onDestroy(){
        super.onDestroy()
        sharedPrefManager.clearAllValues()
        sharedPrefManager.removeCurrentUserCaloriesConsumed()
        sharedPrefManager.clearSelectedExercisesGO()
        }
}


@Composable
fun BottomAppBar(navigationController: NavHostController, firebaseHelper: FirebaseHelper) {
    val context = LocalContext.current
    var currentRoute by remember { mutableStateOf(Screens.LoginPage.screen) }
    val selected = remember { mutableStateOf(Icons.Default.Home) }
    var currentExerciseID by remember{ mutableStateOf(0) }
    val sharedPrefManager by lazy{SharedPrefManager(context)}
    val exList =sharedPrefManager.getAllExercises()
    Scaffold(
        bottomBar = {
            if (currentRoute != Screens.SignInPage.screen && currentRoute != Screens.LoginPage.screen) {
                NavigationBar(containerColor = Purple80,modifier = Modifier.height(75.dp)) {
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

                    AnimatedGradientFloatingActionButton(
                        selectedIcon = remember { mutableStateOf(Icons.Default.Add) },
                        onClickAction = {
                            selected.value = Icons.Default.Star;
                            Toast.makeText(context, "GO!", Toast.LENGTH_SHORT).show();
                            navigationController.navigate(Screens.StartAnExercise.screen) {
                                popUpTo(0);
                            }
                        }
                    )

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
                Home(navigationController)
                currentRoute = Screens.Home.screen
            }
            composable(Screens.Goals.screen) {
                Goals(navController = navigationController, exList = exList)
                sharedPrefManager.clearSelectedExercises()
                currentRoute = Screens.Goals.screen
            }
            composable(Screens.Profile.screen) {
                Profile(navigationController)
                currentRoute = Screens.Profile.screen
            }
            composable(Screens.StartAnExercise.screen) {
                StartAnExercise(navController = navigationController)
                currentRoute = Screens.StartAnExercise.screen
            }

            composable(Screens.SignInPage.screen) {
                SignUp(navigationController = navigationController)
                currentRoute = Screens.SignInPage.screen
            }
            composable(Screens.PostSignUp.screen){
                PostSignUp(navController = navigationController)
                currentRoute=Screens.PostSignUp.screen
            }
            composable(Screens.FitAi.screen){
                ChatScreen()
                currentRoute=Screens.FitAi.screen
            }
            composable(Screens.ApplicationInfo.screen){
                ApplicationInformationPage()
                currentRoute=Screens.ApplicationInfo.screen
            }
            composable(Screens.userInfoPage.screen){
                UserInformationPage()
                currentRoute=Screens.userInfoPage.screen
            }
            composable(Screens.updateUserMeasurements.screen){
                UpdateHeightWeightScreen(){

                }
                currentRoute=Screens.updateUserMeasurements.screen
            }

            composable(
                route = Screens.SubExerciseDetail.routeWithArgs,
                arguments = listOf(navArgument("exerciseID") { type = NavType.IntType })
            ) { backStackEntry ->
                val exerciseID = backStackEntry.arguments?.getInt("exerciseID") ?: 0
                Log.d("NavHost", "Navigating to SubExerciseDetail with exerciseID: $exerciseID")
                currentExerciseID = exerciseID
                SubExercisePage(navController = navigationController, exercise = getExerciseByID(exList, exerciseID))
                currentRoute = Screens.SubExerciseDetail(exerciseID).screen
            }

            composable(
                route = Screens.ExercisePage.routeWithArgs,
                arguments = listOf(navArgument("subExerciseID") { type = NavType.IntType })
            ) { backStackEntry ->
                val subExerciseID = backStackEntry.arguments?.getInt("subExerciseID") ?: 0
                val subEx= getExerciseByID(exList,currentExerciseID).getSubExerciseById(subExerciseID)
                ExercisePage1(
                    exerciseName = subEx!!.subExerciseName,
                    subExercise=subEx,
                    navController = navigationController
                )
            }

            composable(
                route = Screens.ExerciseInfoPage.routeWithArgs,
                arguments = listOf(navArgument("subExerciseID") { type = NavType.IntType })
            ) { backStackEntry ->

                val subExerciseID:Int=backStackEntry.arguments?.getInt("subExerciseID") ?: 0
                val subEx= getExerciseByID(exList,currentExerciseID).getSubExerciseById(subExerciseID) // çok saçma
                if (subEx != null) {
                    ExerciseInfoPage(subExercise = subEx, navController = navigationController)
                } else {
                    Toast.makeText(context,"Passed an invalid ID for subexercise!!!",Toast.LENGTH_SHORT).show()
                    Log.e("NavHost","Passed subexercise is invalid")
                }
            }

            composable(
                route = Screens.PostExercisePage.routeWithArgs,
                arguments = listOf(
                    navArgument("subExerciseID") { type = NavType.IntType ;Log.d("NavHost/PostExercisePage","Received subexerciseID")},
                    navArgument("caloriesBurned") { type = NavType.FloatType ;Log.d("NavHost/PostExercisePage","Received caloriesBurned")},
                    navArgument("minutes") { type = NavType.LongType ;Log.d("NavHost/PostExercisePage","Received minutes")},
                    navArgument("seconds") { type = NavType.LongType ;Log.d("NavHost/PostExercisePage","Received seconds")}
                )
            ) { backStackEntry ->
                val subExerciseID = backStackEntry.arguments?.getInt("subExerciseID") ?: 0
                Log.d("NavHost/PostExercisePage","Popped backstack subExerciseID = $subExerciseID")
                val caloriesBurned = backStackEntry.arguments?.getFloat("caloriesBurned") ?:0.0
                Log.d("NavHost/PostExercisePage","Popped backstack caloriesBurned =$caloriesBurned")
                val minutes = backStackEntry.arguments?.getLong("minutes")  ?:0L
                Log.d("NavHost/PostExercisePage","Popped backstack minutes = $minutes")
                val seconds = backStackEntry.arguments?.getLong("seconds") ?: 0L
                Log.d("NavHost/PostExercisePage","Popped backstack seconds = $seconds")

                PostExercisePage(
                    subExerciseID = subExerciseID,
                    caloriesBurned = caloriesBurned.toFloat(),
                    minutes = minutes,
                    seconds = seconds,
                    navController = navigationController
                )
            }
            composable(Screens.DietPage.screen){
                DietPage()
                currentRoute=Screens.DietPage.screen
            }






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
