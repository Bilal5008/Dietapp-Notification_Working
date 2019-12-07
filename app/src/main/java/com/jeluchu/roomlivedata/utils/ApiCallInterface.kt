package com.jeluchu.roomlivedata.utils

import com.google.gson.JsonElement
import io.reactivex.Observable
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiCallInterface {


    @POST(Constants.LOGIN)
    fun login(@Query("secret_key") key: String, @Query("param1") email: String, @Query("param2") password: String): Observable<JsonElement>

}
