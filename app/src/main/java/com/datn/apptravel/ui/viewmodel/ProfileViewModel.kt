package com.datn.apptravel.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.datn.apptravel.domain.repository.AuthRepository
import com.datn.apptravel.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class ProfileViewModel(private val authRepository: AuthRepository) : BaseViewModel() {

    fun getUserProfile() {
        // TODO: Implement with repository
        setLoading(false)
    }

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