package com.jeluchu.roomlivedata.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.jeluchu.roomlivedata.database.WordRoomDatabase
import com.jeluchu.roomlivedata.model.Notification
import com.jeluchu.roomlivedata.respository.WordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class WordViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: WordRepository
    val allWords: LiveData<List<Notification>>

    init {
        val wordsDao = WordRoomDatabase.getDatabase(application, scope).wordDao()
        repository = WordRepository(wordsDao)
        allWords = repository.allWords
    }

    fun insert(notification: Notification) = scope.launch(Dispatchers.IO) {
        repository.insert(notification)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    // BORRAR TODOS LOS DATOS
    fun deleteAll() {
        repository.deleteAll()
    }

    // BORRAR UN SOLO DATO
    fun deleteWord(notification: Notification) {
        repository.deleteWord(notification)
    }

    fun updateWord(notification: Notification) {
        repository.update(notification)
    }


}