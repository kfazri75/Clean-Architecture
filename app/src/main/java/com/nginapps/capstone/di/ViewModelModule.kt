package com.nginapps.capstone.di

import com.nginapps.capstone.detail.viewmodel.DetailViewModel
import com.nginapps.capstone.main.viewmodel.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainViewModel(get())
    }
    viewModel {
        DetailViewModel(get())
    }
}