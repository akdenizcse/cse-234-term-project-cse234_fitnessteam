import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseHelper {
    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("users")

    fun addUser(
        context: Context,
        firstName: String,
        lastName: String?,
        email: String,
        userName: String,
        password: String,
        callback:(Boolean)->Unit
    ) {


        checkForExistingUser(userName,email) { exists ->
            if (exists) {
                callback(false)
            } else {

                val user = hashMapOf(
                    "userId" to usersCollection.document().id, // Generate unique ID
                    "firstName" to firstName,
                    "lastName" to lastName,
                    "email" to email,
                    "userName" to userName,
                    "password" to password
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
    fun checkForExistingUser(userName: String, email: String, callback: (Boolean) -> Unit) {
        val usernameQuery = usersCollection.whereEqualTo("userName", userName).get()
        val emailQuery = usersCollection.whereEqualTo("email", email).get()

        // Wait for both queries to complete
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
        Tasks.whenAllComplete(usernameQuery, passwordQuery).addOnCompleteListener{ tasks ->
            val usernameExists = !usernameQuery.result.isEmpty
            val passwordExists = !passwordQuery.result.isEmpty
            callback(usernameExists && passwordExists)
        }.addOnFailureListener { e ->
            Log.e("FirebaseHelper", "Error authenticating user", e)
            callback(false)
        }
    }







}

