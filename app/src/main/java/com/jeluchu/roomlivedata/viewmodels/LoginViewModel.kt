package com.jeluchu.roomlivedata.viewmodels


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jeluchu.roomlivedata.respository.Repository
import com.jeluchu.roomlivedata.utils.ApiResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class LoginViewModel(private val repository: Repository) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val responseLiveData = MutableLiveData<ApiResponse>()

    fun loginResponse(): MutableLiveData<ApiResponse> {
        return responseLiveData
    }

    /*
     * method to call normal login api with $(mobileNumber + password)
     * */
    fun hitLoginApi( key: String ,userName: String, password: String) {

        disposables.add(repository.executeLogin( key,userName, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { d -> responseLiveData.setValue(ApiResponse.loading()) }
            .subscribe(
                { result -> responseLiveData.setValue(ApiResponse.success(result)) },
                { throwable -> responseLiveData.setValue(ApiResponse.error(throwable)) }
            ))

    }

    override fun onCleared() {
        disposables.clear()
    }
}
