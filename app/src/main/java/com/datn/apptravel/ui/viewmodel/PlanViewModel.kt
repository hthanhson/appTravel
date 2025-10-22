package com.datn.apptravel.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.datn.apptravel.ui.base.BaseViewModel

/**
 * ViewModel for plan operations
 */
class PlanViewModel : BaseViewModel() {
    
    // Plan options
    private val _planOptions = MutableLiveData<List<Any>>()
    val planOptions: LiveData<List<Any>> = _planOptions
    
    /**
     * Get available plan options
     */
    fun getPlanOptions(tripId: String) {
        setLoading(true)
        
        // Simulated API call with delay
        android.os.Handler().postDelayed({
            // Simulate fetching plan options
            _planOptions.value = listOf(Any(), Any(), Any()) // Placeholder
            setLoading(false)
        }, 1500)
    }
}