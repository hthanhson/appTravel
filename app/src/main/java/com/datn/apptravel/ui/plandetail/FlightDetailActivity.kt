package com.datn.apptravel.ui.plandetail

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.datn.apptravel.R
import com.datn.apptravel.databinding.ActivityFlightDetailBinding
import com.datn.apptravel.ui.viewmodel.FlightViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

class FlightDetailActivity : AppCompatActivity() {
    
    private val viewModel: FlightViewModel by viewModel()
    private var tripId: String? = null
    private lateinit var binding: ActivityFlightDetailBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlightDetailBinding.inflate(layoutInflater)
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
            saveFlightDetails()
        }
        
        // Setup date pickers
        setupDatePickers()
        
        // Setup time pickers
        setupTimePickers()
    }
    
    private fun setupObservers() {
        // Observe add flight result
        viewModel.addFlightResult.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Flight added to your trip", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to add flight", Toast.LENGTH_SHORT).show()
            }
        }
        
        // Observe loading state
        viewModel.isLoading.observe(this) { isLoading ->
            // Show/hide loading indicator if needed
        }
    }
    
    private fun setupDatePickers() {
        // Departure date picker
        binding.etDepartureDate.setOnClickListener {
            showDatePicker(binding.etDepartureDate)
        }
        
        // Arrival date picker
        binding.etArrivalDate.setOnClickListener {
            showDatePicker(binding.etArrivalDate)
        }
    }
    
    private fun setupTimePickers() {
        // Check-in time picker
        binding.etCheckInTime.setOnClickListener {
            showTimePicker(binding.etCheckInTime)
        }
        
        // Arrival time picker
        binding.etArrivalTime.setOnClickListener {
            showTimePicker(binding.etArrivalTime)
        }
    }
    
    private fun showDatePicker(targetEditText: android.widget.EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        
        DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
            targetEditText.setText(formattedDate)
        }, year, month, day).show()
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

    private fun saveFlightDetails() {
        // Validate inputs
        if (binding.etDepartureDate.text.isNullOrEmpty() || binding.etAirline.text.isNullOrEmpty()) {
            Toast.makeText(this, "Please fill out required fields", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Add flight to trip
        tripId?.let { id ->
            // Create flight details from form inputs
            val flightDetails = mapOf(
                "airline" to binding.etAirline.text.toString(),
                "departureDate" to binding.etDepartureDate.text.toString(),
                "departureTime" to binding.etCheckInTime.text.toString(),
                "arrivalDate" to binding.etArrivalDate.text.toString(),
                "arrivalTime" to binding.etArrivalTime.text.toString(),
                "expense" to binding.etExpense.text.toString().ifEmpty { "0" },
                "terminal" to binding.etTerminal.text.toString(),
                "gate" to binding.etGate.text.toString()
            )
            
            viewModel.addFlightToTrip(id, flightDetails.toString())
        } ?: run {
            Toast.makeText(this, "Trip ID is missing", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadFlightOptions() {
        // Here we would load flight options based on trip details
        viewModel.getFlightOptions(
            origin = "NYC",
            destination = "PAR",
            departureDate = "2025-10-10",
            returnDate = "2025-10-15",
            travelers = 2
        )
    }
}