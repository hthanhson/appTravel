package com.datn.apptravel.domain.model

/**
 * Data class for onboarding screen data
 */
data class OnboardingData(
    val imageRes: Int,
    val title: String,
    val description: String,
    val showBackButton: Boolean,
    val showNextButton: Boolean,
    val showStartButton: Boolean
)