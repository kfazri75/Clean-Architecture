package com.nginapps.capstone.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nginapps.core.data.repository.DestinationsRepository
import com.nginapps.core.model.Destination

class DetailViewModel (private val destinationsRepository: DestinationsRepository) : ViewModel() {
    fun setFavoriteDestination(destination: Destination, newState: Boolean) {
        destinationsRepository.setFavorite(destination, newState)
    }

    fun getDestination(id: Int) = destinationsRepository.getDestinationById(id).asLiveData()
}