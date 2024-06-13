import android.os.Parcel
import android.os.Parcelable
import androidx.compose.runtime.Composable
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize


// Define Exercise data model
 open class Exercise(
    val name: String,
    open val exerciseID:Int
) {
val subExercises:MutableList<SubExercise>
init{
    subExercises=  mutableListOf()
}
fun addExercise(exList:List<SubExercise>){
    subExercises.addAll(exList)
}fun getSubExerciseById(subExerciseID: Int): SubExercise? {
        return subExercises.find { it.subExerciseID == subExerciseID } //crash sebebi buymuş it.exerciseID==subExerciseID'ydi önceden.
    }




}
class SubExercise(val exerciseName:String,
                  val description: String,
                  val videoUrl: String,
                  var groupName:String, override val exerciseID: Int,
    var subExerciseID:Int= subExerciseIDs
): Exercise(groupName, exerciseID = exerciseID) {

    companion object{
        var subExerciseIDs:Int=0
    }
    init{
        this.subExerciseID= subExerciseIDs
        subExerciseIDs++
    }




}

