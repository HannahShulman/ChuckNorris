package com.hanna.modebanking.testapplication.di.components;


import com.hanna.modebanking.testapplication.MainActivity;
import com.hanna.modebanking.testapplication.di.scope.FragmentScoped;
import com.hanna.modebanking.testapplication.ui.JokeDialog;
import com.hanna.modebanking.testapplication.ui.JokesListFragment;

import dagger.Component;

@FragmentScoped
@Component(dependencies = NetComponent.class)
public interface ApplicationComponent {

    void inject(MainActivity activity);
    void inject(JokesListFragment fragment);
    void inject(JokeDialog fragment);
}
