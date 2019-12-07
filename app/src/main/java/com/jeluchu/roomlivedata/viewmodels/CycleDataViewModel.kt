package com.jeluchu.roomlivedata.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.jeluchu.roomlivedata.database.WordRoomDatabase
import com.jeluchu.roomlivedata.model.CycleData
import com.jeluchu.roomlivedata.respository.CycleDataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CycleDataViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: CycleDataRepository
    val allCycleData: LiveData<List<CycleData>>

    init {
        val cycleDao = WordRoomDatabase.getDatabase(application, scope).cycleDataDao()
        repository = CycleDataRepository(cycleDao)
        allCycleData = repository.allCycleData
    }

    fun insert(cycleData : CycleData) = scope.launch(Dispatchers.IO) {
        repository.insert(cycleData)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }


    fun deleteAll() {
        repository.deleteAll()
    }


    fun deleteWord(cycleData: CycleData) {
        repository.deleteWord(cycleData)
    }

    fun updateWord(cycleData: CycleData) {
        repository.update(cycleData)
    }


}