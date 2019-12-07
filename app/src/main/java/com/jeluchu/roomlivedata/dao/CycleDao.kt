package com.jeluchu.roomlivedata.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jeluchu.roomlivedata.model.CycleData
import com.jeluchu.roomlivedata.model.Notification

@Dao
interface CycleDao {

    // MOSTRAR DATOS Y ORDENARLOS
    @Query("SELECT * from cycle_table ORDER BY id ASC")
    fun getAllCycleData(): LiveData<List<CycleData>>

    // AÑADIR DATOS
    @Insert
    fun insert(CycleData: CycleData)

    // ELIMINAR ALL DATA
    @Query("DELETE FROM word_table")
    fun deleteAll()

    // ACTUALIZAR DATOS
    //Sin Query
    @Update
    fun update(CycleData: CycleData)
 
    //Con Query
    @Query("UPDATE word_table SET word = :word WHERE id == :id")
    fun updateItem(word: String, id: Int)

    // BORRAR ITEM
    @Delete
    fun deleteWord(CycleData: CycleData)
}