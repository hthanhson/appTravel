package com.datn.apptravel.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Manager for handling user session data using DataStore
 */
class SessionManager(private val context: Context) {
    
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session_prefs")
        private val AUTH_TOKEN = stringPreferencesKey("auth_token")
        private val SELECTED_LANGUAGE = stringPreferencesKey("selected_language")
    }
    
    /**
     * Save authentication token to DataStore
     */
    suspend fun saveAuthToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[AUTH_TOKEN] = token
        }
    }
    
    /**
     * Get authentication token from DataStore
     */
    val authToken: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[AUTH_TOKEN]
    }
    
    /**
     * Save selected language code to DataStore
     */
    suspend fun saveSelectedLanguage(languageCode: String) {
        context.dataStore.edit { preferences ->
            preferences[SELECTED_LANGUAGE] = languageCode
        }
    }
    
    /**
     * Get selected language code from DataStore
     */
    val selectedLanguage: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[SELECTED_LANGUAGE]
    }
    
    /**
     * Clear all session data
     */
    suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}