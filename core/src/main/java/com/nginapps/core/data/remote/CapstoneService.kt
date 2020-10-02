@file:Suppress("SpellCheckingInspection", "SpellCheckingInspection", "SpellCheckingInspection")

package com.nginapps.core.data.remote

import com.nginapps.core.model.Destination
import retrofit2.Response
import retrofit2.http.GET

interface CapstoneService {

    @GET("/kfazri75/Capstone/master/json/destination.json")
    suspend fun getDestinations(): Response<List<Destination>>

    companion object{
        const val API_URL = "https://raw.githubusercontent.com"
    }
}