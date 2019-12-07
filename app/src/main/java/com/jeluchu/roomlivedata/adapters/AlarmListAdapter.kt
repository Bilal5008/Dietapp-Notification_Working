package com.jeluchu.roomlivedata.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.jeluchu.roomlivedata.R
import com.jeluchu.roomlivedata.model.Notification
import com.jeluchu.roomlivedata.utils.Constants
import java.text.SimpleDateFormat
import java.util.*
import java.util.Collections.emptyList

class AlarmListAdapter internal constructor(context: Context) :
    androidx.recyclerview.widget.RecyclerView.Adapter<AlarmListAdapter.WordViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var words = emptyList<Notification>() // Cached copy of words

    inner class WordViewHolder(itemView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.ar_time)
        val category: TextView = itemView.findViewById(R.id.id_reminder_title)
        val m: TextView = itemView.findViewById(R.id.id_day_m)
        val t: TextView = itemView.findViewById(R.id.id_day_t)
        val w: TextView = itemView.findViewById(R.id.id_day_w)
        val th: TextView = itemView.findViewById(R.id.id_day_th)
        val f: TextView = itemView.findViewById(R.id.id_day_f)
        val s: TextView = itemView.findViewById(R.id.id_day_s)
        val sun: TextView = itemView.findViewById(R.id.id_day_sun)
        val image: ImageView = itemView.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.notification_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = words[position]
        val dateString = SimpleDateFormat("hh:mm aa").format(Date(current.word))
        holder.wordItemView.text = dateString.toString()
        holder.category.text = current.alarmType


        setAlarmHeader(current.alarmType, holder)





        if (current.monday) {
            holder.m.setTextColor(Color.BLACK)

        }
        if (current.tuesday) {
            holder.t.setTextColor(Color.BLACK)

        }
        if (current.wednesday) {
            holder.w.setTextColor(Color.BLACK)

        }
        if (current.thursday) {
            holder.th.setTextColor(Color.BLACK)

        }
        if (current.friday) {
            holder.f.setTextColor(Color.BLACK)

        }
        if (current.saturday) {
            holder.s.setTextColor(Color.BLACK)

        }
        if (current.sunday) {
            holder.sun.setTextColor(Color.BLACK)

        }


    }

    internal fun setWords(notifications: List<Notification>) {
        this.words = notifications
        notifyDataSetChanged()
    }

    fun getWordAtPosition(position: Int): Notification {
        return words[position]
    }


    override fun getItemCount() = words.size

    private fun setAlarmHeader(
        alarmType: String?,
        holder: WordViewHolder
    ) {
        if (alarmType!!.equals(Constants.Blief)) {
            holder.image.setImageResource(R.drawable.beliefg)

        } else if (alarmType.equals(Constants.Breath)) {
            holder.image.setImageResource(R.drawable.airg)
        } else if (alarmType.equals(Constants.Control)) {
            holder.image.setImageResource(R.drawable.controlg)
        } else if (alarmType.equals(Constants.EatHealthy)) {
            holder.image.setImageResource(R.drawable.dietg)
        } else if (alarmType.equals(Constants.GoOutSun)) {
            holder.image.setImageResource(R.drawable.sung)
        } else if (alarmType.equals(Constants.Excercise)) {
            holder.image.setImageResource(R.drawable.fitnessg)

        } else if (alarmType.equals(Constants.Tech)) {
            holder.image.setImageResource(R.drawable.techg)
        } else if (alarmType.equals(Constants.Water)) {
            holder.image.setImageResource(R.drawable.waterg)
        } else if (alarmType.equals(Constants.Sleep)) {
            holder.image.setImageResource(R.drawable.sleepg)

        }

    }
}