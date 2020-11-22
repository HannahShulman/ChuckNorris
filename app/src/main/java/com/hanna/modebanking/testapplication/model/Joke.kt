package com.hanna.modebanking.testapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.hanna.modebanking.testapplication.datasource.db.CategoriesConverter

data class JokesResponse(val type: String, val value: List<Joke>)

@Entity
@TypeConverters(CategoriesConverter::class)
data class Joke(
    @PrimaryKey val id: Int = 0,
    val joke: String = "",
    var categories: List<String> = listOf()
)