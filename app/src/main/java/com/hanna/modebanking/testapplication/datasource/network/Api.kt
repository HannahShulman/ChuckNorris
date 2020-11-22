package com.hanna.modebanking.testapplication.datasource.network

import com.hanna.modebanking.testapplication.model.JokesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//Prototypes - N/A
//Tests - N/A
interface Api {

    @GET("jokes")
    suspend fun getAllJokes(): Response<JokesResponse>

    @GET("jokes/")
    suspend fun getRandomJokesExcludingExplicit(@Query("exclude") list: List<String> = listOf("explicit")): Response<JokesResponse>
}