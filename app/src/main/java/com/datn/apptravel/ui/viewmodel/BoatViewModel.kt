package com.datn.apptravel.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.datn.apptravel.ui.base.BaseViewModel

/**
 * ViewModel for boat operations
 */
class BoatViewModel : BaseViewModel() {
    
    // Save boat result
    private val _saveBoatResult = MutableLiveData<Boolean>()
    val saveBoatResult: LiveData<Boolean> = _saveBoatResult
    
    /**
     * Save boat details to trip
     */
    fun saveBoat(tripId: String, boatDetails: String) {
        setLoading(true)
        
        // Simulated API call with delay
        android.os.Handler().postDelayed({
            // In a real app, we would send these details to an API
            // For now, just simulate success
            _saveBoatResult.value = true
            setLoading(false)
        }, 1500)
    }
}