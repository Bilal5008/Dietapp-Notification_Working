package com.jeluchu.roomlivedata.respository

import com.google.gson.JsonElement
import com.jeluchu.roomlivedata.utils.ApiCallInterface
import io.reactivex.Observable

/**
 * Created by ${Saquib} on 03-05-2018.
 */

class Repository(private val apiCallInterface: ApiCallInterface) {

    /*
     * method to call login api
     * */
    fun executeLogin(key : String ,email: String, password: String): Observable<JsonElement> {
        return apiCallInterface.login(key,email, password)
    }


}
