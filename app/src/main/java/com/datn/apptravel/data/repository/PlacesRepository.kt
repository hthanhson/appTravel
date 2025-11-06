package com.datn.apptravel.data.repository

import com.datn.apptravel.BuildConfig
import com.datn.apptravel.data.api.ApiService
import com.datn.apptravel.data.api.NetworkResult
import com.datn.apptravel.data.model.response.MapPlace

class PlacesRepository(private val apiService: ApiService) {
    
    companion object {
        private val API_KEY = BuildConfig.GEOAPIFY_API_KEY
        private const val DEFAULT_RADIUS = 30000
    }

    suspend fun getPlacesByCategory(
        category: String,
        latitude: Double,
        longitude: Double,
        radius: Int = DEFAULT_RADIUS,
        limit: Int = 20
    ): NetworkResult<List<MapPlace>> {
        return try {
            val filter = "circle:$longitude,$latitude,$radius"
            val response = apiService.getPlaces(
                categories = category,
                filter = filter,
                limit = limit,
                apiKey = API_KEY
            )
            
            if (response.isSuccessful && response.body() != null) {
                val places = response.body()!!.features?.mapNotNull { feature ->
                    val properties = feature.properties
                    if (properties?.lat != null && properties.lon != null) {
                        MapPlace(
                            id = properties.placeId ?: "",
                            name = properties.name ?: "Unknown",
                            latitude = properties.lat,
                            longitude = properties.lon,
                            address = properties.formatted ?: properties.addressLine1,
                            category = category
                        )
                    } else null
                } ?: emptyList()
                
                NetworkResult.Success(places)
            } else {
                NetworkResult.Error("Failed to fetch places: ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResult.Error("Network error: ${e.message}")
        }
    }

    suspend fun searchPlaces(
        query: String,
        latitude: Double,
        longitude: Double,
        radius: Int = DEFAULT_RADIUS,
        limit: Int = 20
    ): NetworkResult<List<MapPlace>> {
        return try {
            val filter = "circle:$longitude,$latitude,$radius"
            val response = apiService.searchPlaces(
                text = query,
                filter = filter,
                limit = limit,
                apiKey = API_KEY
            )
            
            if (response.isSuccessful && response.body() != null) {
                val places = response.body()!!.features?.mapNotNull { feature ->
                    val properties = feature.properties
                    if (properties?.lat != null && properties.lon != null) {
                        MapPlace(
                            id = properties.placeId ?: "",
                            name = properties.name ?: "Unknown",
                            latitude = properties.lat,
                            longitude = properties.lon,
                            address = properties.formatted ?: properties.addressLine1,
                            category = properties.categories?.firstOrNull() ?: "general"
                        )
                    } else null
                } ?: emptyList()
                
                NetworkResult.Success(places)
            } else {
                NetworkResult.Error("Search failed: ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResult.Error("Network error: ${e.message}")
        }
    }
}
