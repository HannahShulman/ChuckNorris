package com.hanna.modebanking.testapplication.di.components;

import com.hanna.modebanking.testapplication.datasource.db.JokeDao
import com.hanna.modebanking.testapplication.di.modules.*
import com.hanna.modebanking.testapplication.repository.JokesRepository
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, NetModule::class, DaoModule::class, DbModule::class,
    RepositoryModule::class, SharedPreferenceModule::class])
interface NetComponent {

    val jokesRepository: JokesRepository

    val jokeDao: JokeDao
}
