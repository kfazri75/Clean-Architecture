package com.nginapps.core.data.local

import com.nginapps.core.data.local.dao.DestinationDao
import com.nginapps.core.model.Destination
import kotlinx.coroutines.flow.Flow

class DestinationLocalDataSource (private val dao: DestinationDao) {
    fun getAllDestination() : Flow<List<Destination>> = dao.getAllDestinations()

    fun getFavoriteDestination(): Flow<List<Destination>> = dao.getFavoriteDestination()

    fun insertDestination(destinationList: List<Destination>) = dao.insertDestinations(destinationList)

    fun setFavoriteDestination(destination: Destination, newState: Boolean) {
        destination.isFavorite = newState
        dao.updateFavoriteDestination(destination)
    }

    fun getDestination(id: Int) = dao.getDestinationById(id)
}