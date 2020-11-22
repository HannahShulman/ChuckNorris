package com.hanna.modebanking.testapplication.di.components

import com.hanna.modebanking.testapplication.di.scope.FragmentScoped
import com.hanna.modebanking.testapplication.ui.JokeDialog
import com.hanna.modebanking.testapplication.ui.JokesListFragment
import dagger.Component

@FragmentScoped
@Component(dependencies = [NetComponent::class])
interface ApplicationComponent {
    fun inject(fragment: JokesListFragment)
    fun inject(fragment: JokeDialog)
}