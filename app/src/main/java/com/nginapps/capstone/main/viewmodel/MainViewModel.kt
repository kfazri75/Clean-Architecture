package com.nginapps.capstone.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nginapps.core.data.repository.DestinationsRepository
import com.nginapps.core.model.Destination
import com.nginapps.core.utils.State
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel (private val destinationsRepository: DestinationsRepository) : ViewModel() {
    private val _destinationsLiveData = MutableLiveData<State<List<Destination>>>()

    val destinationsLiveData: LiveData<State<List<Destination>>>
        get() = _destinationsLiveData

    fun getDestinations() {
        viewModelScope.launch {
            destinationsRepository.getAllDestinations().collect {
                _destinationsLiveData.value = it
            }
        }
    }
}