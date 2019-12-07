package com.jeluchu.roomlivedata.utils

import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class Constants {

    companion object {
        val listView: String? = "listView"
        val FILE_NAME: String? = "9factordiet"
        val AlarmType = "AlarmType"
        val NotificationAlarmType = "NotificationAlarmType"
        val Breath = "breath"
        val Sleep = "sleep"
        val Water = "water"
        val Excercise = "excerise"
        val EatHealthy = "eathealthy"
        val GoOutSun = "gooutsun"
        val Blief = "blief"
        val Tech = "tech"
        val Control = "control"
        val Theme = "Theme"
        val chewDataFirstTime = "chewDataFirstTime"
        val AddNewReminderActivity = "AddNewReminderActivity"
        val tts = "tts"
        var StopWatchValue = 32
        var FirstTime = "FirstTime"
        var defualtAlarmFirstTime = "defualtAlarmFirstTime"
        var Appcontext = "context"
        var Dietno = "Dietno"

        var kUSER_ID = "USER_ID"
        var kPARENT_ID = "PARENT_ID"

        var PREF_NAME = "com.diet.app"

        //const val BASE_URL = "https://plan.vlatkins.com/wp-json/wcra/v1/getusers/?secret_key=0aPrw1k5acAj4gFcic7DsmhC4e0POftD&param1=252525@sdfgdrg.hk&param2=Accountusername99999"
        const val BASE_URL = "https://plan.vlatkins.com/wp-json/wcra/v1/getusers/"


        //
        //   http://192.168.0.104:3000/api/purchase/allPurchase/userId/parent_id
        // public static final String LOGIN = "abc";//put your end point here

        // const val LOGIN = BASE_URL + "api/members/login"
        const val LOGIN = BASE_URL
        //   const val LOGIN = BASE_URL + "?secret_key={key}&param1={email}&param2={password}"


        fun getProgressDialog(context: Context, msg: String): ProgressDialog {
            val progressDialog = ProgressDialog(context)
            progressDialog.setMessage(msg)
            progressDialog.setCancelable(false)
            return progressDialog
        }


        fun checkInternetConnection(context: Context): Boolean {
            val connectivity = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivity == null) {
                return false
            } else {
                val info = connectivity.allNetworkInfo
                if (info != null) {
                    for (i in 0..info.size) {
                        if (info[i].state == NetworkInfo.State.CONNECTED) {
                            return true
                        }
                    }
                }
            }
            return false
        }


    }


}
