package com.hanna.modebanking.testapplication.di

import com.hanna.modebanking.testapplication.MainActivity
import com.hanna.modebanking.testapplication.MyApp
import com.hanna.modebanking.testapplication.di.components.DaggerApplicationComponent
import com.hanna.modebanking.testapplication.ui.JokeDialog
import com.hanna.modebanking.testapplication.ui.JokesListFragment

object DaggerInjectHelper {

    fun inject(activity: MainActivity) {
        DaggerApplicationComponent.builder()
            .netComponent((activity.applicationContext as MyApp).netComponent)
            .build()
            .inject(activity)
    }

    fun inject(fragment: JokesListFragment) {
        DaggerApplicationComponent.builder()
            .netComponent((fragment.context?.applicationContext as MyApp).netComponent)
            .build()
            .inject(fragment)
    }

    fun inject(jokeDialog: JokeDialog) {
        DaggerApplicationComponent.builder()
            .netComponent((jokeDialog.context?.applicationContext as MyApp).netComponent)
            .build()
            .inject(jokeDialog)
    }
}