package com.datn.apptravel.ui.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * Base Activity class that provides common functionality for all activities
 */
abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel> : AppCompatActivity() {
    
    protected lateinit var binding: VB
    protected abstract val viewModel: VM
    
    /**
     * Abstract function to get ViewBinding instance
     */
    abstract fun getViewBinding(): VB
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        
        setupUI()
        observeData()
    }
    
    /**
     * Setup UI components
     */
    open fun setupUI() {
        // Override in child classes
    }
    
    /**
     * Observe LiveData from ViewModel
     */
    private fun observeData() {
        // Observe loading state
        viewModel.isLoading.observe(this) { isLoading ->
            handleLoading(isLoading)
        }
        
        // Observe error messages
        viewModel.error.observe(this) { errorMessage ->
            errorMessage?.let {
                showError(it)
                viewModel.clearError()
            }
        }
    }
    
    /**
     * Handle loading state
     */
    open fun handleLoading(isLoading: Boolean) {
        // Override in child classes to show/hide loading indicators
    }
    
    /**
     * Show error message
     */
    open fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}