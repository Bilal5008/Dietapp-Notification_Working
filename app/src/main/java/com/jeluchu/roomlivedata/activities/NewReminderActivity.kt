package com.jeluchu.roomlivedata.activities


import android.app.Activity
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jeluchu.roomlivedata.R
import com.jeluchu.roomlivedata.model.Notification
import com.jeluchu.roomlivedata.utils.Constants
import com.jeluchu.roomlivedata.utils.SharedPreferenceHelper
import com.michaldrabik.classicmaterialtimepicker.CmtpTimeDialogFragment
import com.michaldrabik.classicmaterialtimepicker.model.CmtpTime12
import com.michaldrabik.classicmaterialtimepicker.utilities.setOnTime12PickedListener
import com.michaldrabik.classicmaterialtimepicker.utilities.setOnTime24PickedListener
import kotlinx.android.synthetic.main.activity_add_alarm.*
import kotlinx.android.synthetic.main.activity_add_alarm.id_blief
import kotlinx.android.synthetic.main.activity_add_alarm.id_breath_air
import kotlinx.android.synthetic.main.activity_add_alarm.id_control
import kotlinx.android.synthetic.main.activity_add_alarm.id_eat_healthy
import kotlinx.android.synthetic.main.activity_add_alarm.id_excercise
import kotlinx.android.synthetic.main.activity_add_alarm.id_go_out
import kotlinx.android.synthetic.main.activity_add_alarm.id_sleep
import kotlinx.android.synthetic.main.activity_add_alarm.id_tech
import kotlinx.android.synthetic.main.activity_add_alarm.id_water
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_alarm.*
import java.text.SimpleDateFormat
import java.util.*


class NewReminderActivity : AppCompatActivity() {
    var alarmType: String? = null
    var notification: Notification? = null
    var calendar: Calendar? = null

    var sharedPreferenceHelper: SharedPreferenceHelper? = null

    var timeInMilliSeconds: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {

        sharedPreferenceHelper = SharedPreferenceHelper.instance


        if (sharedPreferenceHelper!!.getBoolean(Constants.Theme)) {
            setTheme(R.style.darkTheme)

        } else {
            setTheme(R.style.AppTheme)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_alarm)
        getSupportActionBar()!!.hide()

        alarmType = intent.getStringExtra("AlarmType")
        setAlarmHeader(alarmType)


//        notification = intent.getParcelableExtra("Notification")
//
//
//        if (notification != null) {
//            fillData(notification!!)
//            alarm_save.visibility = View.GONE
//            edit_alarm_save.visibility = View.VISIBLE
//        }


        edit_alarm_time_picker.setOnClickListener {
            // Get Current Time
            calendar = Calendar.getInstance()
            val hour = calendar!!.get(Calendar.HOUR_OF_DAY)
            val minute = calendar!!.get(Calendar.MINUTE)

            // Launch Time Picker Dialog
            val timePickerDialog = TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minuteOfHour ->

                    calendar!!.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar!!.set(Calendar.MINUTE, minuteOfHour)
                    calendar!!.set(Calendar.SECOND, 0)


                    val formattedTime = String.format("%02d:%02d", hourOfDay, minuteOfHour)
                    edit_alarm_time_picker.text = formattedTime

                    val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
                    val formattedDate = sdf.format(calendar!!.time)
                    val date = sdf.parse(formattedDate)
                    timeInMilliSeconds = date.time
                },
                hour,
                minute,
                true
            )
            timePickerDialog.show()

        }



        alarm_save.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(edit_alarm_time_picker.text) || edit_alarm_time_picker.text.equals(
                    "Set Alarm"
                )
            ) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                replyIntent.putExtra("alarmTime", calendar!!.timeInMillis)
                replyIntent.putExtra("sound", id_ring.isChecked)
                replyIntent.putExtra("vibration", id_vib.isChecked)
                replyIntent.putExtra("monday", edit_alarm_mon.isChecked)
                replyIntent.putExtra("tuesday", edit_alarm_tues.isChecked)
                replyIntent.putExtra("wednesday", edit_alarm_wed.isChecked)
                replyIntent.putExtra("thursday", edit_alarm_thurs.isChecked)
                replyIntent.putExtra("friday", edit_alarm_fri.isChecked)
                replyIntent.putExtra("saturday", edit_alarm_sat.isChecked)
                replyIntent.putExtra("sunday", edit_alarm_sun.isChecked)


                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }


    }

    private fun fillData(notification: Notification) {
        edit_alarm_time_picker.text = notification.word.toString()

        id_ring.isChecked = notification.sound
        id_vib.isChecked = notification.vibration
        edit_alarm_mon.isChecked = notification.monday
        edit_alarm_tues.isChecked = notification.tuesday
        edit_alarm_wed.isChecked = notification.wednesday
        edit_alarm_thurs.isChecked = notification.thursday
        edit_alarm_fri.isChecked = notification.friday
        edit_alarm_sat.isChecked = notification.saturday
        edit_alarm_sun.isChecked = notification.sunday


    }

    private fun showTime24PickerDialog() {

        val calendar = Calendar.getInstance()
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        val minOfHour = calendar.get(Calendar.MINUTE)
        val dialog = CmtpTimeDialogFragment.newInstance()
        dialog.setInitialTime24(hourOfDay, minOfHour)
        dialog.setOnTime24PickedListener {
            edit_alarm_time_picker.text = it.toString()
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        }
        dialog.show(supportFragmentManager, "TimePicker")
    }

    private fun showTime12PickerDialog() {

        val calendar = Calendar.getInstance()
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        val minOfHour = calendar.get(Calendar.MINUTE)

        print("hour of day" + hourOfDay)
        val dialog = CmtpTimeDialogFragment.newInstance()
        dialog.setInitialTime12(hourOfDay, minOfHour, CmtpTime12.PmAm.PM)
        dialog.setOnTime12PickedListener {
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        }
        dialog.show(supportFragmentManager, "TimePicker")
    }

    private fun setAlarmHeader(alarmType: String?) {
        if (alarmType!!.equals(Constants.Blief)) {
            id_blief.visibility = View.VISIBLE

        } else if (alarmType.equals(Constants.Breath)) {
            id_breath_air.visibility = View.VISIBLE

        } else if (alarmType.equals(Constants.Control)) {

            id_control.visibility = View.VISIBLE

        } else if (alarmType.equals(Constants.EatHealthy)) {
            id_eat_healthy.visibility = View.VISIBLE

        } else if (alarmType.equals(Constants.GoOutSun)) {
            id_go_out.visibility = View.VISIBLE

        } else if (alarmType.equals(Constants.Excercise)) {
            id_excercise.visibility = View.VISIBLE


        } else if (alarmType.equals(Constants.Tech)) {
            id_tech.visibility = View.VISIBLE

        } else if (alarmType.equals(Constants.Water)) {

            id_water.visibility = View.VISIBLE

        } else if (alarmType.equals(Constants.Sleep)) {
            id_sleep.visibility = View.VISIBLE

        }

    }
//    override fun onBackPressed() {
//        super.onBackPressed()
//
//        var intent = Intent(this, AddNewReminderActivity::class.java)
//        startActivity(intent)
//        finish()
//
//    }

}

