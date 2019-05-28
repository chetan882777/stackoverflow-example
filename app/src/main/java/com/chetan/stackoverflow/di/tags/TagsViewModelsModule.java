package com.chetan.stackoverflow.di.tags;

import androidx.lifecycle.ViewModel;

import com.chetan.stackoverflow.di.ViewModelKey;
import com.chetan.stackoverflow.ui.auth.AuthViewModel;
import com.chetan.stackoverflow.ui.tags.TagsViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class TagsViewModelsModule {

    @Binds
   @IntoMap
    @ViewModelKey(TagsViewModel.class)
    public abstract ViewModel bindAuthViewModel(TagsViewModel viewModel);
}
