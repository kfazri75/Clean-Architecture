package com.nginapps.favorite.favorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nginapps.core.data.repository.DestinationsRepository

class FavoritesViewModel (private val destinationsRepository: DestinationsRepository) : ViewModel() {
    fun getFavoriteDestination() = destinationsRepository.getFavoriteDestination().asLiveData()
}