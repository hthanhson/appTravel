package com.datn.apptravel.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.datn.apptravel.R
import com.datn.apptravel.databinding.ActivityLanguageSelectionBinding
import com.datn.apptravel.ui.adapter.LanguageAdapter
import com.datn.apptravel.ui.base.BaseActivity
import com.datn.apptravel.ui.viewmodel.LanguageViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LanguageSelectionActivity : BaseActivity<ActivityLanguageSelectionBinding, LanguageViewModel>() {
    
    override val viewModel: LanguageViewModel by viewModel()
    
    private lateinit var languageAdapter: LanguageAdapter
    
    override fun getViewBinding(): ActivityLanguageSelectionBinding = 
        ActivityLanguageSelectionBinding.inflate(layoutInflater)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Hide action bar
        supportActionBar?.hide()
    }
    
    override fun setupUI() {
        setupRecyclerView()
        setupClickListeners()
        observeLanguages()
    }
    
    private fun setupRecyclerView() {
        languageAdapter = LanguageAdapter(emptyList()) { selectedLanguage ->
            viewModel.selectLanguage(selectedLanguage)
        }
        
        binding.recyclerViewLanguages.apply {
            layoutManager = LinearLayoutManager(this@LanguageSelectionActivity)
            adapter = languageAdapter
        }
    }
    
    private fun setupClickListeners() {
        binding.btnDone.setOnClickListener {
            // Navigate to OnboardingActivity
            if (viewModel.selectedLanguage.value != null) {
                startActivity(Intent(this, OnboardingActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Please select a language", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun observeLanguages() {
        viewModel.languages.observe(this) { languages ->
            languageAdapter = LanguageAdapter(languages) { selectedLanguage ->
                viewModel.selectLanguage(selectedLanguage)
            }
            binding.recyclerViewLanguages.adapter = languageAdapter
        }
    }
    
    override fun handleLoading(isLoading: Boolean) {
        // Show loading indicator if needed
        binding.btnDone.isEnabled = !isLoading
    }
}