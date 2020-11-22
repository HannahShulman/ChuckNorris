package com.hanna.modebanking.testapplication.di.modules

import com.hanna.modebanking.testapplication.datasource.db.AppDb
import com.hanna.modebanking.testapplication.datasource.db.JokeDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DaoModule {

    @Provides
    @Singleton
    fun provideJokeDao(db: AppDb): JokeDao = db.jokeDao()
}