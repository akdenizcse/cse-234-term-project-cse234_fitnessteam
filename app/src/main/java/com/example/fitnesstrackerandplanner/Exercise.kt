import androidx.compose.runtime.Composable

// Define Exercise data model
 open class Exercise(
    val name: String,
     val exerciseID:Int
) {
val subExercises:MutableList<SubExercise>
init{
    subExercises=  mutableListOf()
}
fun addExercise(exList:List<SubExercise>){
    subExercises.addAll(exList)
}




}
class SubExercise(val exerciseName:String,
                   val description: String,
                   val videoUrl: String,
                   groupName:String,exerciseID: Int): Exercise(groupName, exerciseID = exerciseID) {




}

