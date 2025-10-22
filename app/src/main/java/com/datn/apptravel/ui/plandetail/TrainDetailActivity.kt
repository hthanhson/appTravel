package com.datn.apptravel.ui.plandetail

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.datn.apptravel.databinding.ActivityTrainDetailBinding
import com.datn.apptravel.ui.viewmodel.TrainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

/**
 * Activity for train details and booking
 */
class TrainDetailActivity : AppCompatActivity() {
    
    private val viewModel: TrainViewModel by viewModel()
    private var tripId: String? = null
    private lateinit var binding: ActivityTrainDetailBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrainDetailBinding.inflate(layoutInflater)
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
            saveTrainDetails()
        }
        
        // Setup time pickers
        setupTimePickers()
    }
    
    private fun setupObservers() {
        // Observe save train result
        viewModel.saveTrainResult.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Train details added to your trip", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to add train details", Toast.LENGTH_SHORT).show()
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
     * Save train details to trip
     */
    private fun saveTrainDetails() {
        // Validate inputs
        if (binding.etTrainName.text.isNullOrEmpty() || binding.etDepartureTime.text.isNullOrEmpty() 
            || binding.etArrivalTime.text.isNullOrEmpty() || binding.etDepartureStation.text.isNullOrEmpty() 
            || binding.etArrivalStation.text.isNullOrEmpty()) {
            Toast.makeText(this, "Please fill out required fields", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Add train details to trip
        tripId?.let { id ->
            // Create train details from form inputs
            val trainDetails = mapOf(
                "trainName" to binding.etTrainName.text.toString(),
                "coach" to binding.etCoach.text.toString(),
                "seat" to binding.etSeat.text.toString(),
                "departureTime" to binding.etDepartureTime.text.toString(),
                "departureStation" to binding.etDepartureStation.text.toString(),
                "departureLocation" to binding.etDepartureLocation.text.toString(),
                "arrivalTime" to binding.etArrivalTime.text.toString(),
                "arrivalStation" to binding.etArrivalStation.text.toString(),
                "arrivalLocation" to binding.etArrivalLocation.text.toString(),
                "expense" to binding.etExpense.text.toString().ifEmpty { "0" }
            )
            
            viewModel.saveTrain(id, trainDetails.toString())
        } ?: run {
            Toast.makeText(this, "Trip ID is missing", Toast.LENGTH_SHORT).show()
        }
    }
}