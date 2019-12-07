package com.jeluchu.roomlivedata.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cycle_table")
class CycleData(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "cycleno") var cycleno: Int,
    @ColumnInfo(name = "time") var tiem: String,
    @ColumnInfo(name = "cycleinterval") var cycleinterval: String
)
