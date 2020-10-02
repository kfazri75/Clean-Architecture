package com.nginapps.core.data.repository

import androidx.annotation.MainThread
import com.nginapps.core.data.local.DestinationLocalDataSource
import com.nginapps.core.data.remote.CapstoneService
import com.nginapps.core.model.Destination
import com.nginapps.core.utils.AppExecutors
import com.nginapps.core.utils.State
import com.nginapps.core.utils.map.DestinationDataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import retrofit2.Response

class DestinationsRepository (
    private val appExecutors: AppExecutors,
    private val capstoneService: CapstoneService,
    private val localDataSource: DestinationLocalDataSource,
) {
    fun getAllDestinations(): Flow<State<List<Destination>>> {
        return object : NetworkBoundRepository<List<Destination>, List<Destination>>() {

            override fun fetchFromLocal(): Flow<List<Destination>> {
                return localDataSource.getAllDestination().map {
                    DestinationDataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Destination>?): Boolean {
                //data null || empty
                //change to true if always update data from internet
                return true
            }

            override suspend fun fetchFromRemote(): Response<List<Destination>> =
                capstoneService.getDestinations()

            override suspend fun saveRemoteData(response: List<Destination>) {
                val destinationList = DestinationDataMapper.mapResponseToEntities(response)
                localDataSource.insertDestination(destinationList)
            }

        }.asFlow().flowOn(Dispatchers.IO)
    }

    fun getFavoriteDestination() : Flow<List<Destination>> {
        return localDataSource.getFavoriteDestination().map {
            DestinationDataMapper.mapEntitiesToDomain(it)
        }
    }

    fun setFavorite(destination: Destination, state: Boolean) {
        val destiny = DestinationDataMapper.mapDomainToEntity(destination)
        appExecutors.diskIO().execute { localDataSource.setFavoriteDestination(destiny, state) }
    }

    @MainThread
    fun getDestinationById(id: Int): Flow<Destination> = localDataSource.getDestination(id)
}