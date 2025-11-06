package com.datn.apptravel.domain.repository

interface AuthRepository {
    suspend fun login(email: String, password: String): Boolean

    suspend fun register(email: String, password: String, name: String): Boolean

    suspend fun isLoggedIn(): Boolean

    suspend fun logout(): Boolean
}