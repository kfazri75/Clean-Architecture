@file:Suppress("SpellCheckingInspection")

package com.nginapps.core.di

import com.nginapps.core.data.remote.CapstoneService
import com.nginapps.core.data.repository.DestinationsRepository
import com.nginapps.core.utils.TimberLoggingInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        val hostname = "raw.githubusercontent.com"
        val certificate = CertificatePinner.Builder()
            .add(hostname, "sha256/xlDAST56PmiT3SR0WdFOR3dghwJrQ8yXx6JLSqTIRpk=")
            .add(hostname, "sha256/k2v657xBsOVe1PQRwOsHsw3bsGT2VzIqz5K+59sNQws=")
            .add(hostname, "sha256/WoiWRyIOVNa9ihaBciRSC7XHjliYS9VwUGOIud4PB18=")
            .build()

        OkHttpClient.Builder()
            .addInterceptor(TimberLoggingInterceptor())
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificate)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(CapstoneService.API_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .client(get())
            .build()
            .create(CapstoneService::class.java)
    }

    single {
        DestinationsRepository(get(), get(), get())
    }

}