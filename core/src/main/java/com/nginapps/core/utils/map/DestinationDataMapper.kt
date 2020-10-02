package com.nginapps.core.utils.map

import com.nginapps.core.model.Destination

object DestinationDataMapper {
    fun mapResponseToEntities(input: List<Destination>): List<Destination> {
        val destinationList = mutableListOf<Destination>()
        input.map {
            val destination = Destination(
                id = it.id,
                title = it.title,
                lat = it.lat,
                lng = it.lng,
                photo = it.photo,
                desc = it.desc,
                isFavorite = false
            )
            destinationList.add(destination)
        }
        return destinationList
    }

    fun mapEntitiesToDomain(input: List<Destination>) : List<Destination> =
        input.map {
            Destination(
                id = it.id,
                title = it.title,
                lat = it.lat,
                lng = it.lng,
                photo = it.photo,
                desc = it.desc,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: Destination) = Destination(
        id = input.id,
        title = input.title,
        lat = input.lat,
        lng = input.lng,
        photo = input.photo,
        desc = input.desc,
        isFavorite = input.isFavorite
    )
}