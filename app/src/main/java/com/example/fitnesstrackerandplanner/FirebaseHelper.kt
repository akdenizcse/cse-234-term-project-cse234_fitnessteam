import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.fitnesstrackerandplanner.toLocalDateTime
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Date

class FirebaseHelper {
    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("users")
    private val exerciseCollectionRef = db.collection("exercise")
    private val subExerciseCollectionRef = db.collection("subExercise")
    private val userDataCollection = db.collection("userData")
    private val userSleepCollection= db.collection("userSleepData")
    private val exerciseSessionsCollection = db.collection("exerciseSessions")
    private val userCalorieCollection=db.collection("userCalorieData")
    fun addUser(
        context: Context,
        firstName: String,
        lastName: String?,
        email: String,
        userName: String,
        password: String,
        age: Int,
        gender: Boolean,
        callback: (Boolean) -> Unit
    ) {


        checkForExistingUser(userName, email) { exists ->
            if (exists) {
                callback(false)
            } else {

                val user = hashMapOf(
                    "userId" to usersCollection.document().id,
                    "firstName" to firstName,
                    "lastName" to lastName,
                    "email" to email,
                    "userName" to userName,
                    "password" to password,
                    "age" to age,
                    "gender" to gender
                )

                usersCollection.add(user)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(context, "User added successfully!", Toast.LENGTH_SHORT)
                            .show()
                        callback(true)
                    }
                    .addOnFailureListener { e ->
                        Log.w("FirebaseHelper", "Error adding user: ", e)
                        Toast.makeText(context, "Failed to add user !", Toast.LENGTH_SHORT).show()
                        callback(false)
                    }
            }
        }

    }

    fun setHeightWeight(
        userName: String,
        height: Int,
        weight: Int,
        callback: (Boolean) -> Unit
    ) {
        usersCollection.whereEqualTo("userName", userName).get()
            .addOnSuccessListener { documents ->
                if (documents.size() == 1) {
                    val document = documents.documents[0]
                    document.reference.update(
                        mapOf(
                            "height" to height,
                            "weight" to weight
                        )
                    )
                        .addOnSuccessListener {
                            Log.d("FirebaseHelper", "Height and weight updated successfully")
                            callback(true)
                        }
                        .addOnFailureListener { e ->
                            Log.w("FirebaseHelper", "Error updating height and weight: ", e)
                            callback(false)
                        }
                } else {
                    Log.w("FirebaseHelper", "User not found or multiple users found")
                    callback(false)
                }
            }
            .addOnFailureListener { e ->
                Log.w("FirebaseHelper", "Error finding user: ", e)
                callback(false)
            }
    }

    fun checkForExistingUser(userName: String, email: String, callback: (Boolean) -> Unit) {
        val usernameQuery = usersCollection.whereEqualTo("userName", userName).get()
        val emailQuery = usersCollection.whereEqualTo("email", email).get()

        Tasks.whenAllComplete(usernameQuery, emailQuery).addOnCompleteListener { tasks ->
            val usernameExists = usernameQuery.result?.isEmpty == false
            val emailExists = emailQuery.result?.isEmpty == false
            callback(usernameExists || emailExists)
        }.addOnFailureListener { e ->
            Log.w("FirebaseHelper", "Error checking user: ", e)
            callback(false) // or handle the error as needed
        }
    }


    fun loginAuth(username: String, password: String, callback: (Boolean) -> Unit) {
        val usernameQuery = usersCollection.whereEqualTo("userName", username).get()
        val passwordQuery = usersCollection.whereEqualTo("password", password).get()

        // Using whenAllComplete() to wait for both queries to complete
        Tasks.whenAllComplete(usernameQuery, passwordQuery).addOnCompleteListener { tasks ->
            val usernameExists = !usernameQuery.result.isEmpty
            val passwordExists = !passwordQuery.result.isEmpty
            callback(usernameExists && passwordExists)
        }.addOnFailureListener { e ->
            Log.e("FirebaseHelper", "Error authenticating user", e)
            callback(false)
        }
    }

    fun fetchAge(userName: String, callback: (Int?) -> Unit) {
        Log.d("FirebaseHelper", "Fetching age for user: $userName")
        val usernameQuery = usersCollection.whereEqualTo("userName", userName).get()
        usernameQuery.addOnSuccessListener { documents ->
            if (!documents.isEmpty) {
                val document = documents.documents[0]
                val age = document.getLong("age")?.toInt()
                Log.d("FirebaseHelper", "Fetched age for user $userName: $age")
                callback(age)
            } else {
                Log.d("FirebaseHelper", "User $userName not found")
                callback(null)
            }
        }.addOnFailureListener { e ->
            Log.w("FirebaseHelper", "Error fetching age: ", e)
            callback(null)
        }

    }

    fun fetchHeight(userName: String, callback: (Int?) -> Unit) {
        Log.d("FirebaseHelper", "Fetching height for user: $userName")
        val usernameQuery = usersCollection.whereEqualTo("userName", userName).get()
        usernameQuery.addOnSuccessListener { documents ->
            if (!documents.isEmpty) {
                val document = documents.documents[0]
                val height = document.getLong("height")?.toInt()
                Log.d("FirebaseHelper", "Fetched height for user $userName: $height")
                callback(height)
            } else {
                Log.d("FirebaseHelper", "User $userName not found")
                callback(null)
            }
        }.addOnFailureListener { e ->
            Log.w("FirebaseHelper", "Error fetching height: ", e)
            callback(null)
        }
    }

    fun fetchGender(userName: String, callback: (Boolean?) -> Unit) {
        Log.d("FirebaseHelper", "Fetching gender for user: $userName")
        val usernameQuery = usersCollection.whereEqualTo("userName", userName).get()
        usernameQuery.addOnSuccessListener { documents ->
            if (!documents.isEmpty) {
                val document = documents.documents[0]
                val gender = document.getBoolean("gender")
                Log.d("FirebaseHelper", "Fetched gender for user $userName: $gender")
                callback(gender)
            } else {
                Log.d("FirebaseHelper", "User $userName not found")
                callback(null)
            }
        }.addOnFailureListener { e ->
            Log.w("FirebaseHelper", "Error fetching gender: ", e)
            callback(null)
        }
    }


    fun fetchWeight(userName: String, callback: (Int?) -> Unit) {
        val usernameQuery = usersCollection.whereEqualTo("userName", userName).get()
        usernameQuery.addOnSuccessListener { documents ->
            if (!documents.isEmpty) {
                val document = documents.documents[0]
                val weight = document.getLong("weight")?.toInt()
                callback(weight)
            } else {
                callback(null)
            }
        }.addOnFailureListener { e ->
            Log.w("FirebaseHelper", "Error fetching weight: ", e)
            callback(null)
        }
    }


