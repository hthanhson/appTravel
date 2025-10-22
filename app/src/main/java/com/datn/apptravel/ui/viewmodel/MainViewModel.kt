package com.datn.apptravel.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.datn.apptravel.domain.repository.AuthRepository
import com.datn.apptravel.ui.base.BaseViewModel
import kotlinx.coroutines.launch

/**
 * ViewModel for MainActivity
 */
class MainViewModel(private val authRepository: AuthRepository) : BaseViewModel() {
    
    private val _isUserLoggedIn = MutableLiveData<Boolean>()
    val isUserLoggedIn: LiveData<Boolean> = _isUserLoggedIn
    
    init {
        checkLoginStatus()
    }
    
    /**
     * Check if user is logged in
     */
    fun checkLoginStatus() {
        viewModelScope.launch {
            try {
                val isLoggedIn = authRepository.isLoggedIn()
                _isUserLoggedIn.value = isLoggedIn
            } catch (e: Exception) {
                setError("Error checking login status: ${e.message}")
                _isUserLoggedIn.value = false
            }
        }
    }
    
    /**
     * Logout user
     */
    fun logout() {
        viewModelScope.launch {
            setLoading(true)
            try {
                val success = authRepository.logout()
                if (success) {
                    _isUserLoggedIn.value = false
                } else {
                    setError("Failed to logout")
                }
            } catch (e: Exception) {
                setError("Error logging out: ${e.message}")
            } finally {
                setLoading(false)
            }
        }
    }
}