package com.datn.apptravel.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.datn.apptravel.ui.activity.MainActivity
import com.datn.apptravel.ui.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Activity for user sign up
 */
class SignUpActivity : AppCompatActivity() {
    
    private val viewModel: AuthViewModel by viewModel()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Observe sign up result
        viewModel.signUpResult.observe(this) { success ->
            if (success) {
                navigateToMain()
            }
        }
    }
    
    /**
     * Navigate to Main screen
     */
    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}