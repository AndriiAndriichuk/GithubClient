package com.ciuc.andrii.myapplication.di

import com.ciuc.andrii.myapplication.ui.activities.main.MainViewModel
import com.ciuc.andrii.myapplication.ui.activities.repository_info.RepositoryInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainViewModel(get(), get())
    }

    viewModel {
        RepositoryInfoViewModel(get(), get())
    }
}