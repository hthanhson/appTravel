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

/**
 * Fragment for displaying user trips
 */
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
        setupTabListeners()
        setupAddTripButton()
        setupRecyclerView()
        observeTrips()
        loadTrips()
    }
    
    private fun setupTabListeners() {
        binding.apply {
            btnOngoingTrips.setOnClickListener {
                if (currentTab != TAB_ONGOING) {
                    selectTab(TAB_ONGOING)
                    loadTrips()
                }
            }
            
            btnPastTrips.setOnClickListener {
                if (currentTab != TAB_PAST) {
                    selectTab(TAB_PAST)
                    loadTrips()
                }
            }
            
            btnCommunity.setOnClickListener {
                if (currentTab != TAB_COMMUNITY) {
                    selectTab(TAB_COMMUNITY)
                    loadTrips()
                }
            }
        }
    }
    
    private fun setupAddTripButton() {
        binding.btnAddTrip.setOnClickListener {
            // Intent can be implemented later when CreateTripActivity is available
            // For now, just toggle the empty state for demo purposes
            showEmptyState(false)
        }
    }
    
    private fun selectTab(tabIndex: Int) {
        currentTab = tabIndex
        
        // Reset all tabs
        binding.apply {
            btnOngoingTrips.setBackgroundResource(0)
            btnOngoingTrips.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey_600))
            
            btnPastTrips.setBackgroundResource(0)
            btnPastTrips.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey_600))
            
            btnCommunity.setBackgroundResource(0)
            btnCommunity.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey_600))
            
            // Set the selected tab
            when (tabIndex) {
                TAB_ONGOING -> {
                    btnOngoingTrips.setBackgroundResource(R.drawable.tab_selected_background)
                    btnOngoingTrips.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
                    tvEmptyStateTitle.text = "You don't have any Ongoing Trips yet!"
                }
                TAB_PAST -> {
                    btnPastTrips.setBackgroundResource(R.drawable.tab_selected_background)
                    btnPastTrips.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
                    tvEmptyStateTitle.text = "You don't have any Past Trips yet!"
                }
                TAB_COMMUNITY -> {
                    btnCommunity.setBackgroundResource(R.drawable.tab_selected_background)
                    btnCommunity.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
                    tvEmptyStateTitle.text = "No Community Trips available!"
                }
            }
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
     * Setup RecyclerView for trips
     */
    private fun setupRecyclerView() {
        tripAdapter = com.datn.apptravel.ui.adapter.TripAdapter(
            emptyList()
        ) { trip ->
            // Navigate to TripDetailActivity with the selected trip
            navigateToTripDetail(trip)
        }
        
        binding.rvTrips.apply {
            adapter = tripAdapter
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
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
            // If trips list is empty, show empty state
            showEmptyState(trips.isEmpty())
            
            // If trips exist, update the adapter with new data
            if (trips.isNotEmpty()) {
                tripAdapter.updateTrips(trips)
            }
        }
    }
    
    /**
     * Show or hide empty state
     */
    private fun showEmptyState(show: Boolean) {
        binding.apply {
            if (show) {
                emptyStateContainer.visibility = View.VISIBLE
                rvTrips.visibility = View.GONE
            } else {
                emptyStateContainer.visibility = View.GONE
                rvTrips.visibility = View.VISIBLE
            }
        }
    }
    
    override fun handleLoading(isLoading: Boolean) {
        // Show/hide loading indicator
        binding.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}