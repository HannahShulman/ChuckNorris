package com.hanna.modebanking.testapplication.datasource

import android.content.SharedPreferences
import java.util.*
import javax.inject.Inject

//Prototypes - V
//Tests - V
class SpContract @Inject constructor(private val sp: SharedPreferences) {

    var lastTimeJokesFetched: Long = 0L
        get() = sp.getLong(LAST_TIME_FETCHED, 0L)
        set(value) {
            field = value
            sp.edit().putLong(LAST_TIME_FETCHED, value).apply()
        }

    companion object {
        const val LAST_TIME_FETCHED = "last_fetched_jokes"
    }
}