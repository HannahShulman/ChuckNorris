package com.hanna.modebanking.testapplication.repository

import android.text.format.DateUtils
import com.hanna.modebanking.testapplication.datasource.SpContract
import com.hanna.modebanking.testapplication.datasource.db.JokeDao
import com.hanna.modebanking.testapplication.datasource.network.Api
import com.hanna.modebanking.testapplication.datasource.network.FlowNetworkBoundResource
import com.hanna.modebanking.testapplication.datasource.network.Resource
import com.hanna.modebanking.testapplication.model.Joke
import com.hanna.modebanking.testapplication.model.JokesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.util.*
import javax.inject.Inject

interface JokesRepository {
    suspend fun getAllJokesExcludingExplicit(): Flow<Resource<List<Joke>>>
    suspend fun getJokeById(id: Int): Joke
}

class JokesRepositoryImpl @Inject constructor(
    private val api: Api,
    private val jokeDao: JokeDao,
    private val spContract: SpContract
) : JokesRepository {

    override suspend fun getAllJokesExcludingExplicit(): Flow<Resource<List<Joke>>> {
        return object : FlowNetworkBoundResource<List<Joke>, JokesResponse>() {
            override suspend fun saveNetworkResult(item: JokesResponse) {
                spContract.lastTimeJokesFetched = Date().time
                withContext(Dispatchers.Default) { jokeDao.saveAllJokes(item.value) }
            }

            override suspend fun loadFromDb(): List<Joke> {
                return jokeDao.getRandomJokesExcludingExplicit()
            }

            override suspend fun fetchFromNetwork(): Response<JokesResponse> {
                return api.getRandomJokesExcludingExplicit()
            }

            override fun shouldFetch(): Boolean {
                return (Date().time.minus(DateUtils.DAY_IN_MILLIS) >= spContract.lastTimeJokesFetched)
            }
        }.asFlow()
    }

    override suspend fun getJokeById(id: Int): Joke {
        return jokeDao.getJokeById(id)
//        return withContext(Dispatchers.Default) { async { jokeDao.getJokeById(id) }.await() }
    }
}