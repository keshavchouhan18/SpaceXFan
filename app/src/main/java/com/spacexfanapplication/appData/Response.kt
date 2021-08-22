package com.spacexfanapplication.appData

import androidx.annotation.Nullable

class Response<T>(var status: Status, val Data: T?, var message: String?) {
    var call: ApiInteractor<T>? = null   // must be set in case of network requests
    var errorCode: Int = 0

    companion object {
        @JvmStatic
        fun <T> success(data: T): Response<T> {
            return Response(Status.SUCCESS, data, null)
        }

        @JvmStatic
        fun <T> error(msg: String?, @Nullable data: T?): Response<T> {
            return Response(Status.FAILURE, data, msg)
        }

        @JvmStatic
        fun <T> error(errorCode: Int, msg: String?, @Nullable data: T?): Response<T> {
            val result = Response(Status.FAILURE, data, msg)
            result.errorCode = errorCode
            return result
        }

        @JvmStatic
        fun <T> loading(@Nullable data: T?): Response<T> {
            return Response(Status.LOADING, data, null)
        }
    }

    fun saveCall(call: ApiInteractor<T>?): Response<T> {
        this.call = call
        return this
    }

    fun retry() {
        call?.fetchFromNetwork()
    }

    fun isSuccess(): Boolean {
        return status == Status.SUCCESS
    }

    fun isFailure(): Boolean {
        return status == Status.FAILURE
    }

    fun isLoading(): Boolean {
        return status == Status.LOADING
    }

    enum class Status {
        LOADING,
        SUCCESS,
        FAILURE
    }
}