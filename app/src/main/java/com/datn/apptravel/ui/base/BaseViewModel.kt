package com.datn.apptravel.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Base ViewModel class that provides common functionality for all ViewModels
 */
abstract class BaseViewModel : ViewModel() {
    
    // Loading state
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    // Error handling
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error
    
    /**
     * Set loading state
     */
    protected fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }
    
    /**
     * Set error message
     */
    protected fun setError(message: String?) {
        _error.value = message
    }
    
    /**
     * Clear error message
     */
    fun clearError() {
        _error.value = null
    }
}