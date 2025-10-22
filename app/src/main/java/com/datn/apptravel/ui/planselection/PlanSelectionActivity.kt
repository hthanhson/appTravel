package com.datn.apptravel.ui.planselection

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.datn.apptravel.R
import com.datn.apptravel.databinding.ActivityPlanSelectionBinding
import com.datn.apptravel.ui.fragment.TripsFragment
import com.datn.apptravel.ui.plandetail.BoatDetailActivity
import com.datn.apptravel.ui.plandetail.FlightDetailActivity
import com.datn.apptravel.ui.plandetail.LodgingDetailActivity
import com.datn.apptravel.ui.plandetail.TrainDetailActivity
import com.datn.apptravel.ui.viewmodel.PlanViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Activity for selecting trip plans (flights, lodging, etc.)
 */
class PlanSelectionActivity : AppCompatActivity() {
    
    private val viewModel: PlanViewModel by viewModel()
    private var tripId: String? = null
    private lateinit var binding: ActivityPlanSelectionBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlanSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Get trip ID from intent - use the key "tripId" as passed from TripDetailActivity
        tripId = intent.getStringExtra("tripId") ?: intent.getStringExtra(TripsFragment.EXTRA_TRIP_ID)
        
        setupUI()
    }
    
    /**
     * Set up the UI elements and click listeners
     */
    private fun setupUI() {
        // Set up back button
        binding.btnBack.setOnClickListener {
            finish()
        }
        
        // Set up plan options click listeners
        setupPlanClickListeners()
    }
    
    /**
     * Set up click listeners for plan options
     */
    private fun setupPlanClickListeners() {
        // Previously used options
        
        // Lodging option
        binding.layoutLodging.setOnClickListener {
            navigateToLodgingDetail()
        }
        
        // Flight option
        binding.layoutFlight.setOnClickListener {
            navigateToFlightDetail()
        }
        
        // Restaurant option
        binding.layoutRestaurant.setOnClickListener {
            // For now, just show a toast
            android.widget.Toast.makeText(this, "Restaurant feature coming soon!", android.widget.Toast.LENGTH_SHORT).show()
        }
        
        // More options
        
        // Tour option
        binding.layoutTour?.setOnClickListener {
            android.widget.Toast.makeText(this, "Tour feature coming soon!", android.widget.Toast.LENGTH_SHORT).show()
        }
        
        // Car Rental option
        binding.layoutCarRental?.setOnClickListener {
            android.widget.Toast.makeText(this, "Car Rental feature coming soon!", android.widget.Toast.LENGTH_SHORT).show()
        }
        
        // Activity option
        binding.layoutActivity?.setOnClickListener {
            android.widget.Toast.makeText(this, "Activities feature coming soon!", android.widget.Toast.LENGTH_SHORT).show()
        }

        // Boat option
        binding.layoutBoat?.setOnClickListener {
            navigateToBoatDetail()
        }
        
        // Train option
        binding.layoutTrain?.setOnClickListener {
            navigateToTrainDetail()
        }

        // Meeting option
        binding.layoutMeeting?.setOnClickListener {
            android.widget.Toast.makeText(this, "Meeting feature coming soon!", android.widget.Toast.LENGTH_SHORT).show()
        }
        
        // Note option
        binding.layoutNote?.setOnClickListener {
            android.widget.Toast.makeText(this, "Note feature coming soon!", android.widget.Toast.LENGTH_SHORT).show()
        }

        // Theater option
        binding.layoutTheater?.setOnClickListener {
            android.widget.Toast.makeText(this, "Theater feature coming soon!", android.widget.Toast.LENGTH_SHORT).show()
        }

        // Shopping option
        binding.layoutShopping?.setOnClickListener {
            android.widget.Toast.makeText(this, "Shopping feature coming soon!", android.widget.Toast.LENGTH_SHORT).show()
        }

        // Concert option
        binding.layoutConcert?.setOnClickListener {
            android.widget.Toast.makeText(this, "Concert feature coming soon!", android.widget.Toast.LENGTH_SHORT).show()
        }
    }
    
    /**
     * Navigate to Flight Detail screen
     */
    private fun navigateToFlightDetail() {
        val intent = Intent(this, FlightDetailActivity::class.java)
        intent.putExtra("tripId", tripId)
        startActivity(intent)
    }
    
    /**
     * Navigate to Lodging Detail screen
     */
    private fun navigateToLodgingDetail() {
        val intent = Intent(this, LodgingDetailActivity::class.java)
        intent.putExtra("tripId", tripId)
        startActivity(intent)
    }
    
    /**
     * Navigate to Boat Detail screen
     */
    private fun navigateToBoatDetail() {
        val intent = Intent(this, BoatDetailActivity::class.java)
        intent.putExtra("tripId", tripId)
        startActivity(intent)
    }
    
    /**
     * Navigate to Train Detail screen
     */
    private fun navigateToTrainDetail() {
        val intent = Intent(this, TrainDetailActivity::class.java)
        intent.putExtra("tripId", tripId)
        startActivity(intent)
    }
}