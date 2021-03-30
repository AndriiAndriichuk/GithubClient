package com.ciuc.andrii.myapplication.di

import com.ciuc.andrii.myapplication.ui.fragment.repository_info.ProfileInfoViewModel
import com.ciuc.andrii.myapplication.ui.fragment.search.ProfileSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        ProfileSearchViewModel(get(), get())
    }

    viewModel {
        ProfileInfoViewModel(get(), get())
    }
}