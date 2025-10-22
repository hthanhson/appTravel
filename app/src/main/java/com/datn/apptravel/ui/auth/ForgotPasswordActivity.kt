package com.datn.apptravel.ui.auth

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.datn.apptravel.ui.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Activity for password reset
 */
class ForgotPasswordActivity : AppCompatActivity() {
    
    private val viewModel: AuthViewModel by viewModel()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Observe reset password result
        viewModel.resetPasswordResult.observe(this) { success ->
            if (success) {
                showResetSuccessMessage()
            }
        }
    }
    
    /**
     * Show reset success message
     */
    private fun showResetSuccessMessage() {
        Toast.makeText(this, "Password reset instructions sent to your email!", Toast.LENGTH_LONG).show()
    }
}