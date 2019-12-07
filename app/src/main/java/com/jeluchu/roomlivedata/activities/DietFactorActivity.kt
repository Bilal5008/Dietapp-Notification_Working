package com.jeluchu.roomlivedata.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jeluchu.roomlivedata.R

import com.jeluchu.roomlivedata.utils.Constants
import com.jeluchu.roomlivedata.utils.SharedPreferenceHelper
import kotlinx.android.synthetic.main.dietfactor_activity.*
import me.ithebk.barchart.BarChartModel
import java.text.SimpleDateFormat
import java.util.*


class DietFactorActivity : AppCompatActivity() {
    private var mActive: Boolean = false
    private var mHandler: Handler? = null
    private var actualvalue: Int = 0
    private val sdf = SimpleDateFormat("MM-dd-yy, hh:mm:ss")
    var sharedPreferenceHelper: SharedPreferenceHelper? = null

    private var totalValue: String = ""
    private var value: Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {

        sharedPreferenceHelper = SharedPreferenceHelper.instance

        if (sharedPreferenceHelper!!.getBoolean(Constants.Theme)) {
            setTheme(R.style.darkTheme)
        } else {
            setTheme(R.style.AppTheme)
        }

        super.onCreate(savedInstanceState)

        getSupportActionBar()!!.hide()
        setContentView(R.layout.dietfactor_activity)
        setTitle("9 Factor Screen")

        mHandler = Handler()
        startClock()




        id_selectdiet.setOnClickListener { onSelectDietClicked() }




        id_dietno_text1.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub



            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
                // TODO Auto-generated method stub

            }

            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub

                if (id_dietno_text1.text.length < 0 && id_dietno_text2.text.length < 0) {
                    id_dietno_text2.requestFocus()
                } else {
                    checkValues()
                }
            }

        })
        id_dietno_text2.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub



            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int, after: Int
            ) {
                // TODO Auto-generated method stub

            }

            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub

                checkValues()
            }

        })


