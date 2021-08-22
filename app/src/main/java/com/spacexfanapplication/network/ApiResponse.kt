package com.spacexfanapplication.network

import com.spacexfanapplication.R
import retrofit2.Response

@Suppress("unused")
sealed class ApiResponse<T> {
    companion object {
        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(-2, error.message ?: R.string.something_went_wrong.toString())
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.code() == 401) {

                ApiUnAuthorizeResponse(response.code(), response.message())
            } else if (response.code() == 200 && response.body() != null) {
                val body = response.body()
                ApiSuccessResponse(body!!)
            } else {
                ApiErrorResponse(response.code(), response.message())
            }
        }
    }
}

class ApiUnAuthorizeResponse<T>(val status: Int, val message: String) : ApiResponse<T>()

data class ApiSuccessResponse<T>(
        val body: T
) : ApiResponse<T>()

data class ApiErrorResponse<T>(val status: Int, val message: String) : ApiResponse<T>()