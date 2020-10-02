package com.nginapps.core.data.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.nginapps.core.utils.State
import kotlinx.coroutines.flow.*
import retrofit2.Response

abstract class NetworkBoundRepository<RESULT, REQUEST> {
    fun asFlow() = flow<State<RESULT>> {

        emit(State.loading())

        val dbSource = fetchFromLocal().first()
        if (shouldFetch(dbSource)) {
            emit(State.loading())
            val apiResponse = fetchFromRemote()

            val remotePosts = apiResponse.body()

            if (apiResponse.isSuccessful) {
                if (remotePosts != null) {
                    saveRemoteData(remotePosts)
                    emitAll(fetchFromLocal().map { State.success(it) })
                } else {
                    emitAll(fetchFromLocal().map { State.success(it) })
                }
            } else {
                emit(State.error(apiResponse.message()))
            }
        } else {
            emitAll(fetchFromLocal().map { State.success(it) })
        }
    }

    @WorkerThread
    protected abstract suspend fun saveRemoteData(response: REQUEST)

    @MainThread
    protected abstract fun fetchFromLocal(): Flow<RESULT>

    @MainThread
    protected abstract fun shouldFetch(data: RESULT?): Boolean

    @MainThread
    protected abstract suspend fun fetchFromRemote(): Response<REQUEST>
}