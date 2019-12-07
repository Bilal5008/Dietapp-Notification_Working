package com.jeluchu.roomlivedata.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity

import com.jeluchu.roomlivedata.R
import com.jeluchu.roomlivedata.utils.Constants
import com.jeluchu.roomlivedata.utils.SharedPreferenceHelper
import kotlinx.android.synthetic.main.activity_meal.*


class MealActivity : AppCompatActivity() {
    var mealPlan: String? = null
    var dietno: String? = null
    var categories = arrayOf<String>()

    var sharedPreferenceHelper: SharedPreferenceHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        sharedPreferenceHelper = SharedPreferenceHelper.instance


        if (sharedPreferenceHelper!!.getBoolean(Constants.Theme)) {
            setTheme(R.style.darkTheme)

        } else {
            setTheme(R.style.AppTheme)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal)
        mealPlan = intent.getStringExtra("MealPlan")
        dietno = intent.getStringExtra("Dietno")

        if (mealPlan.toString().toInt() == 1 && dietno.toString().toInt() == 1) {
            categories = getResources().getStringArray(R.array.meal_1_1)

            val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1, categories)

            list.setAdapter(adapter)


        }
        if (mealPlan.toString().toInt() == 2 && dietno.toString().toInt() == 1) {
            categories = getResources().getStringArray(R.array.meal_1_2)

            val adapter =
                ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1, categories)

            list.setAdapter(adapter)


        }


    }
}
