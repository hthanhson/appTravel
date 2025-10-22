package com.datn.apptravel.data.api

import retrofit2.Response

/**
 * Helper class to handle API responses and errors safely
 */
abstract class BaseApiHelper {
    /**
     * Safe API call wrapper that handles exceptions
     */
    protected suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return NetworkResult.Success(body)
                }
            }
            return NetworkResult.Error("API call failed with code: ${response.code()}, message: ${response.message()}")
        } catch (e: Exception) {
            return NetworkResult.Error("Network error: ${e.message ?: "Unknown error occurred"}")
        }
    }
}