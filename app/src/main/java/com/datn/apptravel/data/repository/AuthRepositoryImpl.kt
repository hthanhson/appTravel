package com.datn.apptravel.data.repository

import com.datn.apptravel.data.api.ApiService
import com.datn.apptravel.data.local.SessionManager
import com.datn.apptravel.domain.repository.AuthRepository
import kotlinx.coroutines.flow.first

class AuthRepositoryImpl(
    private val sessionManager: SessionManager
) : AuthRepository {

    override suspend fun login(email: String, password: String): Boolean {
        // TODO: Make actual API call when API is ready
        // Mock implementation for now
        sessionManager.saveAuthToken("mock_token")
        return true
    }
    override suspend fun register(email: String, password: String, name: String): Boolean {
        // TODO: Make actual API call when API is ready
        sessionManager.saveAuthToken("mock_token")
        return true
    }

    override suspend fun isLoggedIn(): Boolean {
        val token = sessionManager.authToken.first()
        return !token.isNullOrEmpty()
    }

    override suspend fun logout(): Boolean {
        sessionManager.clearSession()
        return true
    }
}