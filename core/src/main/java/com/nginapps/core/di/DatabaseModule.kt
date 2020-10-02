package com.nginapps.core.di

import com.nginapps.core.data.local.CapstoneDestinationsDatabase
import com.nginapps.core.data.local.DestinationLocalDataSource
import com.nginapps.core.utils.AppExecutors
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        CapstoneDestinationsDatabase.getInstance(androidApplication())
    }
    single {
        get<CapstoneDestinationsDatabase>().getDestinationsDao()
    }

    single {
        DestinationLocalDataSource(get())
    }

    factory { AppExecutors() }
}