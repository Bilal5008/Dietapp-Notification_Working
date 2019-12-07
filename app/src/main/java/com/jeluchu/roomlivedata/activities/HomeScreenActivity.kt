package com.jeluchu.roomlivedata.activities

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.alarms.AlarmReceiver
import com.example.alarms.Utils
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jeluchu.roomlivedata.R
import com.jeluchu.roomlivedata.model.Notification
import com.jeluchu.roomlivedata.utils.Constants
import com.jeluchu.roomlivedata.utils.SharedPreferenceHelper
import com.jeluchu.roomlivedata.viewmodels.WordViewModel
import kotlinx.android.synthetic.main.activity_home_screen.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class HomeScreenActivity : AppCompatActivity() {
    private var mActive: Boolean = false

    private var mHandler: Handler? = null
    private var intentAlarm: Intent? = null

    var notificationList = ArrayList<Notification>()
    var manager: AlarmManager? = null
    private val sdf = SimpleDateFormat("dd-M-yyyy, hh:mm")
    var sharedPreferenceHelper: SharedPreferenceHelper? = null

    private lateinit var wordViewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        sharedPreferenceHelper = SharedPreferenceHelper.instance


        if (sharedPreferenceHelper!!.getBoolean(Constants.Theme)) {
            setTheme(R.style.darkTheme)

        } else {
            setTheme(R.style.AppTheme)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)



        wordViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(WordViewModel::class.java)

//        wordViewModel.allWords.observe(this, androidx.lifecycle.Observer { words ->
//
//
//            for (i in 0..words.size - 1) {
//                wordViewModel.insert(notificationList[i])
//                setAlarm(notificationList[i])
//                Log.e("HomeScreenActivity", "setAlarm: " + notificationList[i])
//            }
//
//            //  Log.e("HomeScreenActivity", "setAlarm: " + notificationList[words.size ])
//        })


//            for (i in 0..words.size - 1) {
//                if (words[i].alarmType.equals(alarmType)) {
//                    notificationList.add(words[i])
//                }
//            }
//            // setAlarm(notificationList)
//            adapter.setWords(notificationList)


        if (!sharedPreferenceHelper!!.getBoolean(Constants.defualtAlarmFirstTime)) {
            manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            intentAlarm = Intent(this, AlarmReceiver::class.java)
//            freshAirFun()
//            goodNightSleep()
//            drinkWater()
//            physicalActivity()
//            eatHealthyFood()
//            gooutinSun()
//            blief()
//            tech()
            control()
            sharedPreferenceHelper!!.setBoolean(Constants.defualtAlarmFirstTime, true)
        }



        getSupportActionBar()!!.hide()
        mHandler = Handler()

        startClock()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.getMenu().getItem(0).setCheckable(false)

        id_setting.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            finish()

        }

        id_selectdiet.setOnClickListener {
            val intent = Intent(this, DietFactorActivity::class.java)
            startActivity(intent)

        }
        id_notification.setOnClickListener {
            val intent = Intent(this, RemindersActivity::class.java)
            startActivity(intent)

        }
        id_stopwatch.setOnClickListener {
            val intent = Intent(this, ChewDataActivity::class.java)
            startActivity(intent)

        }


    }

