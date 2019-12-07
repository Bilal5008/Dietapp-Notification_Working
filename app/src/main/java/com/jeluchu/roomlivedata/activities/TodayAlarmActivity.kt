package com.jeluchu.roomlivedata.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jeluchu.roomlivedata.R
import com.jeluchu.roomlivedata.adapters.TodayAlarmListAdapter
import com.jeluchu.roomlivedata.model.Notification
import com.jeluchu.roomlivedata.utils.Constants
import com.jeluchu.roomlivedata.utils.SharedPreferenceHelper
import com.jeluchu.roomlivedata.viewmodels.WordViewModel
import kotlinx.android.synthetic.main.content_main.*
import java.util.*


class TodayAlarmActivity : AppCompatActivity() {
    val notificationList = ArrayList<Notification>()
    private lateinit var wordViewModel: WordViewModel
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
        setContentView(R.layout.activity_today_alarm)
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_WEEK)
        val adapter = TodayAlarmListAdapter(this)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)


        wordViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(WordViewModel::class.java)

        wordViewModel.allWords.observe(this, Observer { words ->


            for (i in 0..words.size - 1) {


                when (day) {
                    Calendar.SATURDAY -> {

                        if (words[i].saturday) {
                            notificationList.add(words[i])

                        }
                    }

                    Calendar.SUNDAY -> {
                        if (words[i].sunday) {
                            notificationList.add(words[i])

                        }
                    }
                    Calendar.MONDAY -> {
                        if (words[i].monday) {
                            notificationList.add(words[i])

                        }
                    }
                    Calendar.TUESDAY -> {
                        if (words[i].tuesday) {
                            notificationList.add(words[i])

                        }
                    }
                    Calendar.WEDNESDAY -> {
                        if (words[i].wednesday) {
                            notificationList.add(words[i])

                        }
                    }
                    Calendar.THURSDAY -> {
                        if (words[i].thursday) {
                            notificationList.add(words[i])

                        }
                    }
                    Calendar.FRIDAY -> {
                        if (words[i].friday) {
                            notificationList.add(words[i])

                        }
                    }
                }
            }

            adapter.setWords(notificationList)


        })
    }
}
