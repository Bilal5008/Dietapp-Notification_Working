package com.jeluchu.roomlivedata.activities

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
import com.jeluchu.roomlivedata.R
import com.jeluchu.roomlivedata.adapters.CycleDataListAdapter
import com.jeluchu.roomlivedata.model.CycleData
import com.jeluchu.roomlivedata.utils.Constants
import com.jeluchu.roomlivedata.utils.SharedPreferenceHelper
import com.jeluchu.roomlivedata.viewmodels.CycleDataViewModel
import kotlinx.android.synthetic.main.activity_chew_data.*
import kotlinx.android.synthetic.main.activity_main.fab
import kotlinx.android.synthetic.main.content_main.recyclerview


class ChewDataActivity : AppCompatActivity() {

    var myCycleData: CycleData? = null
    private val newWordActivityRequestCode = 1
    private lateinit var cycleDataViewModel: CycleDataViewModel
    var sharedPreferenceHelper: SharedPreferenceHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        sharedPreferenceHelper = SharedPreferenceHelper.instance


        if (sharedPreferenceHelper!!.getBoolean(Constants.Theme)) {
            setTheme(R.style.darkTheme)

        } else {
            setTheme(R.style.AppTheme)
        }



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chew_data)

        getSupportActionBar()!!.hide()


        checkVisibilty()
        id_firsTime.setOnClickListener(View.OnClickListener {
            sharedPreferenceHelper!!.setBooleanValue(
                Constants.chewDataFirstTime,
                true
            )
            checkVisibilty()
        })


        val adapter = CycleDataListAdapter(this)
        recyclerview.adapter = adapter


        recyclerview.layoutManager = LinearLayoutManager(this)

        cycleDataViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(CycleDataViewModel::class.java)

        cycleDataViewModel.allCycleData.observe(this, Observer { words ->


            adapter.setCycleData(words)


        })

        fab.setOnClickListener {
            val intent = Intent(this@ChewDataActivity, StopActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }

        // OPTIONS FOR SWIPE RECYCLERVIEW
        val helper = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    myCycleData = adapter.getWordAtPosition(position)

                    if (direction == ItemTouchHelper.LEFT) {
                        cycleDataViewModel.deleteWord(myCycleData!!)
                        cycleDataViewModel.allCycleData

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
        if (!sharedPreferenceHelper!!.getBoolean(Constants.chewDataFirstTime)) {
            id_firsTime.visibility = View.VISIBLE
            fab.visibility = View.GONE
        } else {
            id_firsTime.visibility = View.GONE
            fab.visibility = View.VISIBLE

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

        if (requestCode == newWordActivityRequestCode && intentData != null) {
            // intentData?.let { data ->

            val cycleData = CycleData(
                0,
                intentData.getIntExtra("cycleno", 0)

                , intentData.getStringExtra("time")

                , intentData.getStringExtra("cycleinterval")


            )

            cycleDataViewModel.insert(cycleData)


            //  }
        } else {
            Toast.makeText(applicationContext, R.string.empty_not_saved, Toast.LENGTH_LONG).show()
        }
    }


}


