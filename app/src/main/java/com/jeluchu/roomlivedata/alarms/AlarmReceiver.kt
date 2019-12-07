package com.example.alarms

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.jeluchu.roomlivedata.R
import com.jeluchu.roomlivedata.activities.AddNewReminderActivity
import com.jeluchu.roomlivedata.activities.HomeScreenActivity
import com.jeluchu.roomlivedata.alarms.StringSplit
import com.jeluchu.roomlivedata.utils.Constants
import java.util.*

/**
 * Created by ptyagi on 4/17/17.
 */

/**
 * AlarmReceiver handles the broadcast message and4 generates Notification
 */
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_WEEK)

        if (StringSplit.getIntAt0(Utils.millitohourmin(calendar.timeInMillis).trim()) < StringSplit.getIntAt0(intent.getStringExtra("date")
            )
        ) {
            if (StringSplit.getIntAt1(Utils.millitohourmin(calendar.timeInMillis).trim()) < StringSplit.getIntAt1(
                    intent.getStringExtra("date")
                )
            ) {


                when (day) {
                    Calendar.SATURDAY -> {

                        if (intent!!.getBooleanExtra("saturday", false)) {
                            showNotificationonDay(intent, context)

                        }
                    }

                    Calendar.SUNDAY -> {
                        if (intent!!.getBooleanExtra("sunday", false)) {
                            //  sendNotification2(context);
                            showNotificationonDay(intent, context)

                        }
                    }
                    Calendar.MONDAY -> {
                        if (intent!!.getBooleanExtra("monday", false)) {

                            showNotificationonDay(intent, context)
                        }
                    }
                    Calendar.TUESDAY -> {
                        if (intent!!.getBooleanExtra("tuesday", false)) {

                            showNotificationonDay(intent, context)
                        }
                    }
                    Calendar.WEDNESDAY -> {
                        if (intent!!.getBooleanExtra("wednesday", false)) {

                            showNotificationonDay(intent, context)
                        }
                    }
                    Calendar.THURSDAY -> {
                        if (intent!!.getBooleanExtra("thursday", false)) {

                            showNotificationonDay(intent, context)
                        }
                    }
                    Calendar.FRIDAY -> {
                        if (intent!!.getBooleanExtra("friday", false)) {

                            showNotificationonDay(intent, context)
                        }
                    }


                }
            }
        }
    }

    fun showNotificationonDay(intent: Intent, context: Context) {

        Log.e("MainActivity", "AlarmDone")

        //Intent to invoke app when click on notification.
        //In this sample, we want to start/launch this sample app when user clicks on notification
        val intentToRepeat = Intent(context, AddNewReminderActivity::class.java)
        intentToRepeat.putExtra(Constants.AlarmType, intent.getStringExtra(Constants.AlarmType))
        //set flag to restart/relaunch the app
        intentToRepeat.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        //Pending intent to handle launch of Activity in intent above
        val pendingIntent = PendingIntent.getActivity(
            context,
            intent!!.getIntExtra("id", 0),
            intentToRepeat,
            PendingIntent.FLAG_UPDATE_CURRENT
        )


        //Build notification
        //   val repeatedNotification = buildLocalNotification(context, pendingIntent).build()

        //Send local notification
        // NotificationHelper.getNotificationManager(context).notify(NotificationHelper.ALARM_TYPE_RTC, repeatedNotification)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = "Alarm"
            val descriptionText = "Alarm details"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel("AlarmId", name, importance)
            mChannel.description = descriptionText
            val notificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        // Create the notification to be shown
        val mBuilder = NotificationCompat.Builder(context!!, "AlarmId")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Alarm")
            .setContentText(intent.getStringExtra("date"))
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        if (intent!!.getBooleanExtra("vibration", false))
            mBuilder.setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
        if (intent!!.getBooleanExtra("sound", false))
            mBuilder.setSound(defaultSoundUri)


        // Get the Notification manager service
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Generate an Id for each notification
        //   val id = System.currentTimeMillis() / 1000

        // Show a notification
        notificationManager.notify(intent!!.getIntExtra("id", 0), mBuilder.build())

    }
}



