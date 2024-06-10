import androidx.compose.runtime.Composable

// Define Exercise data model
 open class Exercise(
    val name: String
) {
val subExercises:MutableList<Exercise>
init{
    subExercises=  mutableListOf()
}
fun addExercise(exList:List<Exercise>){
    subExercises.addAll(exList)
}




}
class SubExercise(val exerciseName:String,
                   val description: String,
                   val videoUrl: String,
                   groupName:String): Exercise(groupName) {




}

