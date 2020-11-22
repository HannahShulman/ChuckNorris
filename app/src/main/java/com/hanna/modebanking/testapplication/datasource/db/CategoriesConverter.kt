package com.hanna.modebanking.testapplication.datasource.db

import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

//Prototypes - V
//Tests - V
class CategoriesConverter {

    @TypeConverter
    fun stringToProductArray(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun arrayToString(value: List<String>): String {
        return Gson().toJson(value)
    }
}