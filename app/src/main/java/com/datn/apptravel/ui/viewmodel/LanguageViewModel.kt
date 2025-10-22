package com.datn.apptravel.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.datn.apptravel.R
import com.datn.apptravel.data.local.SessionManager
import com.datn.apptravel.data.model.Language
import com.datn.apptravel.ui.base.BaseViewModel
import kotlinx.coroutines.launch

/**
 * ViewModel for language selection screen
 */
class LanguageViewModel(private val sessionManager: SessionManager) : BaseViewModel() {
    
    private val _languages = MutableLiveData<List<Language>>()
    val languages: LiveData<List<Language>> = _languages
    
    private val _selectedLanguage = MutableLiveData<Language?>()
    val selectedLanguage: LiveData<Language?> = _selectedLanguage
    
    init {
        loadLanguages()
    }
    
    /**
     * Load available languages
     */
    private fun loadLanguages() {
        val languageList = listOf(
            Language("English", R.drawable.flag_english, true, "en"),
            Language("France", R.drawable.flag_france, false, "fr"),
            Language("China", R.drawable.flag_china, false, "zh"),
            Language("Indonesia", R.drawable.flag_indonesia, false, "id"),
            Language("India", R.drawable.flag_india, false, "hi"),
            Language("Germany", R.drawable.flag_germany, false, "de")
        )
        
        _languages.value = languageList
        _selectedLanguage.value = languageList.first { it.isSelected }
    }
    
    /**
     * Select a language
     * @param language The language to select
     */
    fun selectLanguage(language: Language) {
        val updatedList = _languages.value?.map {
            it.copy(isSelected = it.name == language.name)
        } ?: return
        
        _languages.value = updatedList
        _selectedLanguage.value = language
        
        // Save selected language
        viewModelScope.launch {
            try {
                sessionManager.saveSelectedLanguage(language.languageCode)
            } catch (e: Exception) {
                setError("Error saving language: ${e.message}")
            }
        }
    }
}