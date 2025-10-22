package com.datn.apptravel.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.datn.apptravel.domain.repository.AuthRepository
import com.datn.apptravel.ui.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ViewModel for SplashActivity
 */
class SplashViewModel(private val authRepository: AuthRepository) : BaseViewModel() {
    
    private val _navigateToNext = MutableLiveData<SplashNavigationState>()
    val navigateToNext: LiveData<SplashNavigationState> = _navigateToNext
    
    /**
     * Initialize splash screen logic
     * @param splashDelay Delay in milliseconds before navigation
     */
    fun initSplash(splashDelay: Long = 2000) {
        viewModelScope.launch {
            setLoading(true)
            delay(splashDelay)
            
            try {
                val isFirstLaunch = true // For testing navigation flow
                
                // Always navigate to onboarding for testing purposes
                _navigateToNext.value = SplashNavigationState.ToOnboarding
            } catch (e: Exception) {
                setError("Error checking login status: ${e.message}")
                _navigateToNext.value = SplashNavigationState.ToOnboarding
            } finally {
                setLoading(false)
            }
        }
    }
    
    /**
     * Sealed class for navigation states from splash screen
     */
    sealed class SplashNavigationState {
        data object ToLanguageSelection : SplashNavigationState()
        data object ToOnboarding : SplashNavigationState()
        data object ToMain : SplashNavigationState()
    }
}