package com.hanna.modebanking.testapplication.datasource.network

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.flow.flow
import retrofit2.Response

abstract class FlowNetworkBoundResource<ResultType, RequestType> {

    fun asFlow() = flow {
        emit(Resource.loading(null))
        val dbValue = loadFromDb()
        emit(Resource.loading(dbValue))
        if (shouldFetch()) {
            val apiResponse = fetchFromNetwork()
            if (apiResponse.isSuccessful) {
                saveNetworkResult(processResponse(apiResponse.body()!!))
                val data = loadFromDb()
                emit(Resource.success(data))
            } else {
                onFetchFailed()
                val data = loadFromDb()
                emit(Resource.error(apiResponse.message(), data))
            }
        } else {
            val data = loadFromDb()
            emit(Resource.success(data))
        }
    }

    protected open fun onFetchFailed() {
        // Implement in sub-classes to handle errors
    }

    @WorkerThread
    protected open fun processResponse(response: RequestType) = response

    @WorkerThread
    protected abstract suspend fun saveNetworkResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(): Boolean

    @MainThread
    protected abstract suspend fun loadFromDb(): ResultType

    @MainThread
    protected abstract suspend fun fetchFromNetwork(): Response<RequestType>
}
