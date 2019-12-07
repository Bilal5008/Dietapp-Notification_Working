package com.jeluchu.roomlivedata.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jeluchu.roomlivedata.R
import com.jeluchu.roomlivedata.model.CycleData
import com.jeluchu.roomlivedata.model.Notification
import android.R.string


class CycleDataListAdapter internal constructor(context: Context) :
    androidx.recyclerview.widget.RecyclerView.Adapter<CycleDataListAdapter.WordViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var words = emptyList<CycleData>() // Cached copy of words


    inner class WordViewHolder(itemView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {


        val time: TextView = itemView.findViewById(R.id.id_time)
        val cycleNo: TextView = itemView.findViewById(R.id.id_cycleno)
        val cycleInterval: TextView = itemView.findViewById(R.id.id_cycleinterval)
        val dayTime: TextView = itemView.findViewById(R.id.id_eat_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.table_list_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        // val current = words[position]

        val rowViewHolder = holder as WordViewHolder

        val rowPos = rowViewHolder.adapterPosition


//        if (rowPos == 0) {
//            // Header Cells. Main Headings appear here
//            rowViewHolder.time.setBackgroundResource(R.drawable.table_header_cell_bg)
//            rowViewHolder.cycleNo.setBackgroundResource(R.drawable.table_header_cell_bg)
//            rowViewHolder.cycleInterval.setBackgroundResource(R.drawable.table_header_cell_bg)
//            rowViewHolder.dayTime.setBackgroundResource(R.drawable.table_header_cell_bg)
//
//            rowViewHolder.time.setText("Rank")
//            rowViewHolder.cycleNo.setText("Name")
//            rowViewHolder.cycleInterval.setText("Year")
//            rowViewHolder.dayTime.setText("Budget (in Millions)")
//        } else {
        val modal = words.get(rowPos)

        // Content Cells. Content appear here
        rowViewHolder.time.setBackgroundResource(R.drawable.table_content_cell_bg)
        rowViewHolder.cycleNo.setBackgroundResource(R.drawable.table_content_cell_bg)
        rowViewHolder.cycleInterval.setBackgroundResource(R.drawable.table_content_cell_bg)
        rowViewHolder.dayTime.setBackgroundResource(R.drawable.table_content_cell_bg)

        val hours = modal.tiem.split(":").toTypedArray()
        rowViewHolder.time.text = modal.tiem

        if (Integer.parseInt(hours[0]) >= 1 && Integer.parseInt(hours[0]) <= 12) {
            // Toast.makeText(this, "Good Morning", Toast.LENGTH_SHORT).show();
            rowViewHolder.dayTime.text = "BreakFast"
        } else if (Integer.parseInt(hours[0]) >= 12 && Integer.parseInt(hours[0]) <= 16) {
            rowViewHolder.dayTime.text = "Lunch"
            //  Toast.makeText(this, "Good Afternoon", Toast.LENGTH_SHORT).show();
        }else if (Integer.parseInt(hours[0]) >= 16 && Integer.parseInt(hours[0]) <= 24) {

            rowViewHolder.dayTime.text = "Supper"
            //Toast.makeText(this, "Good Night", Toast.LENGTH_SHORT).show();
        }
        rowViewHolder.cycleNo.text = modal.cycleno.toString()
        rowViewHolder.cycleInterval.text = modal.cycleinterval
//        }


    }

    internal fun setCycleData(cycleData: List<CycleData>) {
        this.words = cycleData
        notifyDataSetChanged()
    }

    fun getWordAtPosition(position: Int): CycleData {
        return words[position]
    }


    override fun getItemCount() = words.size
}