//
//        id_dietno_text2.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//
//
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                image_ff.visibility = View.GONE
//                bar_chart_vertical.visibility = View.VISIBLE
//                if (count == 1 && id_dietno_text1.text.length > 0) {
//
//                    totalValue = id_dietno_text1.text.toString() + "." + id_dietno_text2.text.toString()
//
//                    print("Do dinasours still exist?\n" + totalValue)
//                    if (totalValue.toFloat() >= 0.0f || totalValue.toFloat() == 0.1f) {
//                        id_fatburn_text.text = "NOT IN KETOSIS"
//                        actualvalue = 0
//                    } else if (totalValue.toFloat().toFloat() >= 0.2f && totalValue.toFloat()
//                        <= 0.5
//                    ) {
//                        id_fatburn_text.text = "KETOSIS BEGIN"
//                        actualvalue = 1
//                    } else if (totalValue.toFloat() >= 0.6f &&
//                        totalValue.toFloat() <= 2.0f
//                    ) {
//                        id_fatburn_text.text = "FULL STATE OF KETOSIS"
//                        actualvalue = 2
//                    } else if (totalValue.toFloat().toFloat() >= 2.1f &&
//                        totalValue.toFloat()
//                        <= 3.0f
//                    ) {
//                        id_fatburn_text.text = "FULL STATE OF KETOSIS"
//                        actualvalue = 3
//                    } else if (totalValue.toFloat().toFloat() >= 3.1f &&
//                        totalValue.toFloat()
//                        <= 5.0f
//                    ) {
//                        id_fatburn_text.text = "STARVATION"
//                        actualvalue = 4
//                    } else if (totalValue.toFloat().toFloat() >= 5.1f &&
//                        totalValue.toFloat()
//                        <= 7.0f
//                    ) {
//                        id_fatburn_text.text = "MEDICAL HELP"
//                        actualvalue = 5
//                    } else if (totalValue.toFloat().toFloat() >= 7.1f &&
//                        totalValue.toFloat()
//                        <= 7.9f
//                    ) {
//                        id_fatburn_text.text = "DANGER"
//                        actualvalue = 6
//
//
//                    }
//                    bar_chart_vertical.clearAll()
//                    for (i in 0 until actualvalue) {
//                        val barChartModel = BarChartModel()
//                        barChartModel.barValue = (i + 2) * 10
//                        bar_chart_vertical.addBar(barChartModel)
//                    }
//
//                } else {
//                    image_ff.visibility = View.VISIBLE
//                    bar_chart_vertical.visibility = View.GONE
//                    id_fatburn_text.text = ""
//                    bar_chart_vertical.clearAll()
//                }
//
//
//            }
//
//        })

    }

    private fun checkValues() {
        if (id_dietno_text1.text.toString().length > 0 && id_dietno_text2.text.toString().length > 0) {

            // totalValue = id_dietno_text1.text.toString() + "." + id_dietno_text2.text.toString()
            totalValue =
                id_dietno_text1.text.toString() + "." + id_dietno_text2.text.toString()
            value = java.lang.Float.parseFloat(totalValue)
            Log.d("DietFactoryActivity", "Value" + value)
            if (value >= 0.0f && value <= 0.1f) {
                id_fatburn_text.text = "NOT IN KETOSIS"
                actualvalue = 0
            } else if (value.toFloat() >= 0.2 && value
                <= 0.5f
            ) {
                id_fatburn_text.text = "KETOSIS BEGIN"
                actualvalue = 1
            } else if (value >= 0.6f &&
                value <= 2.0f
            ) {
                id_fatburn_text.text = "FULL STATE OF KETOSIS"
                actualvalue = 2
            } else if (value >= 2.1f &&
                value
                <= 3.0f
            ) {
                id_fatburn_text.text = "FULL STATE OF KETOSIS"
                actualvalue = 3
            } else if (value >= 3.1f &&
                value
                <= 5.0f
            ) {
                id_fatburn_text.text = "STARVATION"
                actualvalue = 4
            } else if (value >= 5.1f &&
                value
                <= 7.0f
            ) {
                id_fatburn_text.text = "MEDICAL HELP"
                actualvalue = 5
            } else if (value >= 7.1f && value <= 7.9f) {
                id_fatburn_text.text = "DANGER"
                actualvalue = 6


            }


            bar_chart_vertical.clearAll()
            for (i in 0..actualvalue) {
                val barChartModel = BarChartModel()
                barChartModel.barValue = (i + 2) * 10
                bar_chart_vertical.addBar(barChartModel)


            }


            image_ff.visibility = View.GONE
            bar_chart_vertical.visibility = View.VISIBLE


        } else {
            image_ff.visibility = View.VISIBLE
            bar_chart_vertical.visibility = View.GONE
            id_fatburn_text.text = ""
            bar_chart_vertical.clearAll()
        }
    }

    private fun startClock() {
        mActive = true
        mHandler!!.post(mRunnableclock)


    }

    var mRunnableclock: Runnable = object : Runnable {

        override fun run() {
            if (mActive) {

                id_time.text = getTime()

                mHandler!!.postDelayed(this, 1000)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()

        mHandler!!.removeCallbacks(mRunnableclock)


    }

    private fun getTime(): String {
        return sdf.format(Date(System.currentTimeMillis()))
    }


    private fun onTodayAlarmClicked() {
        startActivity(Intent(this, TodayAlarmActivity::class.java))
    }

    private fun onStopWatchClicked() {
        startActivity(Intent(this, ChewDataActivity::class.java))
    }

    fun onSelectDietClicked() {
        if (formValidate()) {
            var intent = Intent(this, LoginActivity::class.java)
            intent.putExtra(Constants.Dietno, value)
            startActivity(intent)


            //  this.let { ContextCompat.startActivity(it, intent, null) }


        }
    }

    fun onNotificatioinClicked() {

        startActivity(Intent(this, RemindersActivity::class.java))


    }

    fun formValidate(): Boolean {

        if (id_dietno_text2.text.length < 0) {
            Toast.makeText(this, "Please enter ketosis No.", Toast.LENGTH_SHORT).show()
            return false
        } else if (value.toInt() < 1) {
            Toast.makeText(this, "Should be greater the 1", Toast.LENGTH_SHORT).show()
            return false
        } else if (value.toInt() > 7) {
            Toast.makeText(this, "Should less  then 7", Toast.LENGTH_SHORT).show()
            return false
        } else {
            return true
        }


    }
}


