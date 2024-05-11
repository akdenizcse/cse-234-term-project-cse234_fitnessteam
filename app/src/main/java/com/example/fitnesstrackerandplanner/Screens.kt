package com.example.fitnesstrackerandplanner




//This the class to keep track of every screen that is implemented in our app.
sealed class Screens(val screen:String) {

    data object Home:Screens("home")
    data object Activites:Screens("activities")
    data object Goals:Screens("goals")
    data object Profile:Screens("profile")
    data object StartAnExercise:Screens("startanexercise")
    data object LoginPage:Screens("loginpage")

}