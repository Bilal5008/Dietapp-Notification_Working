package com.jeluchu.roomlivedata.activities

import android.content.Intent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.jeluchu.roomlivedata.R
import com.jeluchu.roomlivedata.utils.Constants
import com.jeluchu.roomlivedata.utils.SharedPreferenceHelper

import kotlinx.android.synthetic.main.activity_diet.*


class DietActivity : AppCompatActivity() {
    var dietno: String? = null

    var sharedPreferenceHelper: SharedPreferenceHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        sharedPreferenceHelper = SharedPreferenceHelper.instance


        if (sharedPreferenceHelper!!.getBoolean(Constants.Theme)) {
            setTheme(R.style.darkTheme)

        } else {
            setTheme(R.style.AppTheme)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet)
        dietno = intent.getStringExtra(Constants.Dietno)
        setTitle("Diet " + dietno)

        id_meal_1.setOnClickListener { onMealPlanFirstClicked() }
        id_meal_2.setOnClickListener { onMealPlanSecondClicked() }
        id_meal_3.setOnClickListener { onMealPlanThirdClicked() }
    }

    private fun onMealPlanThirdClicked() {
        var intent = Intent(this, MealActivity::class.java)
        intent.putExtra("Dietno", dietno)
        intent.putExtra("MealPlan", "3")
        startActivity(intent)


     //   this.let { ContextCompat.startActivity(it, intent, null) }
    }

    private fun onMealPlanSecondClicked() {
        var intent = Intent(this, MealActivity::class.java)
        intent.putExtra("Dietno", dietno)
        intent.putExtra("MealPlan", "2")
        startActivity(intent)

      //  this.let { ContextCompat.startActivity(it, intent, null) }
    }

    private fun onMealPlanFirstClicked() {
        var intent = Intent(this, MealActivity::class.java)
        intent.putExtra("Dietno", dietno)
        intent.putExtra("MealPlan", "1")
        startActivity(intent)

   //     this.let { ContextCompat.startActivity(it, intent, null) }
    }
}
