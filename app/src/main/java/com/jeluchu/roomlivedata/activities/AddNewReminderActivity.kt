package com.jeluchu.roomlivedata.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alarms.Utils
import com.jeluchu.roomlivedata.R
import com.jeluchu.roomlivedata.adapters.AlarmListAdapter
import com.jeluchu.roomlivedata.model.Notification
import com.jeluchu.roomlivedata.utils.Constants
import com.jeluchu.roomlivedata.utils.SharedPreferenceHelper
import com.jeluchu.roomlivedata.viewmodels.WordViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.belief_item_layout.view.*
import kotlinx.android.synthetic.main.content_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AddNewReminderActivity : AppCompatActivity() {
    var alarmType: String? = null
    var myNotification: Notification? = null
    private val newWordActivityRequestCode = 1

    private lateinit var wordViewModel: WordViewModel
    val notificationList = ArrayList<Notification>()


    var daysArray = ArrayList<Boolean>()

    var sharedPreferenceHelper: SharedPreferenceHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {


        sharedPreferenceHelper = SharedPreferenceHelper.instance

        if (sharedPreferenceHelper!!.getBoolean(Constants.Theme)) {
            setTheme(R.style.darkTheme)
        } else {
            setTheme(R.style.AppTheme)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkVisibilty()



        id_firsTime.setOnClickListener(View.OnClickListener {
            sharedPreferenceHelper!!.setBooleanValue(Constants.AddNewReminderActivity, true)
            checkVisibilty()
        })




        getSupportActionBar()!!.hide()
        alarmType = intent.getStringExtra(Constants.AlarmType)
        setAlarmHeader(alarmType)

        for (i in 1..7) {
            daysArray.add(true)
        }

        val adapter = AlarmListAdapter(this)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)

        wordViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(WordViewModel::class.java)


        wordViewModel.allWords.observe(this, Observer { words ->
            notificationList.clear()


            for (i in 0..words.size - 1) {
                if (words[i].alarmType.equals(alarmType)) {
                    notificationList.add(words[i])
                }
            }
            // setAlarm(notificationList)
            adapter.setWords(notificationList)


        })

        fab.setOnClickListener {
            val intent = Intent(this@AddNewReminderActivity, NewReminderActivity::class.java)
            intent.putExtra(Constants.AlarmType, alarmType)
            startActivityForResult(intent, newWordActivityRequestCode)
        }

        // OPTIONS FOR SWIPE RECYCLERVIEW
        val helper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {


                return super.getMovementFlags(recyclerView, viewHolder)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                val position = viewHolder.adapterPosition
                myNotification = adapter.getWordAtPosition(position)

                if (direction == ItemTouchHelper.LEFT) {

                    //  deleteByIdAlarms(myNotification!!.id)
                    deleteByIdAlarms(myNotification!!)
                    wordViewModel.deleteWord(myNotification!!)
                    wordViewModel.allWords
                    val intent = intent
                    finish()
                    startActivity(intent)

                }
            }

            // ACTION SWIPE RECYCLERVIEW
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val icon: Bitmap

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    val itemView = viewHolder.itemView

                    val height = itemView.bottom.toFloat() - itemView.top.toFloat()
                    val width = height / 3

                    val p = Paint()
                    if (dX > 0) {

                        p.color = Color.parseColor("#1A7DCB")
                        val background = RectF(
                            itemView.left.toFloat(),
                            itemView.top.toFloat(),
                            dX,
                            itemView.bottom.toFloat()
                        )
                        c.drawRect(background, p)

                        val left = itemView.left.toFloat() + width
                        val top = itemView.top.toFloat() + width
                        val right = itemView.left.toFloat() + 2 * width
                        val bottom = itemView.bottom.toFloat() - width

                        icon = getBitmapFromVectorDrawable(
                            applicationContext,
                            R.drawable.ic_edit
                        )
                        val iconDest = RectF(left, top, right, bottom)

                        c.drawBitmap(icon, null, iconDest, p)

                    } else {

                        p.color = Color.parseColor("#CB1A1A")

                        val background = RectF(
                            itemView.right.toFloat() + dX,
                            itemView.top.toFloat(),
                            itemView.right.toFloat(),
                            itemView.bottom.toFloat()
                        )
                        c.drawRect(background, p)


                        icon = getBitmapFromVectorDrawable(
                            applicationContext,
                            R.drawable.ic_delete_one
                        )

                        val left = itemView.right.toFloat() - 2 * width
                        val top = itemView.top.toFloat() + width
                        val right = itemView.right.toFloat() - width
                        val bottom = itemView.bottom.toFloat() - width
                        val iconDest = RectF(left, top, right, bottom)

                        c.drawBitmap(icon, null, iconDest, p)
                    }

                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }
            }

        })

        helper.attachToRecyclerView(recyclerview)

    }

    private fun checkVisibilty() {

        if (!sharedPreferenceHelper!!.getBoolean(Constants.AddNewReminderActivity)) {
            id_firsTime.visibility = View.VISIBLE
            fab.visibility = View.GONE
        } else {
            id_firsTime.visibility = View.GONE
            fab.visibility = View.VISIBLE

        }


    }

    private fun setAlarmHeader(alarmType: String?) {
        if (alarmType!!.equals(Constants.Blief)) {
            id_blief.visibility = View.VISIBLE
            id_blief.id_next_reminder.visibility = View.GONE
            id_blief.edit_alaram.visibility = View.GONE

        } else if (alarmType.equals(Constants.Breath)) {
            id_breath_air.visibility = View.VISIBLE
            id_breath_air.id_next_reminder.visibility = View.GONE
            id_breath_air.edit_alaram.visibility = View.GONE

        } else if (alarmType.equals(Constants.Control)) {

            id_control.visibility = View.VISIBLE
            id_control.id_next_reminder.visibility = View.GONE
            id_control.edit_alaram.visibility = View.GONE
        } else if (alarmType.equals(Constants.EatHealthy)) {
            id_eat_healthy.visibility = View.VISIBLE
            id_eat_healthy.id_next_reminder.visibility = View.GONE
            id_eat_healthy.edit_alaram.visibility = View.GONE
        } else if (alarmType.equals(Constants.GoOutSun)) {
            id_go_out.visibility = View.VISIBLE
            id_go_out.id_next_reminder.visibility = View.GONE
            id_go_out.edit_alaram.visibility = View.GONE
        } else if (alarmType.equals(Constants.Excercise)) {
            id_excercise.visibility = View.VISIBLE
            id_excercise.id_next_reminder.visibility = View.GONE
            id_excercise.edit_alaram.visibility = View.GONE

        } else if (alarmType.equals(Constants.Tech)) {
            id_tech.visibility = View.VISIBLE
            id_tech.id_next_reminder.visibility = View.GONE
            id_tech.edit_alaram.visibility = View.GONE
        } else if (alarmType.equals(Constants.Water)) {

            id_water.visibility = View.VISIBLE
            id_water.id_next_reminder.visibility = View.GONE
            id_water.edit_alaram.visibility = View.GONE
        } else if (alarmType.equals(Constants.Sleep)) {
            id_sleep.visibility = View.VISIBLE
            id_sleep.id_next_reminder.visibility = View.GONE
            id_sleep.edit_alaram.visibility = View.GONE
        }

    }


    private fun deleteByIdAlarms(notification: Notification) {


        for (i in 0..notificationList.size - 1) {

            if (notification.id == notificationList[i].id - 1) {

                Utils.cancelAlarm(this, notificationList[i])

                Toast.makeText(
                    applicationContext, "Deleted",
                    Toast.LENGTH_LONG
                ).show()

            }
        }

    }

    fun getBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap {
        var drawable = ContextCompat.getDrawable(context, drawableId)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                drawable = DrawableCompat.wrap(drawable!!).mutate()
            }
        }

        val bitmap = Bitmap.createBitmap(
            drawable!!.intrinsicWidth,
            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {

            val notification = Notification(
                0,
                createID(),
                intentData!!.getLongExtra("alarmTime", 0)
                , true
                , intentData!!.getBooleanExtra("vibration", true)
                , intentData!!.getBooleanExtra("sound", true)
                , alarmType.toString()
                , intentData!!.getBooleanExtra("monday", true)
                , intentData!!.getBooleanExtra("tuesday", true)
                , intentData!!.getBooleanExtra("wednesday", true)
                , intentData!!.getBooleanExtra("thursday", true)
                , intentData!!.getBooleanExtra("friday", true)
                , intentData!!.getBooleanExtra("saturday", true)
                , intentData!!.getBooleanExtra("sunday", true)


            )
            if (!findDuplication(notification)) {
                wordViewModel.insert(notification)
                setAlarm(notification)
            } else {
                Toast.makeText(
                    applicationContext,
                    "" + notification.word + " " + "Already Alarm Set for ",
                    Toast.LENGTH_LONG
                ).show()
            }


            //           intentData?.let { data ->
//                val word = alarmType?.let {
//                    Notification(
//                        createID(),
//                        data.getStringExtra(NewReminderActivity.EXTRA_REPLY)
//                        , true
//                        , data.getBooleanExtra("vibration", true)
//                        , data.getBooleanExtra("sound", true)
//                        , it
//                        , data.getBooleanExtra("monday", true)
//                        , data.getBooleanExtra("tuesday", true)
//                        , data.getBooleanExtra("wednesday", true)
//                        , data.getBooleanExtra("thursday", true)
//                        , data.getBooleanExtra("friday", true)
//                        , data.getBooleanExtra("saturday", true)
//                        , data.getBooleanExtra("sunday", true)
//
//
//                    )
//                }
//            word?.let {
//
//                if (!findDuplication(it)) {
//                    wordViewModel.insert(it)
//                    setAlarm(it)
//                } else {
//                    Toast.makeText(
//                        applicationContext,
//                        "" + it.word + " " + "Already Alarm Set for ",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            }
            // word?.let { startAlarm(it) }
            val intent = intent
            finish()
            startActivity(intent)


            //    }
        }


//
//            intentData?.let { data ->
//                val word = myNotification?.id?.let {
//                    alarmType?.let { it1 ->
//                        Notification(
//                            it,
//                            data.getStringExtra(NewReminderActivity.EXTRA_REPLY),
//                            true,
//                            data.getBooleanExtra("vibration", true),
//                            data.getBooleanExtra("sound", true),
//                            it1,
//                            data.getBooleanExtra("monday", true)
//                            ,
//                            data.getBooleanExtra("tuesday", true)
//                            ,
//                            data.getBooleanExtra("wednesday", true)
//                            ,
//                            data.getBooleanExtra("thursday", true)
//                            ,
//                            data.getBooleanExtra("friday", true)
//                            ,
//                            data.getBooleanExtra("saturday", true)
//                            ,
//                            data.getBooleanExtra("sunday", true)
//
//                        )
//                    }
//                }
//                word?.let {
//                    wordViewModel.allWords
//                    if (!findDuplication(it)) {
//                        deleteByIdAlarms(it.id)
//                        wordViewModel.updateWord(it)
//                        setAlarm(it)
//                    } else {
//                        Toast.makeText(
//                            applicationContext,
//                            "" + it.word + " " + "Already Alarm Set for " + " " + it.alarmType,
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//                }
//
//            }


        else {
            val intent = intent
            finish()
            startActivity(intent)
        }
    }

    private fun findDuplication(it: Notification): Boolean {

        // wordViewModel.allWords.value.


        for (i in 0..wordViewModel.allWords.value!!.size - 1) {
            val dateString = SimpleDateFormat("h:mm").format(Date(it.word))
            val dateStringvalue =
                SimpleDateFormat("h:mm").format(Date(wordViewModel.allWords.value!![i].word))
            if (dateStringvalue.equals(dateString)) {
                return true
            }

        }

        return false

    }

    fun createID(): Int {
        val now = Date()
        return Integer.parseInt(SimpleDateFormat("ddHHmmss", Locale.US).format(now))
    }


