import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize

@Parcelize
open class Exercise(
    val exerciseName: String,
    @Expose(serialize = false, deserialize = false)
    open var exerciseID: Int
) : Parcelable {
    val subExercises: MutableList<SubExercise> = mutableListOf()

    constructor() : this("", 0)

    fun addExercise(exList: List<SubExercise>) {
        subExercises.addAll(exList)
    }

    fun getSubExerciseById(subExerciseID: Int): SubExercise? {
        return subExercises.find { it.subExerciseID == subExerciseID }
    }
}

@Parcelize
class SubExercise(
    var subExerciseName: String,
    var description: String,
    var videoUrl: String,
    var groupName: String,
    val exerciseIDs: Int,
    var subExerciseID: Int = subExerciseIDs,
    var approximateCaloriesPerSecond: Double
) : Exercise(groupName, exerciseID = exerciseIDs), Parcelable {
    companion object {
        var subExerciseIDs: Int = 0
    }

    constructor() : this("", "", "", "", 0, 0, 0.0)

    init {
        this.subExerciseID = subExerciseIDs
        subExerciseIDs++
    }
}
