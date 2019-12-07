package com.jeluchu.roomlivedata.respository

import androidx.lifecycle.LiveData
import androidx.annotation.WorkerThread
import android.os.AsyncTask
import com.jeluchu.roomlivedata.dao.WordDao
import com.jeluchu.roomlivedata.model.Notification


class WordRepository(private val wordDao: WordDao) {

    val allWords: LiveData<List<Notification>> = wordDao.getAllWords()

    @WorkerThread
    fun insert(notification: Notification) {
        wordDao.insert(notification)
    }


    /* --------------- BORRAR TODOS LOS DATOS -------------- */

    fun deleteAll() {
        deleteAllWordsAsyncTask(wordDao).execute()
    }

    private class deleteAllWordsAsyncTask internal constructor(private val mAsyncTaskDao: WordDao) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            mAsyncTaskDao.deleteAll()
            return null
        }
    }

    /* ---------------- BORRAR UN SOLO DATO ---------------- */

    fun deleteWord(notification: Notification) {
        deleteWordAsyncTask(wordDao).execute(notification)
    }

    private class deleteWordAsyncTask internal constructor(private val mAsyncTaskDao: WordDao) :
        AsyncTask<Notification, Void, Void>() {

        override fun doInBackground(vararg params: Notification): Void? {
            mAsyncTaskDao.deleteWord(params[0])
            return null
        }
    }

    /* -------------- ACTUALIZAR UN SOLO DATO ---------------- */

    fun update(notification: Notification) {
        updateWordAsyncTask(wordDao).execute(notification)
    }

    private class updateWordAsyncTask internal constructor(private val mAsyncTaskDao: WordDao) :
        AsyncTask<Notification, Void, Void>() {
        override fun doInBackground(vararg params: Notification?): Void? {
            mAsyncTaskDao.update(params[0]!!)
            return null
        }
    }
}