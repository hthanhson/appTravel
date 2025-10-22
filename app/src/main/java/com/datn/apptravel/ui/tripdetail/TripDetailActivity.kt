package com.datn.apptravel.ui.tripdetail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.datn.apptravel.R
import com.datn.apptravel.databinding.ActivityTripDetailBinding
import com.datn.apptravel.ui.adapter.ScheduleDayAdapter
import com.datn.apptravel.ui.fragment.TripsFragment
import com.datn.apptravel.ui.planselection.PlanSelectionActivity
import com.datn.apptravel.ui.viewmodel.TripDetailViewModel
import com.datn.apptravel.util.Trip
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Activity for displaying trip details
 */
class TripDetailActivity : AppCompatActivity() {
    
    private val viewModel: TripDetailViewModel by viewModel()
    private var tripId: String? = null
    private lateinit var binding: ActivityTripDetailBinding
    private lateinit var scheduleDayAdapter: ScheduleDayAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Get trip ID from intent
        tripId = intent.getStringExtra(TripsFragment.EXTRA_TRIP_ID)
        
        setupUI()
        setupObservers()
        
        // Load trip details
        tripId?.let { 
            viewModel.getTripDetails(it) 
        } ?: run {
            // If no trip ID, show empty state
            binding.emptyPlansContainer.visibility = View.VISIBLE
            binding.recyclerViewSchedule.visibility = View.GONE
        }
    }
    
    private fun setupUI() {
        // Setup back button
        binding.btnBack.setOnClickListener {
            finish()
        }
        
        // Setup menu button for opening menu
        binding.btnMenu.setOnClickListener {
            showTripMenu()
        }
        
        // Setup add new plan button
        binding.btnAddNewPlan.setOnClickListener {
            navigateToPlanSelection()
        }
        
        // Setup save button
        binding.btnSaveTrip.setOnClickListener {
            // Toggle save state
            // For now, just show a toast
            android.widget.Toast.makeText(this, "Trip saved!", android.widget.Toast.LENGTH_SHORT).show()
        }
        
        // Setup schedule RecyclerView
        setupRecyclerView()
    }
    
    private fun setupRecyclerView() {
        scheduleDayAdapter = ScheduleDayAdapter(emptyList())
        binding.recyclerViewSchedule.apply {
            adapter = scheduleDayAdapter
            layoutManager = LinearLayoutManager(this@TripDetailActivity)
        }
    }
    
    private fun setupObservers() {
        // Observe trip details
        viewModel.tripDetails.observe(this) { trip ->
            updateUI(trip)
        }
        
        // Observe schedule days
        viewModel.scheduleDays.observe(this) { scheduleDays ->
            if (scheduleDays.isNotEmpty()) {
                scheduleDayAdapter.updateScheduleDays(scheduleDays)
                binding.emptyPlansContainer.visibility = View.GONE
                binding.recyclerViewSchedule.visibility = View.VISIBLE
            } else {
                binding.emptyPlansContainer.visibility = View.VISIBLE
                binding.recyclerViewSchedule.visibility = View.GONE
            }
        }
    }
    
    /**
     * Update UI with trip details
     */
    private fun updateUI(trip: Trip?) {
        if (trip == null) {
            binding.tvTitle.text = getString(R.string.app_name)
            return
        }
        
        // Set trip details
        binding.apply {
            tvTitle.text = trip.title
            tvTripName.text = trip.title
            tvTripDate.text = "${trip.startDate} - ${trip.endDate}"
            tvTripCost.text = "75.000.000đ" // Placeholder price
            
            // If trip has an image URL, load it here
            // For now, we'll use a placeholder
        }
    }
    
    /**
     * Show trip menu options
     */
    private fun showTripMenu() {
        val popupMenu = androidx.appcompat.widget.PopupMenu(this, binding.btnMenu)
        popupMenu.menuInflater.inflate(R.menu.trip_detail_menu, popupMenu.menu)
        
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_edit_trip -> {
                    // Open trip edit
                    android.widget.Toast.makeText(this, "Edit trip coming soon", android.widget.Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_delete_trip -> {
                    // Show delete confirmation
                    showDeleteConfirmation()
                    true
                }
                R.id.action_view_collection -> {
                    // Share trip
                    shareTrip()
                    true
                }
                else -> false
            }
        }
        
        popupMenu.show()
    }

    /**
     * Show delete confirmation dialog
     */
    private fun showDeleteConfirmation() {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Delete Trip")
            .setMessage("Are you sure you want to delete this trip?")
            .setPositiveButton("Delete") { _, _ ->
                // Delete the trip
                android.widget.Toast.makeText(this, "Trip deleted!", android.widget.Toast.LENGTH_SHORT).show()
                finish()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    /**
     * Share trip with others
     */
    private fun shareTrip() {
        val trip = viewModel.tripDetails.value
        val shareText = if (trip != null) {
            "Check out my trip to ${trip.destination}! From ${trip.startDate} to ${trip.endDate}"
        } else {
            "Check out my awesome trip!"
        }
        
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "Share trip via"))
    }
    
    /**
     * Navigate to Plan Selection screen
     */
    private fun navigateToPlanSelection() {
        val intent = Intent(this, PlanSelectionActivity::class.java)
        intent.putExtra("tripId", tripId)
        startActivity(intent)
    }
}