import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.fitnesstrackerandplanner.R
import java.util.concurrent.TimeUnit

object NotificationHelper {

    private const val CHANNEL_ID = "default_channel"
    private const val CHANNEL_NAME = "Default Channel"
    const val NOTIFICATION_ID_WATER = 1
    const val NOTIFICATION_ID_CALORIES_BURNED = 2
    const val NOTIFICATION_ID_SLEEP = 3
    const val NOTIFICATION_ID_CALORIES_CONSUMED = 4

    private const val PREF_NAME = "NotificationPrefs"
    private const val KEY_LAST_NOTIFIED_WATER = "last_notified_water"
    private const val KEY_LAST_NOTIFIED_CALORIES_BURNED = "last_notified_calories_burned"
    private const val KEY_LAST_NOTIFIED_SLEEP = "last_notified_sleep"
    private const val KEY_LAST_NOTIFIED_CALORIES_CONSUMED = "last_notified_calories_consumed"

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Health reminders"
                enableVibration(true)
            }

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun scheduleNotification(context: Context, message: String, notificationId: Int) {
        createNotificationChannel(context)

        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        intent?.let {
            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
            )

            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Health Reminder")
                .setContentText(message)
                .setSmallIcon(R.drawable.dumbell_splash)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build()

            val notificationManager = NotificationManagerCompat.from(context)
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notificationManager.notify(notificationId, notification)

            // Update last notified time in SharedPreferences
            updateLastNotifiedTime(context, notificationId)
        }
    }

    private fun updateLastNotifiedTime(context: Context, notificationId: Int) {
        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = pref.edit()
        val currentTime = System.currentTimeMillis()
        when (notificationId) {
            NOTIFICATION_ID_WATER -> editor.putLong(KEY_LAST_NOTIFIED_WATER, currentTime)
            NOTIFICATION_ID_CALORIES_BURNED -> editor.putLong(KEY_LAST_NOTIFIED_CALORIES_BURNED, currentTime)
            NOTIFICATION_ID_SLEEP -> editor.putLong(KEY_LAST_NOTIFIED_SLEEP, currentTime)
            NOTIFICATION_ID_CALORIES_CONSUMED -> editor.putLong(KEY_LAST_NOTIFIED_CALORIES_CONSUMED, currentTime)
        }
        editor.apply()
    }

    private fun getLastNotifiedTime(context: Context, notificationId: Int): Long {
        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return when (notificationId) {
            NOTIFICATION_ID_WATER -> pref.getLong(KEY_LAST_NOTIFIED_WATER, 0)
            NOTIFICATION_ID_CALORIES_BURNED -> pref.getLong(KEY_LAST_NOTIFIED_CALORIES_BURNED, 0)
            NOTIFICATION_ID_SLEEP -> pref.getLong(KEY_LAST_NOTIFIED_SLEEP, 0)
            NOTIFICATION_ID_CALORIES_CONSUMED -> pref.getLong(KEY_LAST_NOTIFIED_CALORIES_CONSUMED, 0)
            else -> 0
        }
    }

    fun shouldNotify(context: Context, notificationId: Int): Boolean {
        val lastNotifiedTime = getLastNotifiedTime(context, notificationId)
        val currentTime = System.currentTimeMillis()
        val elapsedHours = TimeUnit.MILLISECONDS.toHours(currentTime - lastNotifiedTime)
        return elapsedHours >= 6
    }

}
