package com.jeluchu.roomlivedata.activities


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.jeluchu.roomlivedata.R
import com.jeluchu.roomlivedata.model.Notification
import com.jeluchu.roomlivedata.utils.Constants
import com.jeluchu.roomlivedata.utils.SharedPreferenceHelper
import com.jeluchu.roomlivedata.viewmodels.WordViewModel
import kotlinx.android.synthetic.main.activity_reminders.*
import kotlinx.android.synthetic.main.breath_item_layout.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class RemindersActivity : AppCompatActivity() {

    private var mActive: Boolean = false
    private var mHandler: Handler? = null
    val notificationListbreath = ArrayList<Notification>()
    val notificationListsleep = ArrayList<Notification>()
    val notificationListwater = ArrayList<Notification>()
    val notificationListexcercise = ArrayList<Notification>()
    val notificationListeat = ArrayList<Notification>()
    val notificationListsun = ArrayList<Notification>()
    val notificationListblief = ArrayList<Notification>()
    val notificationListtech = ArrayList<Notification>()
    val notificationListcontrol = ArrayList<Notification>()
    private lateinit var wordViewModel: WordViewModel
    private val sdf = SimpleDateFormat("dd-M-yyyy, hh:mm")
    var sharedPreferenceHelper: SharedPreferenceHelper? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        sharedPreferenceHelper = SharedPreferenceHelper.instance


        if (sharedPreferenceHelper!!.getBoolean(Constants.Theme)) {
            setTheme(R.style.darkTheme)

        } else {
            setTheme(R.style.AppTheme)
        }








        super.onCreate(savedInstanceState)
        getSupportActionBar()!!.hide()
        mHandler = Handler()
        startClock()

        setContentView(R.layout.activity_reminders)
        setTitle("9 Factor diet")
        id_sleep.setOnClickListener {
            onSleepClicked()
        }

        id_breath_air.setOnClickListener {
            onBreathAirClicked()
        }

        id_water.setOnClickListener {
            onWaterClicked()
        }
        id_excercise.setOnClickListener {
            onExcerciseClicked()
        }
        id_eat_healthy.setOnClickListener {
            onEatHealthyClicked()
        }
        id_go_out.setOnClickListener {
            onGoOutClicked()
        }
        id_blief.setOnClickListener {
            onBliefClicked()
        }
        id_tech.setOnClickListener {
            onTechClicked()
        }
        id_control.setOnClickListener {
            onControlClicked()
        }

        wordViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(WordViewModel::class.java)



        wordViewModel.allWords.observe(this, androidx.lifecycle.Observer { words ->
            notificationListbreath.clear()


            for (i in 0..words.size - 1) {
                if (words[i].alarmType.equals(Constants.Sleep)) {


                    if (notificationListsleep.size > 0) {
                        for (j in 0..notificationListsleep.size - 1) {
                            if (!notificationListsleep[j].alarmType.equals(Constants.Sleep))
                                notificationListsleep.add(words[i])

                        }
                    } else {
                        notificationListsleep.add(words[i])
                    }
                } else if (words[i].alarmType.equals(Constants.Breath)) {


                    if (notificationListbreath.size > 0) {
                        for (j in 0..notificationListbreath.size - 1) {
                            if (!notificationListbreath[j].alarmType.equals(Constants.Breath))
                                notificationListbreath.add(words[i])
                        }
                    } else {
                        notificationListbreath.add(words[i])
                    }
                } else if (words[i].alarmType.equals(Constants.Water)) {


                    if (notificationListwater.size > 0) {
                        for (j in 0..notificationListwater.size - 1) {
                            if (!notificationListwater[j].alarmType.equals(Constants.Water))
                                notificationListwater.add(words[i])
                        }
                    } else {
                        notificationListwater.add(words[i])
                    }
                } else if (words[i].alarmType.equals(Constants.Excercise)) {


                    if (notificationListexcercise.size > 0) {
                        for (j in 0..notificationListexcercise.size - 1) {
                            if (!notificationListexcercise[j].alarmType.equals(Constants.Excercise))
                                notificationListexcercise.add(words[i])
                        }
                    } else {
                        notificationListexcercise.add(words[i])
                    }
                } else if (words[i].alarmType.equals(Constants.GoOutSun)) {


                    if (notificationListsun.size > 0) {
                        for (j in 0..notificationListsun.size - 1) {
                            if (!notificationListsun[j].alarmType.equals(Constants.GoOutSun))
                                notificationListsun.add(words[i])
                        }
                    } else {
                        notificationListsun.add(words[i])
                    }
                } else if (words[i].alarmType.equals(Constants.Blief)) {


                    if (notificationListblief.size > 0) {
                        for (j in 0..notificationListblief.size - 1) {
                            if (!notificationListblief[j].alarmType.equals(Constants.Blief))
                                notificationListblief.add(words[i])
                        }
                    } else {
                        notificationListblief.add(words[i])
                    }
                } else if (words[i].alarmType.equals(Constants.Tech)) {


                    if (notificationListtech.size > 0) {
                        for (j in 0..notificationListtech.size - 1) {
                            if (!notificationListtech[j].alarmType.equals(Constants.Tech))
                                notificationListtech.add(words[i])
                        }
                    } else {
                        notificationListtech.add(words[i])
                    }
                } else if (words[i].alarmType.equals(Constants.Control)) {


                    if (notificationListcontrol.size > 0) {
                        for (j in 0..notificationListcontrol.size - 1) {
                            if (!notificationListcontrol[j].alarmType.equals(Constants.Control))
                                notificationListcontrol.add(words[i])
                        }
                    } else {
                        notificationListcontrol.add(words[i])
                    }
                } else if (words[i].alarmType.equals(Constants.EatHealthy)) {


                    if (notificationListeat.size > 0) {
                        for (j in 0..notificationListeat.size - 1) {
                            if (!notificationListeat[j].alarmType.equals(Constants.EatHealthy))
                                notificationListeat.add(words[i])
                        }
                    } else {
                        notificationListeat.add(words[i])
                    }
                }


            }


//            id_sleep.id_next_reminder.text = "Upcoming alarm: " + notificationListsleep[0].word
//            id_breath_air.id_next_reminder.text =
//                "Upcoming alarm: " + notificationListbreath[0].word
//            id_water.id_next_reminder.text = "Upcoming alarm: " + notificationListwater[0].word
//            id_excercise.id_next_reminder.text =
//                "Upcoming alarm: " + notificationListexcercise[0].word
//            id_eat_healthy.id_next_reminder.text =
//                "Upcoming alarm: " + notificationListeat[0].word
//            id_go_out.id_next_reminder.text = "Upcoming alarm: " + notificationListsun[0].word
//            id_blief.id_next_reminder.text = "Upcoming alarm: " + notificationListblief[0].word
//            id_tech.id_next_reminder.text = "Upcoming alarm: " + notificationListtech[0].word
//            id_control.id_next_reminder.text =
//                "Upcoming alarm: " + notificationListcontrol[0].word


        })


    }
