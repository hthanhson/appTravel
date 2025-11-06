package com.datn.apptravel.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.datn.apptravel.BuildConfig
import com.datn.apptravel.R
import com.datn.apptravel.ui.activity.MainActivity
import com.datn.apptravel.ui.viewmodel.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInActivity : AppCompatActivity() {
    
    private val viewModel: AuthViewModel by viewModel()
    private lateinit var googleSignInClient: GoogleSignInClient
    
    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        handleGoogleSignInResult(task)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        
        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.GOOGLE_CLIENT_ID)
            .requestEmail()
            .build()
        
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        
        // Set up UI elements
        setupUI()
        
        // Observe sign in result
        viewModel.signInResult.observe(this) { result ->
            result.onSuccess {
                Toast.makeText(this, "Sign in successful", Toast.LENGTH_SHORT).show()
                // Create a sample trip for testing
                createSampleTrip()
                // Navigate to main screen
                navigateToMain()
            }.onFailure { error ->
                Toast.makeText(this, "Sign in failed: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        }
        
        // Observe Google sign in result
        viewModel.googleSignInResult.observe(this) { result ->
            result.onSuccess {
                Toast.makeText(this, "Google sign in successful", Toast.LENGTH_SHORT).show()
                // Create a sample trip for testing
                createSampleTrip()
                // Navigate to main screen
                navigateToMain()
            }.onFailure { error ->
                Toast.makeText(this, "Google sign in failed: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun setupUI() {
        // Set up sign in button
        findViewById<Button>(R.id.btnSignIn)?.setOnClickListener {
            val email = findViewById<EditText>(R.id.etEmail)?.text.toString().trim()
            val password = findViewById<EditText>(R.id.etPassword)?.text.toString()
            
            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            if (password.isEmpty()) {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            // Sign in with provided credentials
            viewModel.signIn(email, password)
        }
        
        // Set up Google sign in button
        findViewById<Button>(R.id.btnGoogleSignIn)?.setOnClickListener {
            signInWithGoogle()
        }
        
        // Set up sign up text
        findViewById<TextView>(R.id.tvSignUp)?.setOnClickListener {
            navigateToSignUp()
        }
        
        // Set up forgot password text
        findViewById<TextView>(R.id.tvForgotPassword)?.setOnClickListener {
            navigateToForgotPassword()
        }
    }
    
    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }
    
    private fun handleGoogleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val idToken = account.idToken
            
            if (idToken != null) {
                Log.d("SignInActivity", "Google ID Token: $idToken")
                viewModel.signInWithGoogle(idToken)
            } else {
                Toast.makeText(this, "Failed to get ID token", Toast.LENGTH_SHORT).show()
            }
        } catch (e: ApiException) {
            Log.e("SignInActivity", "Google sign in failed", e)
            Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
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