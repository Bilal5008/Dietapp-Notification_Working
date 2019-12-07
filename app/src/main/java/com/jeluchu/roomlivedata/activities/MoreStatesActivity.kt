package com.jeluchu.roomlivedata.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.jeluchu.roomlivedata.R
import com.jeluchu.roomlivedata.utils.Constants
import com.jeluchu.roomlivedata.utils.SharedPreferenceHelper
import kotlinx.android.synthetic.main.activity_chew.*
import kotlinx.android.synthetic.main.activity_more_states.*
import java.util.ArrayList

class MoreStatesActivity : AppCompatActivity() {
    var sharedPreferenceHelper: SharedPreferenceHelper? = null
    internal var ListElementsArrayList: MutableList<String>? = ArrayList<String>()

    internal var adapter: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {


        sharedPreferenceHelper = SharedPreferenceHelper.instance
        supportActionBar!!.hide()

        if (sharedPreferenceHelper!!.getBoolean(Constants.Theme) == true) {
            setTheme(R.style.darkTheme)

        } else {
            setTheme(R.style.AppTheme)
        }


        super.onCreate(savedInstanceState)
        setContentView(
            R.layout.activity_more_states
        )


        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            ListElementsArrayList as ArrayList<String>
        )



        listview1!!.adapter = adapter
        ListElementsArrayList!!.addAll(intent.getStringArrayExtra(Constants.listView))
        adapter!!.notifyDataSetChanged()

    }
}
