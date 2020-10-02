package com.nginapps.core.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nginapps.core.model.Destination.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Destination(
    @PrimaryKey
    var id: Int? = 0,
    var title: String?,
    var lat: String?,
    var lng: String?,
    var photo: String?,
    var desc: String?,
    var isFavorite: Boolean?
) {
    companion object {
        const val TABLE_NAME = "capstone_posts"
    }
}