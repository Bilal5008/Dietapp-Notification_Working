package com.jeluchu.roomlivedata.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.jeluchu.roomlivedata.R
import com.jeluchu.roomlivedata.utils.Constants
import com.jeluchu.roomlivedata.utils.SharedPreferenceHelper

class SleepNotificationActivity : AppCompatActivity() {

    var sharedPreferenceHelper: SharedPreferenceHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        sharedPreferenceHelper = SharedPreferenceHelper.instance


        if (sharedPreferenceHelper!!.getBoolean(Constants.Theme)) {
            setTheme(R.style.darkTheme)

        } else {
            setTheme(R.style.AppTheme)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sleep_notification)
    }
}
