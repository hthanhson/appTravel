package com.datn.apptravel.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.datn.apptravel.data.api.NetworkResult
import com.datn.apptravel.data.model.MapPlace
import com.datn.apptravel.data.model.PlanType
import com.datn.apptravel.data.repository.PlacesRepository
import com.datn.apptravel.ui.base.BaseViewModel
import kotlinx.coroutines.launch

/**
 * ViewModel for plan operations and map display
 */
class PlanViewModel(
    private val placesRepository: PlacesRepository
) : BaseViewModel() {
    
    // Current selected plan type (default to NONE - no filter)
    private val _selectedPlanType = MutableLiveData<PlanType>(PlanType.NONE)
    val selectedPlanType: LiveData<PlanType> = _selectedPlanType
    
    // Places to display on map
    private val _places = MutableLiveData<List<MapPlace>>()
    val places: LiveData<List<MapPlace>> = _places
    
    // Error message
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage
    
    // Current search query
    private var currentSearchQuery: String? = null
    
    // Search location coordinates (if user searched for a location)
    private var searchLatitude: Double? = null
    private var searchLongitude: Double? = null
    
    // All fetched places (before filtering)
    private var allPlaces = listOf<MapPlace>()
    
    /**
     * Select a plan type and fetch places
     * Uses search location if available, otherwise uses current device location
     */
    fun selectPlanType(planType: PlanType, currentLatitude: Double, currentLongitude: Double) {
        _selectedPlanType.value = planType
        
        // If NONE is selected, clear all places
        if (planType == PlanType.NONE) {
            _places.value = emptyList()
            setLoading(false)
            return
        }
        
        // Use search location if available, otherwise use current location
        val targetLat = searchLatitude ?: currentLatitude
        val targetLng = searchLongitude ?: currentLongitude
        
        fetchPlaces(planType, targetLat, targetLng)
    }
    
    /**
     * Fetch places by category
     */
    private fun fetchPlaces(planType: PlanType, latitude: Double, longitude: Double) {
        // Skip if NONE type
        if (planType == PlanType.NONE || planType.geoapifyCategory.isEmpty()) {
            _places.value = emptyList()
            setLoading(false)
            return
        }
        
        setLoading(true)
        
        viewModelScope.launch {
            when (val result = placesRepository.getPlacesByCategory(
                category = planType.geoapifyCategory,
                latitude = latitude,
                longitude = longitude,
                radius = 10000, // 10km
                limit = 50
            )) {
                is NetworkResult.Success -> {
                    _places.value = result.data
                    setLoading(false)
                }
                is NetworkResult.Error -> {
                    _errorMessage.value = result.message
                    _places.value = emptyList()
                    setLoading(false)
                }
                is NetworkResult.Loading -> {
                    setLoading(true)
                }
            }
        }
    }
    
    /**
     * Search places by location name and filter by current plan type
     * Example: Search "Hanoi" + select "Lodging" = Show hotels in Hanoi
     * Saves the searched location coordinates for future plan type changes
     */
    fun searchPlaces(query: String, currentLatitude: Double, currentLongitude: Double) {
        currentSearchQuery = query
        
        if (query.isBlank()) {
            // Clear search location - back to using device location
            searchLatitude = null
            searchLongitude = null
            
            // Show places by plan type at current device location
            val currentPlanType = _selectedPlanType.value ?: PlanType.NONE
            fetchPlaces(currentPlanType, currentLatitude, currentLongitude)
            return
        }
        
        // Check if current plan type is NONE
        val currentPlanType = _selectedPlanType.value ?: PlanType.NONE
        if (currentPlanType == PlanType.NONE) {
            _places.value = emptyList()
            setLoading(false)
            return
        }
        
        setLoading(true)
        
        viewModelScope.launch {
            // First, search for the location to get its coordinates
            when (val searchResult = placesRepository.searchPlaces(
                query = query,
                latitude = currentLatitude,
                longitude = currentLongitude,
                radius = 50000, // Larger radius for location search
                limit = 1
            )) {
                is NetworkResult.Success -> {
                    val searchedPlace = searchResult.data.firstOrNull()
                    if (searchedPlace != null) {
                        // Save searched location coordinates
                        searchLatitude = searchedPlace.latitude
                        searchLongitude = searchedPlace.longitude
                        
                        // Now fetch places of selected type at the searched location
                        when (val placesResult = placesRepository.getPlacesByCategory(
                            category = currentPlanType.geoapifyCategory,
                            latitude = searchedPlace.latitude,
                            longitude = searchedPlace.longitude,
                            radius = 10000,
                            limit = 50
                        )) {
                            is NetworkResult.Success -> {
                                allPlaces = placesResult.data
                                _places.value = allPlaces
                                setLoading(false)
                            }
                            is NetworkResult.Error -> {
                                _errorMessage.value = placesResult.message
                                _places.value = emptyList()
                                setLoading(false)
                            }
                            is NetworkResult.Loading -> {
                                setLoading(true)
                            }
                        }
                    } else {
                        _errorMessage.value = "Location not found: $query"
                        _places.value = emptyList()
                        setLoading(false)
                    }
                }
                is NetworkResult.Error -> {
                    _errorMessage.value = searchResult.message
                    _places.value = emptyList()
                    setLoading(false)
                }
                is NetworkResult.Loading -> {
                    setLoading(true)
                }
            }
        }
    }
    
    /**
     * Clear search and reload places at current device location
     */
    fun clearSearch(currentLatitude: Double, currentLongitude: Double) {
        currentSearchQuery = null
        
        // Clear search location - back to using device location
        searchLatitude = null
        searchLongitude = null
        
        val currentPlanType = _selectedPlanType.value ?: PlanType.NONE
        
        // If current plan type is NONE, just clear places
        if (currentPlanType == PlanType.NONE) {
            _places.value = emptyList()
            setLoading(false)
            return
        }
        
        // Fetch places at current device location
        fetchPlaces(currentPlanType, currentLatitude, currentLongitude)
    }
}