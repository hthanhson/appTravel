package com.datn.apptravel.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * Utility class for handling API responses and network operations
 */
object NetworkUtils {
    
    /**
     * Check if internet connection is available
     */
    suspend fun isNetworkAvailable(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val runtime = Runtime.getRuntime()
                val process = runtime.exec("ping -c 1 google.com")
                val exitValue = process.waitFor()
                return@withContext (exitValue == 0)
            } catch (e: IOException) {
                return@withContext false
            }
        }
    }
    
    /**
     * Constants for network status
     */
    object NetworkStatus {
        const val NETWORK_ERROR = "Network Error"
        const val CONNECTION_ERROR = "Connection Error"
        const val TIMEOUT_ERROR = "Timeout Error"
        const val UNKNOWN_ERROR = "Unknown Error"
    }
}