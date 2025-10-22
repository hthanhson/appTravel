package com.datn.apptravel.ui.viewmodel

import com.datn.apptravel.ui.base.BaseViewModel

/**
 * ViewModel for GuidesFragment
 */
class GuidesViewModel : BaseViewModel() {
    
    /**
     * Get guides data
     */
    fun getGuides() {
        // TODO: Implement with repository
        setLoading(false)
    }
}