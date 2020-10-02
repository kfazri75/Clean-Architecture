package com.nginapps.core.data.local.dao

import androidx.room.*
import com.nginapps.core.model.Destination
import kotlinx.coroutines.flow.Flow

@Dao
interface DestinationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDestinations(destinations: List<Destination>)

    @Query("SELECT * FROM ${Destination.TABLE_NAME} WHERE ID = :destinationId")
    fun getDestinationById(destinationId: Int): Flow<Destination>

    @Query("SELECT * FROM ${Destination.TABLE_NAME}")
    fun getAllDestinations(): Flow<List<Destination>>

    @Query("SELECT * FROM ${Destination.TABLE_NAME} WHERE isFavorite = 1")
    fun getFavoriteDestination() : Flow<List<Destination>>

    @Update
    fun updateFavoriteDestination(destination: Destination)
}