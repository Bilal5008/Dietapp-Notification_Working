package com.jeluchu.roomlivedata.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.facebook.stetho.Stetho


import com.jeluchu.roomlivedata.R


class SplashScreen : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        getSupportActionBar()!!.hide()
        Stetho.initializeWithDefaults(this)
        initNotificationChannels()
        launchSplash()


    }


    private fun launchSplash() {
        val SPLASH_TIMEOUT = 3000
        Handler().postDelayed({
            //     if (Utilities.isUserLoggedIn(this@SplashScreen)) {
            startActivity(Intent(this@SplashScreen, HomeScreenActivity::class.java))
            finish()
//            } else {
//                startActivity(Intent(this@SplashScreen, LoginActivity::class.java))
//                finish()
//            }
        }, SPLASH_TIMEOUT.toLong())
    }


    private fun initNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val pattern = longArrayOf(500, 500, 500, 500, 500)
            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                .build()

            val notificationManager = getSystemService(NotificationManager::class.java)
            // create notification channel
            val messageChannelName = getString(R.string.notification_channel_name)
            val notificationChannel = NotificationChannel(
                getString(R.string.notification_channel_id),
                messageChannelName,
                importance
            )
            notificationChannel.description = getString(R.string.notification_channel_description)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = ContextCompat.getColor(this, R.color.colorAccent)
            notificationChannel.setShowBadge(true)
            notificationChannel.canShowBadge()
            notificationChannel.vibrationPattern = pattern
            notificationChannel.setSound(defaultSoundUri, audioAttributes)
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager.createNotificationChannel(notificationChannel)

        }

    }


}
