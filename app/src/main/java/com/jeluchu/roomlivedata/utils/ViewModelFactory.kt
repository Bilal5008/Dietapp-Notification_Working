package com.jeluchu.roomlivedata.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jeluchu.roomlivedata.respository.Repository
import com.jeluchu.roomlivedata.viewmodels.LoginViewModel
import javax.inject.Inject


class ViewModelFactory @Inject constructor(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        }



        throw IllegalArgumentException("Unknown class name")
    }
}
