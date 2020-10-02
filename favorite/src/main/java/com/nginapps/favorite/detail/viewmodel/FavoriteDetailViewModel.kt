package com.nginapps.favorite.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nginapps.core.data.repository.DestinationsRepository
import com.nginapps.core.model.Destination

class FavoriteDetailViewModel (private val destinationsRepository: DestinationsRepository) : ViewModel() {
    fun getDestinationById(id: Int) = destinationsRepository.getDestinationById(id).asLiveData()
    fun setFavoriteDestination(destination: Destination, newState: Boolean) {
        destinationsRepository.setFavorite(destination, newState)
    }
}