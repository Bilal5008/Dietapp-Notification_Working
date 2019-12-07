package com.jeluchu.roomlivedata.utils

import android.content.Context
import android.content.SharedPreferences

import com.jeluchu.roomlivedata.Application


class SharedPreferenceHelper {
    internal var sharedPreferences: SharedPreferences

    init {

        sharedPreferences = Application.context!!.getSharedPreferences(Constants.FILE_NAME, Context.MODE_PRIVATE)
    }


    fun setInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, -1)
    }

    fun setIntegerValue(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun getIntegerValue(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun setFloatValue(key: String, value: Float) {
        sharedPreferences.edit().putFloat(key, value).apply()
    }

    fun getFloatValue(key: String, defaultValue: Float): Float {
        return sharedPreferences.getFloat(key, defaultValue)
    }


    fun setBooleanValue(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun setBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun getBooleanValue(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun setStringValue(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getStringValue(key: String, defaultValue: String): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    fun setLongValue(key: String, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    fun getLongValue(key: String, defaultValue: Long?): Long {
        return sharedPreferences.getLong(key, defaultValue!!)
    }

    companion object {
        val PREFERENCE_NAME = "PREFERENCE_DATA"
        var sharedPreferenceHelper: SharedPreferenceHelper? = null

        val instance: SharedPreferenceHelper
            get() {
                if (sharedPreferenceHelper == null) {
                    sharedPreferenceHelper = SharedPreferenceHelper()
                }
                return sharedPreferenceHelper!!
            }
    }

}
