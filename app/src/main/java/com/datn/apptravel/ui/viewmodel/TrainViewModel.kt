package com.datn.apptravel.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.datn.apptravel.ui.base.BaseViewModel

/**
 * ViewModel for train operations
 */
class TrainViewModel : BaseViewModel() {
    
    // Save train result
    private val _saveTrainResult = MutableLiveData<Boolean>()
    val saveTrainResult: LiveData<Boolean> = _saveTrainResult
    
    /**
     * Save train details to trip
     */
    fun saveTrain(tripId: String, trainDetails: String) {
        setLoading(true)
        
        // Simulated API call with delay
        android.os.Handler().postDelayed({
            // In a real app, we would send these details to an API
            // For now, just simulate success
            _saveTrainResult.value = true
            setLoading(false)
        }, 1500)
    }
}