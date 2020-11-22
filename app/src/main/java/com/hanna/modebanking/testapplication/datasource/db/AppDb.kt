package com.hanna.modebanking.testapplication.datasource.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hanna.modebanking.testapplication.model.Joke
//Prototypes - N/A
//Tests - N/A
@Database(entities = [Joke::class], version = 1)
abstract class AppDb: RoomDatabase(){
    abstract fun jokeDao(): JokeDao
}