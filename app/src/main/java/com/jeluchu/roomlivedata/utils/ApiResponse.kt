package com.jeluchu.roomlivedata.utils

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.google.gson.JsonElement


class ApiResponse private constructor(val status: Status, @param:Nullable @field:Nullable
val data: JsonElement?, @param:Nullable @field:Nullable
                                      val error: Throwable?) {
    companion object {

        fun loading(): ApiResponse {
            return ApiResponse(Status.LOADING, null, null)
        }

        fun success(@NonNull data:JsonElement): ApiResponse {
            return ApiResponse(Status.SUCCESS, data, null)
        }

        fun error(@NonNull error: Throwable): ApiResponse {
            return ApiResponse(Status.ERROR, null, error)
        }
    }

}