//    private fun startAlarm(notification: Notification) {
//
//
//        var intent = Intent(this, AlarmReciever::class.java)
//        intent.putExtra("Title", notification.word)
//        intent.putExtra("Text", "new notification" + 1)
//        intent.putExtra("vibration", notification.vibration)
//        intent.putExtra("sound", notification.sound)
//        intent.putExtra("id", notification.id)
//        intent.putExtra("monday", notification.monday)
//        intent.putExtra("tuesday", notification.tuesday)
//        intent.putExtra("wednesday", notification.wednesday)
//        intent.putExtra("thursday", notification.thursday)
//        intent.putExtra("friday", notification.friday)
//        intent.putExtra("saturday", notification.saturday)
//        intent.putExtra("sunday", notification.sunday)
//        var res = notification.word.split(":")
//
//        val calendar: Calendar = Calendar.getInstance().apply {
//            timeInMillis = System.currentTimeMillis()
//            set(Calendar.HOUR_OF_DAY, res[0].toInt())
//            set(Calendar.MINUTE, res[1].toInt())
//        }
//
//        var pendingIntent: PendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
//
////        if (calendar.before(Calendar.getInstance())) {
////            calendar.add(Calendar.DATE, 1)
//
////        pendingintent.add(
////            PendingIntent.getBroadcast(
////                this,
////                notification.id,
////                intent,
////                PendingIntent.FLAG_UPDATE_CURRENT
////            )
////        )
//////            manager?.setRepeating(
////                AlarmManager.RTC_WAKEUP,
////                calendar.timeInMillis,
////                AlarmManager.INTERVAL_DAY,
////                pendingintent[i]
////            )
//
//
//        manager?.setExact(
//            AlarmManager.RTC_WAKEUP,
//            calendar.timeInMillis,
//            pendingIntent
//        )
//
//        // }
//    }


    private fun setAlarm(notificationList: Notification) {
        /* Retrieve a PendingIntent that will perform a broadcast */
        Toast.makeText(this, "Alarm has been set!", Toast.LENGTH_LONG).show()
        Utils.setAlarmNew(this, notificationList, notificationList.notiId)
    }


}


