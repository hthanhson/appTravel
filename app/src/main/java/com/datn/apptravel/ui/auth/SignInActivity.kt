package com.datn.apptravel.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.datn.apptravel.R
import com.datn.apptravel.ui.activity.MainActivity
import com.datn.apptravel.ui.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Activity for user sign in
 */
class SignInActivity : AppCompatActivity() {
    
    private val viewModel: AuthViewModel by viewModel()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        
        // Set up UI elements
        setupUI()
        
        // Observe sign in result
        viewModel.signInResult.observe(this) { success ->
            if (success) {
                // Create a sample trip for testing
                createSampleTrip()
                
                // Navigate to main screen
                navigateToMain()
            } else {
                // Show error message
                Toast.makeText(this, "Sign in failed", Toast.LENGTH_SHORT).show()
            }
        }
        
        // Auto sign-in for testing (remove this in production)
        viewModel.signIn("test@example.com", "password")
    }
    
    private fun setupUI() {
        // Set up sign in button if it exists
        findViewById<Button>(R.id.btnSignIn)?.setOnClickListener {
            val email = findViewById<EditText>(R.id.etEmail)?.text.toString() ?: ""
            val password = findViewById<EditText>(R.id.etPassword)?.text.toString() ?: ""
            
            // Sign in with provided credentials
            viewModel.signIn(email, password)
        }
        
        // Set up sign up text if it exists
        findViewById<TextView>(R.id.tvSignUp)?.setOnClickListener {
            navigateToSignUp()
        }
        
        // Set up forgot password text if it exists
        findViewById<TextView>(R.id.tvForgotPassword)?.setOnClickListener {
            navigateToForgotPassword()
        }
    }
    
    /**
     * Create a sample trip for testing
     */
    private fun createSampleTrip() {
        // Create a sample trip using TripManager
        MainActivity.tripManager.createSampleTrip()
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
    
    /**
     * Navigate to Sign Up screen
     */
    private fun navigateToSignUp() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }
    
    /**
     * Navigate to Forgot Password screen
     */
    private fun navigateToForgotPassword() {
        val intent = Intent(this, ForgotPasswordActivity::class.java)
        startActivity(intent)
    }
}