package com.datn.apptravel.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.datn.apptravel.R
import com.datn.apptravel.ui.base.BaseFragment
import com.datn.apptravel.ui.viewmodel.TripsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TripsFragment : BaseFragment<com.datn.apptravel.databinding.FragmentTripsBinding, TripsViewModel>() {
    
    override val viewModel: TripsViewModel by viewModel()
    
    // Track currently selected tab
    private var currentTab = TAB_ONGOING
    
    companion object {
        private const val TAB_ONGOING = 0
        private const val TAB_PAST = 1
        private const val TAB_COMMUNITY = 2
        
        // Intent extra keys
        const val EXTRA_TRIP_ID = "extra_trip_id"
    }
    
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): com.datn.apptravel.databinding.FragmentTripsBinding = 
        com.datn.apptravel.databinding.FragmentTripsBinding.inflate(inflater, container, false)
    
    // Trip adapter
    private lateinit var tripAdapter: com.datn.apptravel.ui.adapter.TripAdapter
    
    override fun setupUI() {
        // Setup UI components
        setupAddTripButton()
        setupRecyclerViews()
        observeTrips()
        loadTrips()
    }
    
    private fun setupAddTripButton() {
        binding.btnAddTripNow.setOnClickListener {
            // Navigate to CreateTripActivity
            val intent = Intent(requireContext(), com.datn.apptravel.ui.trip.CreateTripActivity::class.java)
            startActivity(intent)
        }
        
        binding.tvViewAll?.setOnClickListener {
            // Show all past trips
        }
    }
    
    /**
     * Load trips data based on current tab
     */
    private fun loadTrips() {
        viewModel.getTrips()
    }
    
    /**
     * Refresh trips data
     */
    fun refreshTrips() {
        viewModel.getTrips()
    }
    
    /**
     * Setup RecyclerViews for Adventure and Past Trips
     */
    private fun setupRecyclerViews() {
        // Setup Adventure RecyclerView (horizontal)
        binding.rvAdventure.apply {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context, androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL, false)
        }
        
        // Setup Past Trips RecyclerView (horizontal)
        tripAdapter = com.datn.apptravel.ui.adapter.TripAdapter(
            emptyList()
        ) { trip ->
            navigateToTripDetail(trip)
        }
        
        binding.rvPastTrips.apply {
            adapter = tripAdapter
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context, androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL, false)
        }
    }
    
    /**
     * Navigate to trip detail screen
     */
    private fun navigateToTripDetail(trip: com.datn.apptravel.util.Trip) {
        val intent = Intent(requireContext(), com.datn.apptravel.ui.tripdetail.TripDetailActivity::class.java).apply {
            putExtra(EXTRA_TRIP_ID, trip.id)
        }
        startActivity(intent)
    }
    
    /**
     * Observe trips data from ViewModel
     */
    private fun observeTrips() {
        viewModel.trips.observe(viewLifecycleOwner) { trips ->
            // If trips exist, update the adapter and show RecyclerView
            if (trips.isNotEmpty()) {
                tripAdapter.updateTrips(trips)
                binding.rvPastTrips.visibility = View.VISIBLE
            } else {
                binding.rvPastTrips.visibility = View.GONE
            }
        }
    }
    
    override fun handleLoading(isLoading: Boolean) {
        // Show/hide loading indicator
        binding.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}