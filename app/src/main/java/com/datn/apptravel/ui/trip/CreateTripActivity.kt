package com.datn.apptravel.ui.trip

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.datn.apptravel.ui.tripdetail.TripDetailActivity
import com.datn.apptravel.ui.viewmodel.TripViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateTripActivity : AppCompatActivity() {
    
    private val viewModel: TripViewModel by viewModel()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Observe create trip result
        viewModel.createTripResult.observe(this) { tripId ->
            if (tripId != null) {
                navigateToTripDetail(tripId)
            }
        }
    }

    private fun navigateToTripDetail(tripId: String) {
        val intent = Intent(this, TripDetailActivity::class.java)
        intent.putExtra("tripId", tripId)
        startActivity(intent)
        finish()
    }
}