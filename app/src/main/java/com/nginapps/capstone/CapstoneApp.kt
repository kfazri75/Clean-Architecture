@file:Suppress("unused")
package com.nginapps.capstone

import android.app.Application
import com.nginapps.core.di.databaseModule
import com.nginapps.core.di.networkModule
import com.nginapps.capstone.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class CapstoneApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        initDi()
    }

    private fun initDi() {
        startKoin {
            androidContext(applicationContext)
            modules(networkModule, databaseModule, viewModelModule)
        }
    }
}