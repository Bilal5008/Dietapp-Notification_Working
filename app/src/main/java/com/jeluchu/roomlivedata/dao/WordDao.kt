package com.jeluchu.roomlivedata.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jeluchu.roomlivedata.model.Notification

@Dao
interface WordDao {

    // MOSTRAR DATOS Y ORDENARLOS
    @Query("SELECT * from word_table ORDER BY word ASC")
    fun getAllWords(): LiveData<List<Notification>>

    // AÑADIR DATOS
    @Insert
    fun insert(notification: Notification)

    // ELIMINAR ALL DATA
    @Query("DELETE FROM word_table")
    fun deleteAll()

    // ACTUALIZAR DATOS
    //Sin Query
    @Update
    fun update(notification: Notification)
 
    //Con Query
    @Query("UPDATE word_table SET word = :word WHERE id == :id")
    fun updateItem(word: String, id: Int)

    // BORRAR ITEM
    @Delete
    fun deleteWord(notification: Notification)
}