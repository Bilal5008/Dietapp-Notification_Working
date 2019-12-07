package com.jeluchu.roomlivedata.respository

import androidx.lifecycle.LiveData
import androidx.annotation.WorkerThread
import android.os.AsyncTask
import com.jeluchu.roomlivedata.dao.CycleDao
import com.jeluchu.roomlivedata.dao.WordDao
import com.jeluchu.roomlivedata.model.CycleData
import com.jeluchu.roomlivedata.model.Notification


class CycleDataRepository(private val CycleDao: CycleDao) {

    val allCycleData: LiveData<List<CycleData>> = CycleDao.getAllCycleData()

    @WorkerThread
    fun insert(CycleData: CycleData) {
        CycleDao.insert(CycleData)
    }


    /* --------------- BORRAR TODOS LOS DATOS -------------- */

    fun deleteAll() {
        deleteAllWordsAsyncTask(CycleDao).execute()
    }

    private class deleteAllWordsAsyncTask internal constructor(private val mAsyncTaskDao: CycleDao) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            mAsyncTaskDao.deleteAll()
            return null
        }
    }

    /* ---------------- BORRAR UN SOLO DATO ---------------- */

    fun deleteWord(CycleData: CycleData) {
        deleteWordAsyncTask(CycleDao).execute(CycleData)
    }

    private class deleteWordAsyncTask internal constructor(private val mAsyncTaskDao: CycleDao) :
        AsyncTask<CycleData, Void, Void>() {

        override fun doInBackground(vararg params: CycleData): Void? {
            mAsyncTaskDao.deleteWord(params[0])
            return null
        }
    }

    /* -------------- ACTUALIZAR UN SOLO DATO ---------------- */

    fun update(CycleData: CycleData) {
        updateWordAsyncTask(CycleDao).execute(CycleData)
    }

    private class updateWordAsyncTask internal constructor(private val mAsyncTaskDao: CycleDao) :
        AsyncTask<CycleData, Void, Void>() {
        override fun doInBackground(vararg params: CycleData?): Void? {
            mAsyncTaskDao.update(params[0]!!)
            return null
        }
    }
}