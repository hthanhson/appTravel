package com.datn.apptravel.data.api

import com.datn.apptravel.data.model.GeoapifyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface containing all API endpoint definitions
 */
interface ApiService {
    
    /**
     * Get places from Geoapify API
     * @param categories Place category (e.g., "accommodation", "catering.restaurant")
     * @param filter Geographic filter (e.g., "circle:lon,lat,radius")
     * @param limit Maximum number of results
     * @param apiKey Geoapify API key
     */
    @GET("v2/places")
    suspend fun getPlaces(
        @Query("categories") categories: String,
        @Query("filter") filter: String,
        @Query("limit") limit: Int = 20,
        @Query("apiKey") apiKey: String
    ): Response<GeoapifyResponse>
    
    /**
     * Search places by text
     * @param text Search query
     * @param filter Geographic filter
     * @param limit Maximum number of results
     * @param apiKey Geoapify API key
     */
    @GET("v2/places")
    suspend fun searchPlaces(
        @Query("text") text: String,
        @Query("filter") filter: String,
        @Query("limit") limit: Int = 20,
        @Query("apiKey") apiKey: String
    ): Response<GeoapifyResponse>
}