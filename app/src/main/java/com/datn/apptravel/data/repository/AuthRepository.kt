package com.datn.apptravel.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.datn.apptravel.data.api.RetrofitClient
import com.datn.apptravel.data.model.*
import com.datn.apptravel.data.model.request.ForgotPasswordRequest
import com.datn.apptravel.data.model.request.GoogleTokenRequest
import com.datn.apptravel.data.model.request.LoginRequest
import com.datn.apptravel.data.model.request.SignUpRequest
import com.datn.apptravel.data.model.response.AuthResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import retrofit2.Response
import kotlin.math.log

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class AuthRepository(private val context: Context) {
    
    private val authApiService = RetrofitClient.authApiService
    
    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
        private val USER_ID_KEY = longPreferencesKey("user_id")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val FIRST_NAME_KEY = stringPreferencesKey("first_name")
        private val LAST_NAME_KEY = stringPreferencesKey("last_name")
        private val PROFILE_PICTURE_KEY = stringPreferencesKey("profile_picture")
    }
    
    // Sign up
    suspend fun signUp(request: SignUpRequest): Response<AuthResponse> {
        val response = authApiService.signUp(request)
        if (response.isSuccessful) {
            response.body()?.let { saveUserData(it) }
        }
        return response
    }
    
    // Login
    suspend fun login(request: LoginRequest): Response<AuthResponse> {
        val response = authApiService.login(request)
        if (response.isSuccessful) {
            response.body()?.let { saveUserData(it) }
        }
        return response
    }
    
    // Google Sign-In
    suspend fun googleSignIn(request: GoogleTokenRequest): Response<AuthResponse> {
        val response = authApiService.googleSignIn(request)
        if (response.isSuccessful) {
            response.body()?.let { saveUserData(it) }
        }
        return response
    }
    suspend fun forgotPassword(request : ForgotPasswordRequest):Response<AuthResponse>{
        val response = authApiService.forgotPassword(request)
        if(response.isSuccessful){
            response.body()?.let{body->
                Log.d("AuthRepository", "forgotPassword success")
            }

        }
        else {
            Log.e("AuthRepository", "Forgot password failed: ${response.errorBody()?.string()}")
        }
        return response
    }
    
    // Save user data to DataStore
    private suspend fun saveUserData(authResponse: AuthResponse) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = authResponse.accessToken
            preferences[USER_ID_KEY] = authResponse.userId
            preferences[EMAIL_KEY] = authResponse.email
            preferences[FIRST_NAME_KEY] = authResponse.firstName
            preferences[LAST_NAME_KEY] = authResponse.lastName
            authResponse.profilePicture?.let {
                preferences[PROFILE_PICTURE_KEY] = it
            }
        }
    }
    
    // Get auth token
    suspend fun getAuthToken(): String? {
        return context.dataStore.data.first()[TOKEN_KEY]
    }
    
    // Get user data
    fun getUserData(): Flow<User?> {
        return context.dataStore.data.map { preferences ->
            val userId = preferences[USER_ID_KEY]
            val email = preferences[EMAIL_KEY]
            val firstName = preferences[FIRST_NAME_KEY]
            val lastName = preferences[LAST_NAME_KEY]
            
            if (userId != null && email != null && firstName != null && lastName != null) {
                User(
                    id = userId,
                    email = email,
                    firstName = firstName,
                    lastName = lastName,
                    profilePicture = preferences[PROFILE_PICTURE_KEY]
                )
            } else {
                null
            }
        }
    }
    
    // Check if user is logged in
    suspend fun isLoggedIn(): Boolean {
        return getAuthToken() != null
    }
    
    // Logout
    suspend fun logout() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
