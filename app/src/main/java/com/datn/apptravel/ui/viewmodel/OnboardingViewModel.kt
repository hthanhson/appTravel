package com.datn.apptravel.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.datn.apptravel.R
import com.datn.apptravel.domain.model.OnboardingData
import com.datn.apptravel.ui.base.BaseViewModel

/**
 * ViewModel for onboarding screens
 */
class OnboardingViewModel : BaseViewModel() {
    
    private val _onboardingData = MutableLiveData<List<OnboardingData>>()
    val onboardingData: LiveData<List<OnboardingData>> = _onboardingData
    
    private val _currentPosition = MutableLiveData(0)
    val currentPosition: LiveData<Int> = _currentPosition
    
    init {
        loadOnboardingData()
    }
    
    /**
     * Load onboarding data
     */
    private fun loadOnboardingData() {
        val data = listOf(
            OnboardingData(
                imageRes = R.drawable.onboarding_1,
                title = "Welcome\nto Trip Planner",
                description = "The ultimate travel app to make planning easy, discover amazing destinations.",
                showBackButton = false,
                showNextButton = true,
                showStartButton = false
            ),
            OnboardingData(
                imageRes = R.drawable.onboarding_2,
                title = "We are the first",
                description = "The ultimate travel app to make planning easy, discover amazing destinations and enjoy to the fullest.",
                showBackButton = true,
                showNextButton = true,
                showStartButton = false
            ),
            OnboardingData(
                imageRes = R.drawable.onboarding_3,
                title = "Get started",
                description = "The ultimate travel app to make planning easy, discover amazing destinations and enjoy to the fullest.",
                showBackButton = true,
                showNextButton = false,
                showStartButton = true
            )
        )
        
        _onboardingData.value = data
    }
    
    /**
     * Navigate to the next onboarding page
     */
    fun navigateToNextPage() {
        val currentPos = _currentPosition.value ?: 0
        val maxPosition = _onboardingData.value?.size?.minus(1) ?: 0
        
        if (currentPos < maxPosition) {
            _currentPosition.value = currentPos + 1
        }
    }
    
    /**
     * Navigate to the previous onboarding page
     */
    fun navigateToPreviousPage() {
        val currentPos = _currentPosition.value ?: 0
        
        if (currentPos > 0) {
            _currentPosition.value = currentPos - 1
        }
    }
    
    /**
     * Update current position
     */
    fun updatePosition(position: Int) {
        _currentPosition.value = position
    }
}