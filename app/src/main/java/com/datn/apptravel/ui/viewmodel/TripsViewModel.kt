package com.datn.apptravel.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.datn.apptravel.ui.activity.MainActivity
import com.datn.apptravel.ui.base.BaseViewModel
import com.datn.apptravel.util.Trip

class TripsViewModel : BaseViewModel() {
    
    private val _trips = MutableLiveData<List<Trip>>()
    val trips: LiveData<List<Trip>> = _trips

    fun getTrips() {
        setLoading(true)
        
        // Simulate network call delay
        android.os.Handler().postDelayed({
            // Get trips from TripManager
            val allTrips = MainActivity.tripManager.getAllTrips()
            _trips.value = allTrips
            setLoading(false)
        }, 1000)
    }
}