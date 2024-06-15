import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseHelper {
    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("users")
    private val exerciseCollectionRef = db.collection("exercise")
    private val subExerciseCollectionRef = db.collection("subExercise")
    private val waterConsumptionCollection = db.collection("userData")

    fun addUser(
        context: Context,
        firstName: String,
        lastName: String?,
        email: String,
        userName: String,
        password: String,
        callback: (Boolean) -> Unit
    ) {


        checkForExistingUser(userName, email) { exists ->
            if (exists) {
                callback(false)
            } else {

                val user = hashMapOf(
                    "userId" to usersCollection.document().id, // Generate unique ID
                    "firstName" to firstName,
                    "lastName" to lastName,
                    "email" to email,
                    "userName" to userName,
                    "password" to password,
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
        // Query the collection to find the document with the specified username
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


// Assuming you have Firebase dependencies set up


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

                exercises // Return fetched exercises
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
        val documentId = "$username-${currentTime / 86400000}"  // 86400000 ms in a day
        waterConsumptionCollection.document(documentId)
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

        waterConsumptionCollection
            .whereEqualTo("userName", username)
            .whereLessThan("timestamp", oneDayAgo)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    waterConsumptionCollection.document(document.id).delete()
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

                // Query water consumption data for the current day
                val querySnapshot = waterConsumptionCollection
                    .whereEqualTo("userName", userName)
                    .whereGreaterThan("timestamp", oneDayAgo)
                    .get()
                    .await()

                // Calculate total water consumed for the current day
                var totalWaterConsumed = 0.0f
                for (document in querySnapshot.documents) {
                    val waterDrank = document.getDouble("waterDrank")?.toFloat() ?: 0.0f
                    totalWaterConsumed += waterDrank
                }

                totalWaterConsumed
            } catch (e: Exception) {
                Log.e("FirebaseHelper", "Error fetching water consumption", e)
                0.0f  // Return 0.0f on error
            }
        }
    }

}


