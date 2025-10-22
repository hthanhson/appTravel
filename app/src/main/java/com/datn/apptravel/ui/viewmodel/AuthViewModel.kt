package com.datn.apptravel.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.datn.apptravel.ui.base.BaseViewModel

/**
 * ViewModel for authentication operations
 */
class AuthViewModel : BaseViewModel() {
    
    // Sign in result
    private val _signInResult = MutableLiveData<Boolean>()
    val signInResult: LiveData<Boolean> = _signInResult
    
    // Sign up result
    private val _signUpResult = MutableLiveData<Boolean>()
    val signUpResult: LiveData<Boolean> = _signUpResult
    
    // Reset password result
    private val _resetPasswordResult = MutableLiveData<Boolean>()
    val resetPasswordResult: LiveData<Boolean> = _resetPasswordResult
    
    /**
     * Sign in with email and password
     */
    fun signIn(email: String, password: String) {
        setLoading(true)
        
        // Simulated API call with delay
        android.os.Handler().postDelayed({
            // Simulate successful sign in
            _signInResult.value = true
            setLoading(false)
        }, 2000)
    }
    
    /**
     * Sign up with user details
     */
    fun signUp(firstName: String, lastName: String, email: String, password: String) {
        setLoading(true)
        
        // Simulated API call with delay
        android.os.Handler().postDelayed({
            // Simulate successful sign up
            _signUpResult.value = true
            setLoading(false)
        }, 2000)
    }
    
    /**
     * Reset user password
     */
    fun resetPassword(email: String) {
        setLoading(true)
        
        // Simulated API call with delay
        android.os.Handler().postDelayed({
            // Simulate successful password reset
            _resetPasswordResult.value = true
            setLoading(false)
        }, 2000)
    }
}