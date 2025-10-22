package com.datn.apptravel.ui.viewmodel

import com.datn.apptravel.ui.base.BaseViewModel

/**
 * ViewModel for NotificationFragment
 */
class NotificationViewModel : BaseViewModel() {
    
    /**
     * Get notifications data
     */
    fun getNotifications() {
        // TODO: Implement with repository
        setLoading(false)
    }
}