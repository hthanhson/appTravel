package com.datn.apptravel.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.datn.apptravel.ui.base.BaseViewModel

/**
 * ViewModel for trip operations
 */
class TripViewModel : BaseViewModel() {
    
    // Create trip result
    private val _createTripResult = MutableLiveData<String?>()
    val createTripResult: LiveData<String?> = _createTripResult
    
    /**
     * Create a new trip
     */
    fun createTrip(name: String, destination: String, startDate: String, endDate: String) {
        setLoading(true)
        
        // Simulated API call with delay
        android.os.Handler().postDelayed({
            // Simulate successful trip creation with a generated ID
            _createTripResult.value = "trip_${System.currentTimeMillis()}"
            setLoading(false)
        }, 2000)
    }
    
    /**
     * Get user trips
     */
    fun getTrips() {
        setLoading(true)
        
        // Simulated API call with delay
        android.os.Handler().postDelayed({
            // Simulate fetching trips
            setLoading(false)
        }, 1500)
    }
}