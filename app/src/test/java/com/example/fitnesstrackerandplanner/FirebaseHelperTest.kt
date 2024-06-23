import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FirebaseHelperTest {

   // Mock FirebaseFirestore and CollectionReferences
   @Mock
   private lateinit var mockFirestore: FirebaseFirestore

   @Mock
   private lateinit var mockUsersCollection: CollectionReference

   @Mock
   private lateinit var mockDocumentReference: DocumentReference

   // Mock Context (for toast)
   @Mock
   private lateinit var mockContext: Context

   // Class under test
   private lateinit var firebaseHelper: FirebaseHelper

   @Before
   fun setup() {
      MockitoAnnotations.openMocks(this)

      // Mock behavior of Firebase Firestore methods
      `when`(mockFirestore.collection("users")).thenReturn(mockUsersCollection)
      `when`(mockUsersCollection.add(Mockito.mock(Map::class.java)))
         .thenReturn(Tasks.forResult(mockDocumentReference))

      // Initialize FirebaseHelper with mocked dependencies
      firebaseHelper = mock(FirebaseHelper::class.java)
   }

   @Test
   fun testAddUser_Success() {
      val userName = "melih"
      val callback: (Boolean) -> Unit = { success ->
         assert(success) { "Expected user addition to succeed" }
      }

      // Call the method under test
      firebaseHelper.addUser(
         context = mockContext,
         firstName = "melih",
         lastName = "oz",
         email = "melihoz@example.com",
         userName = userName,
         password = "melihoz1212",
         age = 30,
         gender = true,
         callback = callback
      )
   }
}
