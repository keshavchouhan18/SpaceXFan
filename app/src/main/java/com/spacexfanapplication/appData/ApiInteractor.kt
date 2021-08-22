package com.spacexfanapplication.appData

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.spacexfanapplication.R
import com.spacexfanapplication.SpaceXApplication
import com.spacexfanapplication.network.ApiErrorResponse
import com.spacexfanapplication.network.ApiResponse
import com.spacexfanapplication.network.ApiSuccessResponse
import com.spacexfanapplication.network.ApiUnAuthorizeResponse
import com.spacexfanapplication.utils.AppUtils

abstract class ApiInteractor<ResponseType> {

    private val result = MediatorLiveData<Response<ResponseType>>()

    val asLiveData: LiveData<Response<ResponseType>>
        get() = result

    init {
        fetchFromNetwork()
    }

    fun fetchFromNetwork() {
        val networkResponse = createCall()
        result.value = Response.loading(null)
        if (AppUtils.isNetworkConnected()) {
            result.addSource(networkResponse) { response ->
                result.removeSource(networkResponse)
                when (response) {
                    is ApiSuccessResponse -> {
                        setValue(Response.success(response.body))
                    }
                    is ApiUnAuthorizeResponse -> {
                        setValue(
                                Response.error<ResponseType>(
                                        response.status,
                                        response.message,
                                        null
                                ).saveCall(this@ApiInteractor)
                        )
                    }
                    is ApiErrorResponse -> {
                        onFetchFailed()
                        setValue(
                                Response.error<ResponseType>(
                                        response.status,
                                        response.message,
                                        null
                                ).saveCall(this@ApiInteractor)
                        )
                    }
                }
            }
        } else {
            result.value = Response.error<ResponseType>(
                    -1,
                    SpaceXApplication.context.resources.getString(R.string.no_internet_connection),
                    null
            ).saveCall(this@ApiInteractor)
        }
    }

    @MainThread
    private fun setValue(newValue: Response<ResponseType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<ResponseType>>

    @MainThread
    protected fun onFetchFailed() {
    }
}