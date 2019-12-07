package com.example.alarms

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.provider.SyncStateContract
import com.jeluchu.roomlivedata.model.Notification
import com.jeluchu.roomlivedata.utils.Constants
import java.util.*

object Utils {

    private var alarmManagerRTC: AlarmManager? = null
    private var alarmIntentRTC: PendingIntent? = null

    fun millitohourmin(timeOfAlarm: Long): String {

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeOfAlarm


        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minutes = calendar.get(Calendar.MINUTE)
        return (hour.toString() + " : " + minutes.toString())
    }

    fun setAlarmNew(mainActivity: Context, notification: Notification, i: Int) {


        //Setting intent to class where Alarm broadcast message will be handled

        val broadcastIntent = Intent(mainActivity, AlarmReceiver::class.java)
        broadcastIntent.putExtra("id", i)
        broadcastIntent.putExtra(Constants.AlarmType, notification.alarmType)
        broadcastIntent.putExtra("date", millitohourmin(notification.word))
        broadcastIntent.putExtra("monday", notification.monday)
        broadcastIntent.putExtra("tuesday", notification.tuesday)
        broadcastIntent.putExtra("wednesday", notification.wednesday)
        broadcastIntent.putExtra("thursday", notification.thursday)
        broadcastIntent.putExtra("friday", notification.friday)
        broadcastIntent.putExtra("saturday", notification.saturday)
        broadcastIntent.putExtra("sunday", notification.sunday)
        broadcastIntent.putExtra("vibration", notification.vibration)
        broadcastIntent.putExtra("sound", notification.sound)

        // broadcastIntent.putExtra("date", hourOfDayg.toString() + ":" + minuteOfHourg.toString())

        //Setting alarm pending intent
        alarmIntentRTC = PendingIntent.getBroadcast(
            mainActivity,
            i,
            broadcastIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        //getting instance of AlarmManager service
        alarmManagerRTC = mainActivity.getSystemService(ALARM_SERVICE) as AlarmManager

        //Setting alarm to wake up device every day for clock time.
        //AlarmManager.RTC_WAKEUP is responsible to wake up device for sure, which may not be good practice all the time.
        // Use this when you know what you're doing.
        //Use RTC when you don't need to wake up device, but want to deliver the notification whenever device is woke-up
        //We'll be using RTC.WAKEUP for demo purpose only
        //  alarmManagerRTC!!.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, alarmIntentRTC)
//        alarmManagerRTC!!.set(
//                AlarmManager.RTC_WAKEUP,
//                calendar.timeInMillis,
//                alarmIntentRTC
//        )

        alarmManagerRTC!!.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            notification.word,
            AlarmManager.INTERVAL_DAY,
            alarmIntentRTC
        )

    }

    fun cancelAlarm(mainActivity: Context, notification: Notification) {


        //Setting intent to class where Alarm broadcast message will be handled

        val broadcastIntent = Intent(mainActivity, AlarmReceiver::class.java)
        broadcastIntent.putExtra("id", notification.notiId)
        broadcastIntent.putExtra("date", millitohourmin(notification.word))
        broadcastIntent.putExtra("monday", notification.monday)
        broadcastIntent.putExtra("tuesday", notification.tuesday)
        broadcastIntent.putExtra("wednesday", notification.wednesday)
        broadcastIntent.putExtra("thursday", notification.thursday)
        broadcastIntent.putExtra("friday", notification.friday)
        broadcastIntent.putExtra("saturday", notification.saturday)
        broadcastIntent.putExtra("sunday", notification.sunday)
        broadcastIntent.putExtra("vibration", notification.vibration)
        broadcastIntent.putExtra("sound", notification.sound)

        // broadcastIntent.putExtra("date", hourOfDayg.toString() + ":" + minuteOfHourg.toString())

        //Setting alarm pending intent
        alarmIntentRTC = PendingIntent.getBroadcast(
            mainActivity,
            notification.id,
            broadcastIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        //getting instance of AlarmManager service
        alarmManagerRTC = mainActivity.getSystemService(ALARM_SERVICE) as AlarmManager


        alarmManagerRTC!!.cancel(alarmIntentRTC)

    }


}