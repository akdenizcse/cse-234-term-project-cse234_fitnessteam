package com.example.fitnesstrackerandplanner

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.fitnesstrackerandplanner.ui.theme.FitnessTrackerAndPlannerTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.example.fitnesstrackerandplanner.ui.theme.Pink80
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitnesstrackerandplanner.ui.theme.LimeGreen
import com.example.fitnesstrackerandplanner.ui.theme.Pink40
import com.example.fitnesstrackerandplanner.ui.theme.PurpleGrey40
import androidx.activity.result.contract.ActivityResultContracts
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.ExerciseSessionRecord
import androidx.health.connect.client.time.TimeRangeFilter
import java.time.Instant
import java.time.temporal.ChronoUnit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.content_main)
        installSplashScreen()
        val dbHelper by lazy{ DatabaseHelper(this)}
        val db by lazy { dbHelper.writableDatabase}

        setContent {
            val navigationController= rememberNavController()

            FitnessTrackerAndPlannerTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

                    BottomAppBar(db,navigationController,dbHelper)
                }
            }
        }
    }
}

@Composable
fun BottomAppBar(db: SQLiteDatabase,navigationController:NavHostController,dbHelper: DatabaseHelper){
    val context= LocalContext.current

    var currentRoute by remember{ mutableStateOf(Screens.LoginPage.screen)}
    val selected=remember{
        mutableStateOf(Icons.Default.Home)
    }
    Scaffold(

        bottomBar = {
            if(currentRoute!=Screens.SignInPage.screen && currentRoute!=Screens.LoginPage.screen) {
                NavigationBar(
                    // BottomAppBar()
                    containerColor = LimeGreen,
                ) {
                    IconButton(
                        onClick = {
                            selected.value = Icons.Default.Home
                            navigationController.navigate(Screens.Home.screen) {
                                popUpTo(0)
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()

                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center
                        ) {

                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = null,
                                modifier = Modifier.size(26.dp),
                                tint = if (selected.value == Icons.Default.Home) Color.White else Color.DarkGray
                            )
                            Text(
                                text = "Home",
                                modifier = Modifier.fillMaxWidth(),
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
                            .fillMaxSize()

                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {

                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                modifier = Modifier.size(26.dp),
                                tint = if (selected.value == Icons.Default.Star) Color.White else Color.DarkGray
                            )
                            Text("Goals")

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
                            .fillMaxSize()
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                modifier = Modifier.size(26.dp),
                                tint = if (selected.value == Icons.Default.Favorite) Color.White else Color.DarkGray
                            )
                            Text(text = "Activities", fontSize = 15.sp)
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
                            .fillMaxSize()

                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = null,
                                modifier = Modifier.size(26.dp),
                                tint = if (selected.value == Icons.Default.AccountCircle) Color.White else Color.DarkGray
                            )
                            Text(text = "Profile")

                        }

                    }


                }
            }
        }

    ) {paddingValues ->
        NavHost(navController=navigationController,
            startDestination=Screens.LoginPage.screen,
            modifier=Modifier.padding(paddingValues)){
            composable(Screens.LoginPage.screen){LoginPage(db=db,navigationController=navigationController);currentRoute=Screens.LoginPage.screen}
            composable(Screens.Activities.screen) { Activities(db);currentRoute=Screens.Activities.screen }
            composable(Screens.Home.screen) { Home(db);currentRoute=Screens.Home.screen  }
            composable(Screens.Goals.screen) { Goals(db,dbHelper);currentRoute=Screens.Goals.screen  }
            composable(Screens.Profile.screen) { Profile(db) ;currentRoute=Screens.Profile.screen }
            composable(Screens.StartAnExercise.screen) { initiateStartAnExercise(db, navController = navigationController) ;currentRoute=Screens.StartAnExercise.screen }
            composable(Screens.ExercisePage1.screen) { ExercisePage1(db);currentRoute=Screens.ExercisePage1.screen  }
            composable(Screens.ExercisePage2.screen) { ExercisePage2(db);currentRoute=Screens.ExercisePage2.screen  }
            composable(Screens.ExercisePage3.screen) { ExercisePage3(db);currentRoute=Screens.ExercisePage3.screen  }
            composable(Screens.ExercisePage4.screen) { ExercisePage4(db) ;currentRoute=Screens.ExercisePage4.screen }
            composable(Screens.SignInPage.screen){ SignUp(db = db, navigationController = navigationController,dbHelper);currentRoute=Screens.SignInPage.screen }
        }

    }
}


