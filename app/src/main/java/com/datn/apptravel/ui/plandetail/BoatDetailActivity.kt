package com.datn.apptravel.ui.plandetail

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.datn.apptravel.databinding.ActivityBoatDetailBinding
import com.datn.apptravel.ui.viewmodel.BoatViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

class BoatDetailActivity : AppCompatActivity() {
    
    private val viewModel: BoatViewModel by viewModel()
    private var tripId: String? = null
    private lateinit var binding: ActivityBoatDetailBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoatDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        tripId = intent.getStringExtra("tripId")
        
        setupUI()
        setupObservers()
    }
    
    private fun setupUI() {
        // Setup back button
        binding.btnBack.setOnClickListener {
            finish()
        }
        
        // Setup save button
        binding.btnSave.setOnClickListener {
            saveBoatDetails()
        }
        
        // Setup time pickers
        setupTimePickers()
    }
    
    private fun setupObservers() {
        // Observe save boat result
        viewModel.saveBoatResult.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Boat details added to your trip", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to add boat details", Toast.LENGTH_SHORT).show()
            }
        }
        
        // Observe loading state
        viewModel.isLoading.observe(this) { isLoading ->
            // Show/hide loading indicator if needed
        }
    }
    
    private fun setupTimePickers() {
        // Departure time picker
        binding.etDepartureTime.setOnClickListener {
            showTimePicker(binding.etDepartureTime)
        }
        
        // Arrival time picker
        binding.etArrivalTime.setOnClickListener {
            showTimePicker(binding.etArrivalTime)
        }
    }
    
    private fun showTimePicker(targetEditText: android.widget.EditText) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        
        TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
            targetEditText.setText(formattedTime)
        }, hour, minute, true).show()
    }
    
    /**
     * Save boat details to trip
     */
    private fun saveBoatDetails() {
        // Validate inputs
        if (binding.etBoatName.text.isNullOrEmpty() || binding.etDepartureTime.text.isNullOrEmpty() 
            || binding.etArrivalTime.text.isNullOrEmpty()) {
            Toast.makeText(this, "Please fill out required fields", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Add boat details to trip
        tripId?.let { id ->
            // Create boat details from form inputs
            val boatDetails = mapOf(
                "boatName" to binding.etBoatName.text.toString(),
                "coach" to binding.etCoach.text.toString(),
                "seat" to binding.etSeat.text.toString(),
                "departureTime" to binding.etDepartureTime.text.toString(),
                "departureLocation" to binding.etDepartureLocation.text.toString(),
                "arrivalTime" to binding.etArrivalTime.text.toString(),
                "arrivalLocation" to binding.etArrivalLocation.text.toString(),
                "expense" to binding.etExpense.text.toString().ifEmpty { "0" }
            )
            
            viewModel.saveBoat(id, boatDetails.toString())
        } ?: run {
            Toast.makeText(this, "Trip ID is missing", Toast.LENGTH_SHORT).show()
        }
    }
}