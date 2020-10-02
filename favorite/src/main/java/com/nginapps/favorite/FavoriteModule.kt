package com.nginapps.favorite

import com.nginapps.favorite.detail.viewmodel.FavoriteDetailViewModel
import com.nginapps.favorite.favorite.viewmodel.FavoritesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel { FavoritesViewModel(get()) }
    viewModel { FavoriteDetailViewModel(get()) }
}