package com.ciuc.andrii.myapplication

import android.app.Application
import com.ciuc.andrii.myapplication.di.repositoryModule
import com.ciuc.andrii.myapplication.di.viewModelModule
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            this.androidContext(this@App)
            modules(repositoryModule, viewModelModule)
        }

        Stetho.initializeWithDefaults(this)
    }
}

