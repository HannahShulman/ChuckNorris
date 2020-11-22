package com.hanna.modebanking.testapplication.di.modules


import com.hanna.modebanking.testapplication.repository.JokesRepository
import com.hanna.modebanking.testapplication.repository.JokesRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun jokesRepository(repository: JokesRepositoryImpl): JokesRepository
}