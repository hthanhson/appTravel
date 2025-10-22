package com.datn.apptravel.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.datn.apptravel.ui.base.BaseFragment
import com.datn.apptravel.ui.viewmodel.NotificationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment for displaying user notifications
 */
class NotificationFragment : BaseFragment<com.datn.apptravel.databinding.FragmentNotificationBinding, NotificationViewModel>() {
    
    override val viewModel: NotificationViewModel by viewModel()
    
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): com.datn.apptravel.databinding.FragmentNotificationBinding = 
        com.datn.apptravel.databinding.FragmentNotificationBinding.inflate(inflater, container, false)
    
    override fun setupUI() {
        // Setup UI components
        loadNotifications()
    }
    
    /**
     * Load notifications data
     */
    private fun loadNotifications() {
        viewModel.getNotifications()
    }
    
    override fun handleLoading(isLoading: Boolean) {
        // Show/hide loading indicator
        binding.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}