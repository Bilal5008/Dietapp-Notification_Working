package com.jeluchu.roomlivedata.activities


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.*
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.jeluchu.roomlivedata.R
import com.jeluchu.roomlivedata.interfaces.TodayItemClicked
import com.jeluchu.roomlivedata.model.Notification
import com.jeluchu.roomlivedata.utils.Constants
import com.jeluchu.roomlivedata.utils.SharedPreferenceHelper
import kotlinx.android.synthetic.main.activity_chew.*
import java.text.SimpleDateFormat
import java.util.*


class StopActivity : AppCompatActivity(), TodayItemClicked {
    override fun onRowClick(notification: Notification?) {

        var intent = Intent(this, AddNewReminderActivity::class.java)
        intent.putExtra(Constants.AlarmType, notification!!.alarmType)

        this.let { ContextCompat.startActivity(it, intent, null) }


    }


    var totalMilliSeconds: Int = 0
    private val sdf = SimpleDateFormat("dd-MM HH:mm:ss")
    var vibrator: Vibrator? = null
    var toneG: ToneGenerator? = null
    var countDownTimer: CountDownTimer? = null
    var oldSecond: Int = 0
    internal var MillisecondTime: Long = 0
    internal var StartTime: Long = 0
    internal var TimeBuff: Long = 0
    internal var UpdateTime = 0L
    var sharedPreferenceHelper: SharedPreferenceHelper? = null
    var totalTime: String = ""
    var cyclesNo: Int = 0
    var defaultValue: Int = 32


    private var startClockHandler: Handler? = null

    internal var handler: Handler? = null

    internal var Seconds: Int = 0
    internal var Minutes: Int = 0
    internal var MilliSeconds: Int = 0
    private var isStart: Boolean = false
    private var isPause: Boolean = false


    internal var ListElements = arrayOf<String>()

    internal var ListElementsArrayList: MutableList<String>? = null

    //  internal var adapter: ArrayAdapter<String>? = null
    protected var stopFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferenceHelper = SharedPreferenceHelper.instance
        supportActionBar!!.hide()

        if (sharedPreferenceHelper!!.getBoolean(Constants.Theme) == true) {
            setTheme(R.style.darkTheme)

        } else {
            setTheme(R.style.AppTheme)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chew)
        id_paused_text.setSelection(id_paused_text.getText().length)
        id_cycle_text.setSelection(id_cycle_text.getText().length)

//        startClock()
//        startClockHandler = Handler()

        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        toneG = ToneGenerator(AudioManager.STREAM_ALARM, 100)

        handler = Handler()


        ListElementsArrayList = ArrayList(Arrays.asList(*ListElements))

//        adapter = ArrayAdapter(
//            this,
//            android.R.layout.simple_list_item_1,
//            ListElementsArrayList as ArrayList<String>
//        )

        //  listview1!!.adapter = adapter


        id_start!!.setOnClickListener {

            onStartFunction()
        }

        id_pause!!.setOnClickListener {

            onPauseFunction()

        }
        id_resume!!.setOnClickListener {

            onPauseStartFunction()

        }

        id_default_chewing.setOnClickListener {
            id_cycle_text.setText("32")

        }
        id_pause_time.setOnClickListener {
            id_paused_text.setText("5")

        }

        id_reset!!.setOnClickListener {
            onResetFunction()

        }

        id_stop!!.setOnClickListener {

            onAddRecord(it)


        }
//        id_morestats.setOnClickListener {
//
//            val intent = Intent(this, MoreStatesActivity::class.java)
//            intent.putExtra(Constants.listView, ListElementsArrayList as ArrayList<String>)
//            startActivity(intent)
//
//        }

//        listview1.setOnTouchListener({ v, event ->
//            // Setting on Touch Listener for handling the touch inside ScrollView
//            // Disallow the touch request for parent scroll on touch of child view
//            v.parent.requestDisallowInterceptTouchEvent(true)
//            false
//        })
//

        id_cycle_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (s.toString().length == 1 && s.toString().startsWith("0")) {
                    s.clear()
                }
            }
        })


