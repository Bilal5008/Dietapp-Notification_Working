package com.jeluchu.roomlivedata.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "word_table")
class Notification(
    @PrimaryKey(autoGenerate = true) val id: Int,@ColumnInfo(name = "notiId") var notiId: Int, @ColumnInfo(name = "word") var word: Long, @ColumnInfo(name = "status") var status: Boolean, @ColumnInfo(
        name = "vibration"
    ) var vibration: Boolean, @ColumnInfo(name = "sound") var sound: Boolean, @ColumnInfo(name = "alarmType") var alarmType: String, @ColumnInfo(
        name = "monday"
    ) var monday: Boolean, @ColumnInfo(name = "tuesday") var tuesday: Boolean, @ColumnInfo(name = "wednesday") var wednesday: Boolean, @ColumnInfo(
        name = "thursday"
    ) var thursday: Boolean, @ColumnInfo(name = "friday") var friday: Boolean, @ColumnInfo(name = "saturday") var saturday: Boolean, @ColumnInfo(
        name = "sunday"
    ) var sunday: Boolean
) : Parcelable
