package com.hanna.modebanking.testapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hanna.modebanking.testapplication.OpenForTesting
import com.hanna.modebanking.testapplication.model.Joke
import com.hanna.modebanking.testapplication.repository.JokesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

//This class is marked as opened and should not be so. It should be handled by anotation.
@ExperimentalCoroutinesApi
@OpenForTesting
class JokesViewModel @Inject constructor(private val repository: JokesRepository) :
    ViewModel() {

    suspend fun jokesList() = repository.getAllJokesExcludingExplicit()
    suspend fun getJokeById(id: Int): Joke = repository.getJokeById(id)
}

@ExperimentalCoroutinesApi
class JokesViewModelFactory @Inject constructor(private val repository: JokesRepository) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return JokesViewModel(repository) as T
    }
}