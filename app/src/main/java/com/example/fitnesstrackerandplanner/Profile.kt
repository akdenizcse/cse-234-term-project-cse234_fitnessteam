package com.example.fitnesstrackerandplanner
import android.database.sqlite.SQLiteDatabase
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.fitnesstrackerandplanner.ui.theme.*
import androidx.compose.material3.Surface
import androidx.compose.ui.unit.dp

@Composable
fun Profile(db: SQLiteDatabase?, dbHelper: DatabaseHelper?) {

    /*Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceGreen)
        ) {

        Column (modifier=Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally


        ){


        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .height(280.dp)
                .fillMaxWidth()
                .padding(25.dp),

            colors = CardDefaults.cardColors(
                containerColor = PastelPink
            )

        ) {
            Column(
                modifier=Modifier.fillMaxWidth(),


                ) {
                Row(modifier=Modifier.weight(2f)) {
                    Text(
                        text = "İrem KARAKAPLAN",
                        modifier = Modifier
                            .padding(top = 20.dp, start = 10.dp)
                            .weight(0.2f),
                        textAlign = TextAlign.Start,
                    )

                    Column(modifier=Modifier.padding(end=15.dp)) {
                        Row(modifier=Modifier) {
                            val img=painterResource(R.drawable.scale_logo)
                            Image(painter=img,contentDescription=null,modifier=Modifier.padding(15.dp).size(23.dp,23.dp).fillMaxSize())
                            Text(
                                text = "Weight:${50} kg",
                                modifier = Modifier.padding(top = 15.dp)
                            )
                        }
                        Row(modifier=Modifier) {
                            val img=painterResource(R.drawable.height_logo)
                            Image(painter=img,contentDescription=null,modifier=Modifier.padding(15.dp).size(30.dp,25.dp).fillMaxSize())

                            Text(
                                text = "Height:${167}",
                                modifier = Modifier.padding(top = 15.dp)
                            )
                        }
                    }

                }
                Text(
                    text = "Age:20",
                    modifier = Modifier
                        .padding(start = 10.dp,)
                        .weight(1f),
                    textAlign = TextAlign.Start,
                )
                Text(
                    text="BMI: ${utilityMethods.calculateBMI(52,166)}",
                    modifier = Modifier
                        .padding(start = 10.dp,)

                        .weight(1f),
                    textAlign = TextAlign.Start,
                )



            }
        }

    }}

     */

    val options=listOf("User Information",
        "Change user measurements",
        "Accessibility",
        "Notifications",
        "Application Info",
        "Log Out")

    Surface(modifier=Modifier.fillMaxSize(),color= SurfaceGreen){

            RecyclerView(names = options, greetingMessage ="Fitness and Health Tracker\nver${dbHelper?.getDatabaseVer()}", icon = null, subTitle = null,
                shape= RoundedCornerShape(20.dp)
            )

    }

}

@Preview
@Composable
fun ProfilePreview() {
    Profile(null,null)

}