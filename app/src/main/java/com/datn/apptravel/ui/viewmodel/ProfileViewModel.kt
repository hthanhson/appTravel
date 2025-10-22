package com.datn.apptravel.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.datn.apptravel.domain.repository.AuthRepository
import com.datn.apptravel.ui.base.BaseViewModel
import kotlinx.coroutines.launch

/**
 * ViewModel for ProfileFragment
 */
class ProfileViewModel(private val authRepository: AuthRepository) : BaseViewModel() {
    
    /**
     * Get user profile data
     */
    fun getUserProfile() {
        // TODO: Implement with repository
        setLoading(false)
    }
    
    /**
     * Logout user
     */
    fun logout() {
        viewModelScope.launch {
            setLoading(true)
            try {
                val success = authRepository.logout()
                if (!success) {
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