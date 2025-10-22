package com.datn.apptravel.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.datn.apptravel.databinding.FragmentProfileBinding
import com.datn.apptravel.ui.base.BaseFragment
import com.datn.apptravel.ui.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment for displaying user profile
 */
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {
    
    override val viewModel: ProfileViewModel by viewModel()
    
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding = 
        FragmentProfileBinding.inflate(inflater, container, false)
    
    override fun setupUI() {
        // Setup UI components
        loadUserProfile()
        setupLogoutButton()
    }
    
    /**
     * Load user profile data
     */
    private fun loadUserProfile() {
        viewModel.getUserProfile()
    }
    
    /**
     * Setup logout button
     */
    private fun setupLogoutButton() {
        binding.btnLogout?.setOnClickListener {
            viewModel.logout()
        }
    }
    
    override fun handleLoading(isLoading: Boolean) {
        // Show/hide loading indicator
        binding.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}