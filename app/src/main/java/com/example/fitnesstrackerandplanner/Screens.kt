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
    data object DietPage : Screens("exerciseinfopage")
    data object updateUserMeasurements:Screens("updateusermeasurements")
    data object userInfoPage:Screens("userinfo")
    data object FitAi:Screens("fitai")
    data object ContactPage:Screens("contactpage")
    data object ApplicationInfo:Screens("applicationinfo")
       data class SubExerciseDetail(val exerciseID: Int) : Screens("subexercisedetail/$exerciseID"){
        companion object {
            const val routeWithArgs = "subexercisedetail/{exerciseID}"
        }
    }

    data class ExercisePage(val subExerciseID: Int) : Screens("exercisepage/$subExerciseID"){
        companion object {
            const val routeWithArgs = "exercisepage/{subExerciseID}" //HATA KAYNAĞI OLABİLİR
        }
    }
    data class ExerciseInfoPage(val subExerciseID: Int) : Screens("exerciseinfopage/${subExerciseID}") {
        companion object {
            const val routeWithArgs = "exerciseinfopage/{subExerciseID}"
        }
    }
    data class PostExercisePage(val subExerciseID: Int,val caloriesBurned:Double,val minutes:Long,val seconds:Long):Screens(
        "postexercisepage/${subExerciseID}/${caloriesBurned}/${minutes}/${seconds}"){
        companion object{
            const val routeWithArgs="postexercisepage/{subExerciseID}/{caloriesBurned}/{minutes}/{seconds}"
        }
    }

    }
