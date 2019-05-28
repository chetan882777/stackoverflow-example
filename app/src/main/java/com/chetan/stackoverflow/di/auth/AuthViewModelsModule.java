package com.chetan.stackoverflow.di.auth;

import androidx.lifecycle.ViewModel;

import com.chetan.stackoverflow.di.ViewModelKey;
import com.chetan.stackoverflow.ui.auth.AuthViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AuthViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    public abstract ViewModel bindAuthViewModel(AuthViewModel viewModel);
}
