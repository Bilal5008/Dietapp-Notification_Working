package com.jeluchu.roomlivedata.adapters

import android.content.Context
import android.content.Intent
import android.provider.SyncStateContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import com.example.alarms.Utils
import com.jeluchu.roomlivedata.Application.Companion.context
import com.jeluchu.roomlivedata.R
import com.jeluchu.roomlivedata.activities.AddNewReminderActivity
import com.jeluchu.roomlivedata.interfaces.TodayItemClicked
import com.jeluchu.roomlivedata.model.Notification
import com.jeluchu.roomlivedata.utils.Constants
import java.text.SimpleDateFormat
import java.util.*

class TodayAlarmListAdapter internal constructor(context: Context) :
    androidx.recyclerview.widget.RecyclerView.Adapter<TodayAlarmListAdapter.WordViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var words = emptyList<Notification>() // Cached copy of words

    private var todayItemClicked: TodayItemClicked? = null

    inner class WordViewHolder(itemView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.ar_time)
        val category: TextView = itemView.findViewById(R.id.id_reminder_title)
        val categoryimage: ImageView = itemView.findViewById(R.id.image)
        val layoutClicked: ConstraintLayout = itemView.findViewById(R.id.layout_click)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.notification_item, parent, false)
        this.todayItemClicked = TodayItemClicked {}

        return WordViewHolder(itemView)


    }


    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = words[position]
        holder.wordItemView.text = SimpleDateFormat("hh:mm aa").format(Date(current.word))


        holder.category.text = current.alarmType
        holder.categoryimage.visibility = View.GONE
        holder.layoutClicked.setOnClickListener {

            var intent = Intent(context, AddNewReminderActivity::class.java)
            intent.putExtra(Constants.AlarmType, current.alarmType)

            context?.let { it1 -> startActivity(it1, intent, null) }


//
//            if (todayItemClicked != null) {
//                todayItemClicked!!.onRowClick(current)
//            }
        }


    }

    internal fun setWords(notifications: List<Notification>) {
        this.words = notifications
        notifyDataSetChanged()
    }


    override fun getItemCount() = words.size
}