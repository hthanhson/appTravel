package com.datn.apptravel.domain.repository

/**
 * Interface for authentication related repository operations
 */
interface AuthRepository {
    /**
     * Login user with email and password
     */
    suspend fun login(email: String, password: String): Boolean
    
    /**
     * Register new user
     */
    suspend fun register(email: String, password: String, name: String): Boolean
    
    /**
     * Check if user is logged in
     */
    suspend fun isLoggedIn(): Boolean
    
    /**
     * Logout current user
     */
    suspend fun logout(): Boolean
}