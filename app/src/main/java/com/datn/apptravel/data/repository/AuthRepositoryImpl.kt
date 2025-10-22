package com.datn.apptravel.data.repository

import com.datn.apptravel.data.api.ApiService
import com.datn.apptravel.data.local.SessionManager
import com.datn.apptravel.domain.repository.AuthRepository
import kotlinx.coroutines.flow.first

/**
 * Implementation of AuthRepository interface
 */
class AuthRepositoryImpl(
    private val sessionManager: SessionManager
) : AuthRepository {
    
    /**
     * Login user with email and password
     */
    override suspend fun login(email: String, password: String): Boolean {
        // TODO: Make actual API call when API is ready
        // Mock implementation for now
        sessionManager.saveAuthToken("mock_token")
        return true
    }
    
    /**
     * Register new user
     */
    override suspend fun register(email: String, password: String, name: String): Boolean {
        // TODO: Make actual API call when API is ready
        // Mock implementation for now
        sessionManager.saveAuthToken("mock_token")
        return true
    }
    
    /**
     * Check if user is logged in
     */
    override suspend fun isLoggedIn(): Boolean {
        val token = sessionManager.authToken.first()
        return !token.isNullOrEmpty()
    }
    
    /**
     * Logout current user
     */
    override suspend fun logout(): Boolean {
        sessionManager.clearSession()
        return true
    }
}