//****************------------------------*-*--*-*-*-*-*-*-*-*-*------------------*********************************//


    suspend fun initializeExercises(): List<Exercise> {
        return withContext(Dispatchers.IO) {
            try {
                // Fetch exercises
                val exerciseQuerySnapshot = exerciseCollectionRef.get().await()
                val exercises = mutableListOf<Exercise>()

                for (exerciseDocument in exerciseQuerySnapshot.documents) {
                    val exerciseID = exerciseDocument.getLong("exerciseID")?.toInt() ?: 0
                    val exerciseName = exerciseDocument.getString("exerciseName") ?: ""
                    val exercise = Exercise(exerciseName, exerciseID)

                    // Fetch sub-exercises for the current exercise
                    val subExerciseQuerySnapshot = subExerciseCollectionRef
                        .whereEqualTo("parentExerciseID", exercise.exerciseID)
                        .get()
                        .await()

                    val subExercises = mutableListOf<SubExercise>()
                    for (subDocument in subExerciseQuerySnapshot.documents) {
                        val subExerciseID = subDocument.getLong("subExerciseID")?.toInt() ?: 0
                        val subExerciseName = subDocument.getString("subExerciseName") ?: ""
                        val videoUrl = subDocument.getString("youtubeID") ?: ""
                        val approximateCaloriesPerSecond =
                            subDocument.getDouble("subExerciseCaloriesBurnedPerSec") ?: 0.0

                        val subExercise = SubExercise(
                            subExerciseName = subExerciseName,
                            description = "",  // You can fetch description if added to Firestore
                            videoUrl = videoUrl,
                            groupName = exerciseName,
                            exerciseIDs = exerciseID,
                            subExerciseID = subExerciseID,
                            approximateCaloriesPerSecond = approximateCaloriesPerSecond
                        )
                        subExercises.add(subExercise)
                    }

                    exercise.addExercise(subExercises)
                    exercises.add(exercise)
                }

                exercises
            } catch (e: Exception) {
                Log.e("FirebaseHelper", "Error fetching exercises", e)
                emptyList() // Return empty list on error
            }
        }
    }

    fun updateWaterConsumption(username: String, waterDrank: Float, callback: (Boolean) -> Unit) {
        val currentTime = System.currentTimeMillis()
        val data = hashMapOf(
            "userName" to username,
            "waterDrank" to waterDrank,
            "timestamp" to currentTime
        )
        val documentId = "$username-${currentTime / 86400000}"
        userDataCollection.document(documentId)
            .set(data)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener { e ->
                Log.w("FirebaseHelper", "Error updating water consumption: ", e)
                callback(false)
            }
    }

    fun deleteOldWaterData(username: String, callback: (Boolean) -> Unit) {
        val currentTime = System.currentTimeMillis()
        val oneDayAgo = currentTime - 86400000

        userDataCollection
            .whereEqualTo("userName", username)
            .whereLessThan("timestamp", oneDayAgo)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    userDataCollection.document(document.id).delete()
                }
                callback(true)
            }
            .addOnFailureListener { e ->
                Log.w("FirebaseHelper", "Error deleting old water data: ", e)
                callback(false)
            }
    }

    suspend fun fetchWaterConsumption(userName: String): Float {
        return withContext(Dispatchers.IO) {
            try {
                val currentTime = System.currentTimeMillis()
                val oneDayAgo = currentTime - 86400000  // 86400000 ms in a day

                val querySnapshot = userDataCollection
                    .whereEqualTo("userName", userName)
                    .whereGreaterThan("timestamp", oneDayAgo)
                    .get()
                    .await()

                var totalWaterConsumed = 0.0f
                for (document in querySnapshot.documents) {
                    val waterDrank = document.getDouble("waterDrank")?.toFloat() ?: 0.0f
                    Log.d("FirebaseHelperWater", "Fetched data $waterDrank")
                    totalWaterConsumed += waterDrank
                }

                totalWaterConsumed
            } catch (e: Exception) {
                Log.e("FirebaseHelper", "Error fetching water consumption", e)
                0.0f  // Return 0.0f on error
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun logExerciseSession(
        userName: String,
        subExerciseID: Int,
        caloriesBurned: Double,
        exerciseTime: Long,
        fittyHealthPointsGained: Int,
        startTime: ZonedDateTime,
        endTime: ZonedDateTime,
        callback: (Boolean) -> Unit
    ) {
        val exerciseSession = hashMapOf(
            "userName" to userName,
            "subExerciseID" to subExerciseID,
            "caloriesBurned" to caloriesBurned,
            "exerciseTime" to exerciseTime,
            "fittyHealthPointsGained" to fittyHealthPointsGained,
            "startTime" to Timestamp(startTime.toInstant().toEpochMilli() / 1000, 0),
            "endTime" to Timestamp(endTime.toInstant().toEpochMilli() / 1000, 0)
        )

        exerciseSessionsCollection
            .add(exerciseSession)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }

    suspend fun fetchExerciseSessions(userName: String): List<ExerciseSession> {
        val exerciseSessionsCollection =
            FirebaseFirestore.getInstance().collection("exerciseSessions")

        return withContext(Dispatchers.IO) {
            try {
                val querySnapshot = exerciseSessionsCollection
                    .whereEqualTo("userName", userName)
                    .get()
                    .await()

                querySnapshot.documents.map { document ->
                    ExerciseSession(
                        userName = document.getString("userName") ?: "",
                        subExerciseID = document.getLong("subExerciseID")?.toInt() ?: 0,
                        caloriesBurned = document.getDouble("caloriesBurned") ?: 0.0,
                        exerciseTime = document.getLong("exerciseTime") ?: 0L,
                        fittyHealthPointsGained = document.getLong("fittyHealthPointsGained")
                            ?.toInt() ?: 0,
                        startTime = document.getTimestamp("startTime")?.toDate() ?: Date(),
                        endTime = document.getTimestamp("endTime")?.toDate() ?: Date()
                    )
                }
            } catch (e: Exception) {
                Log.e("Firestore", "Error fetching exercise sessions: ", e)
                emptyList()
            }
        }
    }


    fun fetchDailyBurnedCalories(userName: String, callback: (Double) -> Unit) {
        val exerciseSessionsCollection = FirebaseFirestore.getInstance().collection("exerciseSessions")

        exerciseSessionsCollection
            .whereEqualTo("userName", userName)
            .get()
            .addOnSuccessListener { querySnapshot ->
                var totalCalories = 0.0
                val now = LocalDateTime.now()
                val sessions = querySnapshot.documents.mapNotNull { document ->
                    ExerciseSession(
                        userName = document.getString("userName") ?: "",
                        subExerciseID = document.getLong("subExerciseID")?.toInt() ?: 0,
                        caloriesBurned = document.getDouble("caloriesBurned") ?: 0.0,
                        exerciseTime = document.getLong("exerciseTime") ?: 0L,
                        fittyHealthPointsGained = document.getLong("fittyHealthPointsGained")?.toInt() ?: 0,
                        startTime = document.getTimestamp("startTime")?.toDate() ?: Date(),
                        endTime = document.getTimestamp("endTime")?.toDate() ?: Date()
                    )
                }

                val filteredSessions = sessions.filter { session ->
                    val sessionTime = session.startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                    sessionTime.isAfter(now.minusDays(1))
                }

                for (session in filteredSessions) {
                    totalCalories += session.caloriesBurned
                }

                callback(totalCalories)
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error fetching exercise sessions: ", exception)
                callback(0.0)
            }
    }
    fun updateSleepData(username: String, hoursSlept: Float, callback: (Boolean) -> Unit) {
        val currentTime = Timestamp.now()
        val data = hashMapOf(
            "userName" to username,
            "hoursSlept" to hoursSlept,
            "timestamp" to currentTime
        )
        val documentId = "$username-${currentTime.seconds / 86400}" // Dividing by 86400 to get the day count
        userSleepCollection.document(documentId)
            .set(data)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener { e ->
                Log.w("FirebaseHelper", "Error updating sleep data: ", e)
                callback(false)
            }
    }




    fun fetchSleepData(userName: String, callback: (Float) -> Unit) {
        val oneDayAgo = Timestamp(Date(System.currentTimeMillis() - 86400000)) // 86400000 ms in a day

        userSleepCollection
            .whereEqualTo("userName", userName)
            .whereGreaterThan("timestamp", oneDayAgo)
            .get()
            .addOnSuccessListener { querySnapshot ->
                var totalSleepHours = 0.0f
                for (document in querySnapshot.documents) {
                    val hoursSlept = document.getDouble("hoursSlept")?.toFloat() ?: 0.0f
                    Log.d("FirebaseHelperSleep", "Fetched data $hoursSlept")
                    totalSleepHours += hoursSlept
                }
                callback(totalSleepHours)
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseHelper", "Error fetching sleep data", e)
                callback(0.0f) // Return 0.0f on error
            }
    }

    fun deleteOldSleepData(username: String, callback: (Boolean) -> Unit) {
        val oneDayAgo = Timestamp(Date(System.currentTimeMillis() - 86400000)) // 86400000 ms in a day

        userSleepCollection
            .whereEqualTo("userName", username)
            .whereLessThan("timestamp", oneDayAgo)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    userSleepCollection.document(document.id).delete()
                }
                callback(true)
            }
            .addOnFailureListener { e ->
                Log.w("FirebaseHelper", "Error deleting old sleep data: ", e)
                callback(false)
            }
    }
    fun updateCalorieData(username: String, calorieTaken: Number, callback: (Boolean) -> Unit) {
        val currentTime = Timestamp.now()
        val data = hashMapOf(
            "userName" to username,
            "caloriesTaken" to calorieTaken,
            "timestamp" to currentTime
        )
        val documentId = "$username-${currentTime.seconds / 86400}" // Dividing by 86400 to get the day count
        userCalorieCollection.document(documentId)
            .set(data)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener { e ->
                Log.w("FirebaseHelper", "Error updating calorie data: ", e)
                callback(false)
            }
    }
    fun fetchCalorieData(userName: String, callback: (Float) -> Unit) {
        val oneDayAgo = Timestamp(Date(System.currentTimeMillis() - 86400000)) // 86400000 ms in a day

        userCalorieCollection
            .whereEqualTo("userName", userName)
            .whereGreaterThan("timestamp", oneDayAgo)
            .get()
            .addOnSuccessListener { querySnapshot ->
                var totalCalories = 0.0f
                for (document in querySnapshot.documents) {
                    val calorieTaken = document.getDouble("caloriesTaken")?.toFloat() ?: 0.0f
                    Log.d("FirebaseHelperCalorie", "Fetched data $calorieTaken")
                    totalCalories += calorieTaken
                }
                callback(totalCalories)
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseHelper", "Error fetching calorie data", e)
                callback(0.0f) // Return 0.0f on error
            }
    }
    fun deleteOldCalorieData(username: String, callback: (Boolean) -> Unit) {
        val oneDayAgo = Timestamp(Date(System.currentTimeMillis() - 86400000)) // 86400000 ms in a day

        userCalorieCollection
            .whereEqualTo("userName", username)
            .whereLessThan("timestamp", oneDayAgo)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    userCalorieCollection.document(document.id).delete()
                }
                callback(true)
            }
            .addOnFailureListener { e ->
                Log.w("FirebaseHelper", "Error deleting old calorie data: ", e)
                callback(false)
            }
    }



}
data class ExerciseSession(
    val userName: String,
    val subExerciseID: Int,
    val caloriesBurned: Double,
    val exerciseTime: Long,
    val fittyHealthPointsGained: Int,
    val startTime: Date,
    val endTime: Date
)