//    private fun setAlarm(notificationList: List<Notification>) {
//        Log.e("AddNewReminderActivity", "setAlarm: ")
//
//        for (i in notificationList.indices) {
//
//
//            var intent = Intent(this, AlarmReciever::class.java)
//            intent.putExtra("Title", notificationList[i].word)
//            intent.putExtra("Text", "new notification" + i)
//            intent.putExtra("vibration", notificationList[i].vibration)
//            intent.putExtra("sound", notificationList[i].sound)
//            intent.putExtra("id", notificationList[i].id)
//            intent.putExtra("monday", notificationList[i].monday)
//            intent.putExtra("tuesday", notificationList[i].tuesday)
//            intent.putExtra("wednesday", notificationList[i].wednesday)
//            intent.putExtra("thursday", notificationList[i].thursday)
//            intent.putExtra("friday", notificationList[i].friday)
//            intent.putExtra("saturday", notificationList[i].saturday)
//            intent.putExtra("sunday", notificationList[i].sunday)
//            var res = notificationList[i].word.split(":")
//
//            val calendar: Calendar = Calendar.getInstance().apply {
//                timeInMillis = System.currentTimeMillis()
//                set(Calendar.HOUR_OF_DAY, res[0].toInt())
//                set(Calendar.MINUTE, res[1].toInt())
//            }
//
//
//
//            pendingintentArray.add(
//                PendingIntent.getBroadcast(
//                    this,
//                    notificationList[i].id,
//                    intent,
//                    PendingIntent.FLAG_UPDATE_CURRENT
//                )
//            )
////            manager?.setRepeating(
////                AlarmManager.RTC_WAKEUP,
////                calendar.timeInMillis,
////                AlarmManager.INTERVAL_DAY,
////                pendingintentArray[i]
////            )
//
//
//            manager?.setExact(
//                AlarmManager.RTC_WAKEUP,
//                calendar.timeInMillis,
//                pendingintentArray[i]
//
//            )
//            Log.e("AddNewReminderActivity", "setAlarm: " + pendingintentArray[i])
//
//        }
//
//    }

    private fun startClock() {
        mActive = true
        mHandler!!.post(mRunnableclock)
    }

    var mRunnableclock: Runnable = object : Runnable {

        override fun run() {
            if (mActive) {


                time.text = getTime()

                mHandler!!.postDelayed(this, 1000)
            }
        }


        private fun getTime(): String {
            return sdf.format(Date(System.currentTimeMillis()))
        }


    }

    private val mOnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {

            override fun onNavigationItemSelected(item: MenuItem): Boolean {

                when (item.getItemId()) {
                    R.id.action_item1 -> {
                        startActivityNineFactor()

                        return true
                    }
                    R.id.action_item2 -> {
                        startActivityChew()

                        return true
                    }
                    R.id.action_item3 -> {
                        startActivityDietFactor()
                        return true
                    }
                    R.id.action_item4 -> {
                        startActivitySupport()
                        return true
                    }
                    R.id.action_item5 -> {
                        startTodayAlarms()
                        return true
                    }
                }
                return false
            }
        }

    private fun startTodayAlarms() {
        val intent = Intent(this, TodayAlarmActivity::class.java)
        startActivity(intent)
        navigation.getMenu().getItem(0).setCheckable(true)
    }

    private fun startActivitySearch() {
        return
    }

    private fun startActivitySupport() {
        return

    }

    private fun startActivityChew() {
        val intent = Intent(this, ChewDataActivity::class.java)
        startActivity(intent)
    }

    private fun startActivityNineFactor() {
        val intent = Intent(this, RemindersActivity::class.java)
        startActivity(intent)
    }

    private fun startActivityDietFactor() {
        val intent = Intent(this, DietFactorActivity::class.java)
        startActivity(intent)
    }


    private fun control() {
        var notificationModela =
            Notification(
                0,
                9,
                miliseconds(22, 21, 0),
                false,
                true,
                true,
                Constants.Control,
                true,
                true,
                true,
                true,
                true,
                true,
                true
            )

        // wordViewModel.insert(notificationModela)
        notificationList.add(notificationModela)
        // setAlarm(notificationModela)

        var notificationModelb =
            Notification(
                0,

                18,

                miliseconds(23, 10, 0),
                false,
                true,
                true,
                Constants.Control,
                true,
                true,
                true,
                true,
                true,
                true,
                true
            )


        notificationList.add(notificationModelb)


        for (i in 0..notificationList.size - 1) {
            wordViewModel.insert(notificationList[i])
            setAlarm(notificationList[i])
            Log.e("HomeScreenActivity", "setAlarm: " + notificationList[i])
        }


    }

    private fun tech() {
        var notificationModela =
            Notification(
                0,

                8,
                miliseconds(7, 0, 0),

                false,
                true,
                true,
                Constants.Tech,
                true,
                true,
                true,
                true,
                true,
                true,
                true
            )


        notificationList.add(notificationModela)
    }


    private fun blief() {
        var notificationModela =
            Notification(
                0,

                7,
                miliseconds(22, 0, 0),

                false,
                true,
                true,
                Constants.Blief,
                true,
                true,
                true,
                true,
                true,
                true,
                true
            )

        // wordViewModel.insert(notificationModela)
        //setAlarm(notificationModela)
        notificationList.add(notificationModela)
    }

    private fun gooutinSun() {
        var notificationModela =
            Notification(
                0,

                6,
                miliseconds(12, 45, 0),

                false,
                true,
                true,
                Constants.GoOutSun,
                true,
                true,
                true,
                true,
                true,
                true,
                true
            )

        // wordViewModel.insert(notificationModela)
        // setAlarm(notificationModela)
        notificationList.add(notificationModela)

    }

    private fun eatHealthyFood() {
        var notificationModela =
            Notification(
                0,

                5,
                miliseconds(10, 45, 0),

                false,
                true,
                true,
                Constants.EatHealthy,
                true,
                true,
                true,
                true,
                true,
                true,
                true
            )

        // wordViewModel.insert(notificationModela)
        //  setAlarm(notificationModela)
        notificationList.add(notificationModela)
        var notificationModelb =
            Notification(
                0,

                10,
                miliseconds(16, 45, 0),

                false,
                true,
                true,
                Constants.EatHealthy,
                true,
                true,
                true,
                true,
                true,
                true,
                true
            )

        // wordViewModel.insert(notificationModelb)
//        setAlarm(notificationModelb)
        notificationList.add(notificationModelb)

        var notificationModelc =
            Notification(
                0,

                16,
                miliseconds(20, 45, 0),

                false,
                true,
                true,
                Constants.EatHealthy,
                true,
                true,
                true,
                true,
                true,
                true,
                true
            )

        // wordViewModel.insert(notificationModelc)
        // setAlarm(notificationModelc)
        notificationList.add(notificationModelc)

    }

    private fun physicalActivity() {

        var notificationModela =
            Notification(
                0,

                4,
                miliseconds(18, 30, 0),

                false,
                false,
                false,
                Constants.Excercise,
                false,
                true,
                false,
                true,
                false,
                false,
                true
            )

        // wordViewModel.insert(notificationModela)
        //     setAlarm(notificationModela)
        notificationList.add(notificationModela)


    }

    private fun drinkWater() {

        var notificationModel =
            Notification(
                0,

                2,
                miliseconds(7, 0, 0),
                false,
                false,
                false,
                Constants.Water,
                true,
                true,
                true,
                true,
                true,
                true,
                true
            )

        // wordViewModel.insert(notificationModel)
        // setAlarm(notificationModel)
        notificationList.add(notificationModel)


        var notificationModelc =
            Notification(
                0,

                3,
                miliseconds(9, 5, 0),

                false,
                false,
                false,
                Constants.Water,
                true,
                true,
                true,
                true,
                true,
                true,
                true
            )

        // wordViewModel.insert(notificationModelc)
        //setAlarm(notificationModelc)
        notificationList.add(notificationModelc)


        var notificationModeld =
            Notification(
                0,

                11,
                miliseconds(11, 0, 0),

                false,
                false,
                false,
                Constants.Water,
                true,
                true,
                true,
                true,
                true,
                true,
                true
            )

        // wordViewModel.insert(notificationModeld)
        //   setAlarm(notificationModeld)
        notificationList.add(notificationModeld)


        var notificationModelh =
            Notification(
                0,

                12,
                miliseconds(12, 5, 0),

                false,
                false,
                false,
                Constants.Water,
                true,
                true,
                true,
                true,
                true,
                true,
                true
            )

        // wordViewModel.insert(notificationModelh)
        notificationList.add(notificationModelh)
        //  setAlarm(notificationModelh)


        var notificationModeli =
            Notification(
                0,

                13,
                miliseconds(14, 0, 0),

                false,
                false,
                false,
                Constants.Water,
                true,
                true,
                true,
                true,
                true,
                true,
                true
            )

        // wordViewModel.insert(notificationModeli)
        //  setAlarm(notificationModeli)
        notificationList.add(notificationModeli)


        var notificationModell =
            Notification(
                0,

                14,
                miliseconds(16, 5, 0),
                false,
                false,
                false,
                Constants.Water,
                true,
                true,
                true,
                true,
                true,
                true,
                true
            )

        // wordViewModel.insert(notificationModell)
        // setAlarm(notificationModell)
        notificationList.add(notificationModell)


        //   setAlarm(notificationModell)
        var notificationModelm =
            Notification(
                0,

                15,
                miliseconds(18, 0, 0),

                false,
                false,
                false,
                Constants.Water,
                true,
                true,
                true,
                true,
                true,
                true,
                true
            )

        // wordViewModel.insert(notificationModelm)
        // setAlarm(notificationModelm)
        notificationList.add(notificationModelm)


        var notificationModelp =
            Notification(
                0,
                17,
                miliseconds(20, 5, 0),

                false,
                false,
                false,
                Constants.Water,
                true,
                true,
                true,
                true,
                true,
                true,
                true
            )

        // wordViewModel.insert(notificationModelp)
        //   setAlarm(notificationModelp)
        notificationList.add(notificationModelp)


    }

    private fun goodNightSleep() {


        var notificationModel =
            Notification(
                0,

                1,

                miliseconds(13, 50, 0),

                false,
                false,
                false,
                Constants.Sleep,
                true,
                true,
                true,
                true,
                true,
                true,
                true
            )
        notificationList.add(notificationModel)


    }

    fun createID(): Int {
        val now = Date()
        return Integer.parseInt(SimpleDateFormat("ddHHmmss", Locale.US).format(now))
    }


    private fun freshAirFun() {


        var notificationModel =
            Notification(
                0,
                0,

                miliseconds(17, 15, 0),
                false,
                true,
                true,
                Constants.Breath,
                true,
                true,
                true,
                true,
                true,
                true,
                true
            )

        notificationList.add(notificationModel)


    }


    private fun setAlarm(notificationList: Notification) {
        /* Retrieve a PendingIntent that will perform a broadcast */
        Toast.makeText(this, "Alarm has been set!", Toast.LENGTH_LONG).show()
        Utils.setAlarmNew(this, notificationList, notificationList.notiId)
    }


    fun miliseconds(hrs: Int, min: Int, sec: Int): Long {
        return (((hrs * 60 * 60 + min * 60 + sec) * 1000).toLong())
    }


}