//
//    private fun startCountDownBreath(duration: Long, interval: Long) {
//        countDownTimer = object : CountDownTimer(duration, interval) {
//            override fun onTick(millisUntilFinished: Long) {
//
//                id_breath_air.id_next_reminder.text =
//                    "Upcoming alarm: " + getDateFromMillis(millisUntilFinished)
//
//            }
//
//            override fun onFinish() {
//
//
//                id_breath_air.id_next_reminder.text = "Time up for this Alarm."
//            }
//        }
//        countDownTimer!!.start()
//
//    }

//    private fun startCountDownTimerSleep(duration: Long, interval: Long) {
//        countDownTimer = object : CountDownTimer(duration, interval) {
//            override fun onTick(millisUntilFinished: Long) {
//
//                id_sleep.id_next_reminder.text =
//                    "Upcoming alarm: " + getDateFromMillis(millisUntilFinished)
//
//            }
//
//            override fun onFinish() {
//
//
//                id_breath_air.id_next_reminder.text = "Time up for this Alarm."
//            }
//        }
//        countDownTimer!!.start()
//
//    }

//    fun getTimeInMilli(notificationListbreath: String): Long {
//
//        var min = Integer.parseInt(notificationListbreath.substring(0, 2));
//
//        var sec = Integer.parseInt(notificationListbreath.substring(3));
//
//        var t = (min * 60L) + sec * 60000;
//
//        var result = TimeUnit.SECONDS.toMillis(t)
//
//        return result
//
//
//    }


//    private fun setBreathRemaining(notificationList: ArrayList<Notification>) {
//        val currentString = notificationList[0].word
//        val separated =
//            currentString.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
//        val totalMin =
//            Integer.parseInt(separated[0]) * 60 + Integer.parseInt(separated[1])   // this will contain "Fruit"
//
//
//    }

    private fun onGoOutClicked() {
        var intent = Intent(this, AddNewReminderActivity::class.java)
        intent.putExtra(Constants.AlarmType, Constants.GoOutSun)

    }

    private fun onControlClicked() {
        var intent = Intent(this, AddNewReminderActivity::class.java)
        intent.putExtra(Constants.AlarmType, Constants.Control)
        startActivity(intent)

        startActivity(intent)


    }

    private fun onBreathAirClicked() {
        var intent = Intent(this, AddNewReminderActivity::class.java)
        intent.putExtra(Constants.AlarmType, Constants.Breath)

        startActivity(intent)
    }

    private fun startClock() {
        mActive = true
        mHandler!!.post(mRunnableclock)
    }

    private fun onEatHealthyClicked() {
        var intent = Intent(this, AddNewReminderActivity::class.java)
        intent.putExtra(Constants.AlarmType, Constants.EatHealthy)
        startActivity(intent)

    }


    private fun onTechClicked() {

        var intent = Intent(this, AddNewReminderActivity::class.java)
        intent.putExtra(Constants.AlarmType, Constants.Tech)

        startActivity(intent)
    }

    private fun onBliefClicked() {

        var intent = Intent(this, AddNewReminderActivity::class.java)
        intent.putExtra(Constants.AlarmType, Constants.Blief)

        startActivity(intent)

    }


    private fun onWaterClicked() {
        var intent = Intent(this, AddNewReminderActivity::class.java)
        intent.putExtra(Constants.AlarmType, Constants.Water)

        startActivity(intent)

    }

    override fun onDestroy() {
        super.onDestroy()

    }

    private fun onExcerciseClicked() {
        var intent = Intent(this, AddNewReminderActivity::class.java)
        intent.putExtra(Constants.AlarmType, Constants.Excercise)

        startActivity(intent)
    }

    private fun onSleepClicked() {
        var intent = Intent(this, AddNewReminderActivity::class.java)
        intent.putExtra(Constants.AlarmType, Constants.Sleep)

        startActivity(intent)

    }


    var mRunnableclock: Runnable = object : Runnable {

        override fun run() {
            if (mActive) {

                id_time.text = getTime()

                mHandler!!.postDelayed(this, 1000)
            }
        }


        private fun getTime(): String {
            return sdf.format(Date(System.currentTimeMillis()))
        }


    }

    fun getDateFromMillis(d: Long): String {
        val df = SimpleDateFormat("HH:mm:ss")
        // df.timeZone = TimeZone.getTimeZone("GMT")
        return df.format(d)
    }

}


