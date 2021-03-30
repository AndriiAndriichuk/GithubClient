package com.ciuc.andrii.myapplication.di

import com.ciuc.andrii.myapplication.repository.ProductRepositoryImpl
import com.ciuc.andrii.myapplication.repository.SharedStorage
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single {
        ProductRepositoryImpl()
    }

    single {
        SharedStorage(this.androidContext())
    }
}