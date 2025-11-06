package com.datn.apptravel.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
class SessionManager(private val context: Context) {
    
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session_prefs")
        private val AUTH_TOKEN = stringPreferencesKey("auth_token")
        private val SELECTED_LANGUAGE = stringPreferencesKey("selected_language")
    }
    suspend fun saveAuthToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[AUTH_TOKEN] = token
        }
    }
    val authToken: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[AUTH_TOKEN]
    }
    suspend fun saveSelectedLanguage(languageCode: String) {
        context.dataStore.edit { preferences ->
            preferences[SELECTED_LANGUAGE] = languageCode
        }
    }

    val selectedLanguage: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[SELECTED_LANGUAGE]
    }
    suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}