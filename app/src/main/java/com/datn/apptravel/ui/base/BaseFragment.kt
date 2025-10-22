package com.datn.apptravel.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Base Fragment class that provides common functionality for all fragments
 */
abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment() {
    
    private var _binding: VB? = null
    protected val binding get() = _binding!!
    
    protected abstract val viewModel: VM
    
    /**
     * Abstract function to get ViewBinding instance
     */
    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding(inflater, container)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
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
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            handleLoading(isLoading)
        }
        
        // Observe error messages
        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
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
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}