//        countDownTimer = object : CountDownTimer(Integer.parseInt(id_paused_text.text.toString()).toLong() * 1000, 1) {
//            override fun onTick(millisUntilFinished: Long) {
//                val separated = getDateFromMillis(millisUntilFinished).split(":")
//
//                id_time.setText(separated[2])
//                //val progress = millisUntilFinished.toInt() / 1
//                //  pbTime.setProgress(pbTime.getMax() - progress)
//                //                pbTime.setProgress((int) (millisUntilFinished / interval));
//            }
//
//            override fun onFinish() {
//                // tvTimer.setText("00:00:00")
//                countDownTimer!!.cancel()
//                id_pause.visibility = View.VISIBLE
//                beforeReset()
//                resetfunction()
//
//            }
//        }


    }

    private fun startClock() {

        startClockHandler!!.post(mRunnableclock)


    }

    var mRunnableclock: Runnable = object : Runnable {

        override fun run() {


            id_time.text = getTime()

            startClockHandler!!.postDelayed(this, 1000)

        }
    }

    private fun getTime(): String {
        return sdf.format(Date(System.currentTimeMillis()))
    }


    private fun onPauseStartFunction() {
        StartTime = SystemClock.uptimeMillis()
        handler!!.postDelayed(runnable, 0)
        // id_reset!!.isEnabled = false

        isPause = false
        id_resume.visibility = View.GONE
    }

    fun getDateFromMillis(d: Long): String {
        val df = SimpleDateFormat("HH:mm:ss")
        df.timeZone = TimeZone.getTimeZone("GMT")
        return df.format(d)
    }

    private fun onAddRecord(it: View) {

        val replyIntent = Intent()
        replyIntent.putExtra("time", getCurrentTime(it))
        replyIntent.putExtra("cycleno", cyclesNo)
        replyIntent.putExtra("cycleinterval", id_total_time.text.toString())
        setResult(Activity.RESULT_OK, replyIntent)

        finish()
    }

    private fun onResetFunction() {
        handler!!.removeCallbacks(runnable)
        startActivity(intent)

        finish()
    }

    public override fun onDestroy() {
        stopFlag = true
        super.onDestroy()
    }


    private fun onPauseFunction() {
        TimeBuff += MillisecondTime
        handler!!.removeCallbacks(runnable)

        id_start.visibility = View.GONE
        id_resume.visibility = View.VISIBLE


        id_reset.visibility = View.VISIBLE
        isStart = false
        isPause = true
    }

    private fun onStartFunction() {


        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm!!.hideSoftInputFromWindow(activity_main.getWindowToken(), 0)

        Handler().postDelayed({

            //
//            if (isPause) {
//                StartTime = SystemClock.uptimeMillis()
//                handler!!.postDelayed(runnable, 0)
//                id_reset!!.isEnabled = false
//
//                isPause = false
//            } else
            if (!isStart) {

                if (id_cycle_text.text.toString() == "" || Integer.parseInt(id_cycle_text.text.toString()) == 0 || id_paused_text.text.toString() == "" || Integer.parseInt(
                        id_paused_text.text.toString()
                    ) == 0
                ) {
                } else {
                    isStart = true


                    Constants.StopWatchValue = Integer.parseInt(id_cycle_text.text.toString())
                    handler!!.removeCallbacks(runnable)

                    //   id_default.visibility = View.INVISIBLE
                    donut_progress.visibility = View.VISIBLE
                    id_cycle_text.setFocusable(false)
                    id_paused_text.setFocusable(false)
                    id_reset.visibility = View.GONE



                    MillisecondTime = 0L
                    StartTime = 0L
                    TimeBuff = 0L
                    UpdateTime = 0L
                    Seconds = 0
                    Minutes = 0
                    MilliSeconds = 0

                    StartTime = SystemClock.uptimeMillis()
                    handler!!.postDelayed(runnable, 0)
                    id_reset.visibility = View.GONE
                    id_start.visibility = View.GONE

                }
            }
        }, 0)


    }


    fun getCurrentTime(view: View): String {
        val calendar = Calendar.getInstance()
        val mdformat = SimpleDateFormat("HH:mm:ss")
        val strDate = mdformat.format(calendar.time)
        return strDate
    }

    private fun removingcallbacks() {

        handler!!.removeCallbacks(runnable)

    }

    override fun onBackPressed() {

        if (isStart) {
            Toast.makeText(
                applicationContext,
                "Cannot Go back while Timmer is running press Stop to record",
                Toast.LENGTH_SHORT
            ).show()

        } else {
            finish()
        }
    }


    var runnable: Runnable = object : Runnable {

        override fun run() {

            if (stopFlag) return

            MillisecondTime = SystemClock.uptimeMillis() - StartTime

            UpdateTime = TimeBuff + MillisecondTime

            Seconds = (UpdateTime / 1000).toInt()


            donut_progress.progress =
                Math.round(getPercentageValue(Seconds, id_cycle_text.text.toString())).toFloat()
            Minutes = Seconds / 60

            Seconds = Seconds % 60

            onEverySecondChange(Seconds)

            MilliSeconds = (UpdateTime % 1000).toInt()

            textView!!.text =
                ("" + Minutes + ":" + String.format("%02d", Seconds) + ":" + String.format(
                    "%03d",
                    MilliSeconds
                ))

            handler!!.postDelayed(this, 1000)

            if (Seconds >= Integer.parseInt(id_cycle_text.text.toString())) {

                removingcallbacks()
                countDownTimerStarts()
                //  countDownTimer!!.start()
                if (id_ring_phone_bit.isChecked) {
                    toneG!!.startTone(ToneGenerator.TONE_CDMA_CONFIRM, 200)
                } else {
                    toneG!!.stopTone()
                }

                if (id_vib_phone_bit.isChecked) {


                    if (Build.VERSION.SDK_INT >= 26) {
                        vibrator!!.vibrate(
                            VibrationEffect.createOneShot(
                                500,
                                VibrationEffect.DEFAULT_AMPLITUDE
                            )
                        )
                    } else {
                        vibrator!!.vibrate(200)
                    }
                } else {

                    vibrator!!.cancel()
                }



                id_pause.visibility = View.GONE

                //   ListElementsArrayList!!.add(textView.text.toString())
                //   id_total_no_of_bits.text = ListElementsArrayList!!.size.toString()

                //     adapter!!.notifyDataSetChanged()
                textView.text = "00:00:00"
                //       id_pause!!.isEnabled = false
                id_pause.visibility = View.GONE


            }

        }

    }

    fun countDownTimerStarts() {
        countDownTimer = object :
            CountDownTimer(Integer.parseInt(id_paused_text.text.toString()).toLong() * 1000, 1) {
            override fun onFinish() {

                donut_wait.progress = 0.0f
                countDownTimer!!.cancel()
                id_pause.visibility = View.VISIBLE
                beforeReset()
                resetfunction()

            }

            override fun onTick(millisUntilFinished: Long) {
                val separated = getDateFromMillis(millisUntilFinished).split(":")

                id_time.setText(separated[2])
                //  val progress = millisUntilFinished.toInt()


                Seconds = (millisUntilFinished / 1000).toInt()
                print("Value " + Seconds)
                donut_wait.progress =
                    Math.round(100 - getPercentageValue(Seconds, id_paused_text.text.toString()))
                        .toFloat()

                //  pbTime.setProgress((int) (millisUntilFinished / interval));
            }
        }
        countDownTimer!!.start()
    }


    private fun getPercentageValue(seconds: Int, text: String): Float {
        var completion: Float = (seconds.toFloat() / text.toFloat()) * 100
        return completion
    }

    private fun onEverySecondChange(seconds: Int) {
        if (seconds == Integer.parseInt(id_cycle_text.text.toString())) {
            return
        }

        if (seconds != oldSecond) {
            if (id_ring_phone.isChecked) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
                    toneG!!.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200)
                }


            } else {
                toneG!!.stopTone()
                vibrator!!.cancel()
            }
            if (id_vib_phone.isChecked) {


                if (Build.VERSION.SDK_INT >= 26) {
                    vibrator!!.vibrate(
                        VibrationEffect.createOneShot(
                            200,
                            VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )
                } else {
                    vibrator!!.vibrate(200)
                }
            } else {

                vibrator!!.cancel()
            }

        }
        oldSecond = seconds

    }


    fun beforeReset() {
        StartTime = SystemClock.uptimeMillis()
        handler!!.postDelayed(runnable, 0)

    }

    private fun resetfunction() {


        if (id_cycle_text.text.toString() == "" || Integer.parseInt(id_cycle_text.text.toString()) == 0) {
            return
        }

        id_total_bites.text = "" + ++cyclesNo

        totalMilliSeconds = cyclesNo * Integer.parseInt(id_cycle_text.text.toString())


        val sec = totalMilliSeconds % 60
        val min = (totalMilliSeconds / 60) % 60
        val hour = (totalMilliSeconds / 60) / 60
        id_total_time.text = "" + hour + ":" + min + ":" + sec


        MillisecondTime = 0L
        StartTime = 0L
        TimeBuff = 0L
        UpdateTime = 0L
        Seconds = 0
        Minutes = 0
        MilliSeconds = 0

//
//        ListElementsArrayList!!.add(textView.text.toString())
//
//        adapter!!.notifyDataSetChanged()
//        textView.text = "00:00:00"

        //   id_pause!!.isEnabled = true
        id_pause.visibility = View.VISIBLE
        StartTime = SystemClock.uptimeMillis()
        handler!!.postDelayed(runnable, 0)

    }


}