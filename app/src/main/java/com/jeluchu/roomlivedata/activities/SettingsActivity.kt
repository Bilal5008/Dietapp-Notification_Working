package com.jeluchu.roomlivedata.activities

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import com.jeluchu.roomlivedata.R
import com.jeluchu.roomlivedata.managers.TTSManager
import com.jeluchu.roomlivedata.utils.Constants
import com.jeluchu.roomlivedata.utils.SharedPreferenceHelper
import com.jeluchu.roomlivedata.utils.SharedPreferenceHelper.Companion.instance
import kotlinx.android.synthetic.main.activity_settings.*


class SettingsActivity : AppCompatActivity() {
    var sharedPreferenceHelper: SharedPreferenceHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        sharedPreferenceHelper = instance


        if (sharedPreferenceHelper!!.getBoolean(Constants.Theme)) {
            setTheme(R.style.darkTheme)

        } else {
            setTheme(R.style.AppTheme)
        }
//
//        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
//            setTheme(R.style.darkTheme)
//        } else
//            setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

//        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
//            switch_button.isChecked = true
//        }
        switch_button.isChecked = !sharedPreferenceHelper!!.getBoolean(Constants.Theme)



        this.switch_button.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            // do something, the isChecked will be
            // true if the switch is in the On position
            if (isChecked) {

                setTheme(R.style.AppTheme)
                sharedPreferenceHelper!!.setBoolean(Constants.Theme, false)
                // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                restartApp()
            } else {

                setTheme(R.style.darkTheme)
                sharedPreferenceHelper!!.setBoolean(Constants.Theme, true)
                //  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                restartApp()
            }
        })
        this.switch_tts.setChecked(sharedPreferenceHelper!!.getBoolean(Constants.tts))
        this.switch_tts.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->

            sharedPreferenceHelper!!.setBoolean(Constants.tts, isChecked)


//
//            if (hasNotificationAccess())
//            {
//                if (sharedPreferenceHelper!!.getBoolean(Constants.tts)) {
//                    TTSManager.getInstance(application).unmute()
//                } else {
//                    TTSManager.getInstance(application).mute()
//                }
//                //service is enabled do something
//            } else {
//                //service is not enabled try to enabled by calling...
//                if (sharedPreferenceHelper!!.getBoolean(Constants.tts)) {
//                    TTSManager.getInstance(application).unmute()
//                } else {
//                    TTSManager.getInstance(application).mute()
//                }
//                getApplicationContext().startActivity( Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
//            }
        })

        setTitle("Settings")


    }

    private fun restartApp() {
        val intent = Intent(applicationContext, SettingsActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        val intent = Intent(this, HomeScreenActivity::class.java)
        startActivity(intent)
        finish()
    }

//    fun hasNotificationAccess(): Boolean {
//        val contentResolver = this.contentResolver
//        val enabledNotificationListeners =
//            Settings.Secure.getString(contentResolver, "enabled_notification_listeners")
//        val packageName = this.packageName
//
//        // check to see if the enabledNotificationListeners String contains our package name
//        return !(enabledNotificationListeners == null || !enabledNotificationListeners.contains(
//            packageName
//        ))
//    }
}
