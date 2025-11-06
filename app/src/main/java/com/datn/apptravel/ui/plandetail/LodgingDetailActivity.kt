package com.datn.apptravel.ui.plandetail

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.datn.apptravel.databinding.ActivityLodgingDetailBinding
import com.datn.apptravel.ui.viewmodel.LodgingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

class LodgingDetailActivity : AppCompatActivity() {
    
    private val viewModel: LodgingViewModel by viewModel()
    private var tripId: String? = null
    private lateinit var binding: ActivityLodgingDetailBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLodgingDetailBinding.inflate(layoutInflater)
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
            saveLodgingDetails()
        }
        
        // Setup date pickers
        setupDatePickers()
        
        // Setup time pickers
        setupTimePickers()
    }
    
    private fun setupObservers() {
        // Observe save lodging result
        viewModel.saveLodgingResult.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Lodging added to your trip", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to add lodging", Toast.LENGTH_SHORT).show()
            }
        }
        
        // Observe loading state
        viewModel.isLoading.observe(this) { isLoading ->
            // Show/hide loading indicator if needed
        }
    }
    
    private fun setupDatePickers() {
        // Check-in date picker
        binding.etCheckInDate.setOnClickListener {
            showDatePicker(binding.etCheckInDate)
        }
        
        // Check-out date picker
        binding.etCheckoutDate.setOnClickListener {
            showDatePicker(binding.etCheckoutDate)
        }
    }
    
    private fun setupTimePickers() {
        // Check-in time picker
        binding.etCheckInTime.setOnClickListener {
            showTimePicker(binding.etCheckInTime)
        }
        
        // Check-out time picker
        binding.etCheckoutTime.setOnClickListener {
            showTimePicker(binding.etCheckoutTime)
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

    private fun saveLodgingDetails() {
        // Validate inputs
        if (binding.etLodgingName.text.isNullOrEmpty() || binding.etCheckInDate.text.isNullOrEmpty() 
            || binding.etCheckoutDate.text.isNullOrEmpty()) {
            Toast.makeText(this, "Please fill out required fields", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Add lodging to trip
        tripId?.let { id ->
            // Create lodging details from form inputs
            val lodgingDetails = mapOf(
                "name" to binding.etLodgingName.text.toString(),
                "checkInDate" to binding.etCheckInDate.text.toString(),
                "checkInTime" to binding.etCheckInTime.text.toString(),
                "checkoutDate" to binding.etCheckoutDate.text.toString(),
                "checkoutTime" to binding.etCheckoutTime.text.toString(),
                "expense" to binding.etExpense.text.toString().ifEmpty { "0" },
                "address" to binding.etAddress.text.toString(),
                "phone" to binding.etPhone.text.toString(),
                "email" to binding.etEmail.text.toString()
            )
            
            viewModel.saveLodging(id, lodgingDetails.toString())
        } ?: run {
            Toast.makeText(this, "Trip ID is missing", Toast.LENGTH_SHORT).show()
        }
    }
}