package com.example.fitnesstrackerandplanner

import SubExercise


//This the class to keep track of every screen that is implemented in our app.
sealed class Screens(val screen:String) {

    data object Home:Screens("home")
    data object Activities:Screens("activities")
    data object Goals:Screens("goals")
    data object Profile:Screens("profile")
    data object StartAnExercise:Screens("startanexercise")
    data object SignInPage:Screens("signin")
    data object LoginPage:Screens("loginpage") //
    data object ExercisePage1:Screens("exercisepage1")
    data object ExercisePage2:Screens("exercisepage2")
    data object ExercisePage3:Screens("exercisepage3")
    data object ExercisePage4:Screens("exercisepage4")
    data object PostSignUp:Screens("postsignuppage")

    data class SubExerciseDetail(val exerciseID: Int) : Screens("subexercisedetail/$exerciseID"){
        companion object {
            const val routeWithArgs = "subexercisedetail/{exerciseID}"
        }
    }

    data class ExercisePage(val exerciseName:String) : Screens("exercisepage/$exerciseName"){
        companion object {
            const val routeWithArgs = "exercisepage/{exerciseName}" //HATA KAYNAĞI OLABİLİR
        }
    }
    data class ExerciseInfoPage(val subExerciseID: Int) : Screens("exerciseinfopage/${subExerciseID}") {
        companion object {
            const val routeWithArgs = "exerciseinfopage/{subExerciseID}"
        }
    }
    }
