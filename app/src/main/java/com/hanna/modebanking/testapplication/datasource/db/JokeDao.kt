package com.hanna.modebanking.testapplication.datasource.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.hanna.modebanking.testapplication.model.Joke

//Prototypes - N/A
//Tests - N/A
@Dao
interface JokeDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertJokes(list: List<Joke>)

    @Query("SELECT * FROM Joke")
    suspend fun getAllJokes(): List<Joke>//just an endpoint based on potential future features

    @Query("SELECT * FROM Joke WHERE id =:id")
    suspend fun getJokeById(id: Int): Joke

    @Query("SELECT * FROM Joke")
    suspend fun getRandomJokesExcludingExplicit(): List<Joke>//verbose to support future, if want to add further queries

    @Query("DELETE FROM Joke")
    suspend fun deleteAllJokes()

    @Transaction
    open suspend fun saveAllJokes(list: List<Joke>) {
        deleteAllJokes()
        insertJokes(list)
    }
}