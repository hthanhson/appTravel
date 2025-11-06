package com.datn.apptravel.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.datn.apptravel.data.model.request.ForgotPasswordRequest
import com.datn.apptravel.data.model.request.GoogleTokenRequest
import com.datn.apptravel.data.model.request.LoginRequest
import com.datn.apptravel.data.model.request.SignUpRequest
import com.datn.apptravel.data.repository.AuthRepository
import com.datn.apptravel.ui.base.BaseViewModel
import kotlinx.coroutines.launch


class AuthViewModel(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    // Sign in result
    private val _signInResult = MutableLiveData<Result<String>>()
    val signInResult: LiveData<Result<String>> = _signInResult

    // Sign up result
    private val _signUpResult = MutableLiveData<Result<String>>()
    val signUpResult: LiveData<Result<String>> = _signUpResult

    // Google sign in result
    private val _googleSignInResult = MutableLiveData<Result<String>>()
    val googleSignInResult: LiveData<Result<String>> = _googleSignInResult

    // Reset password result
    private val _resetPasswordResult = MutableLiveData<Boolean>()
    val resetPasswordResult: LiveData<Boolean> = _resetPasswordResult

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            setLoading(true)
            try {
                val request = LoginRequest(email, password)
                val response = authRepository.login(request)

                if (response.isSuccessful) {
                    val authResponse = response.body()
                    _signInResult.value = Result.success("Sign in successful")
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "Sign in failed"
                    _signInResult.value = Result.failure(Exception(errorMsg))
                }
            } catch (e: Exception) {
                _signInResult.value = Result.failure(e)
            } finally {
                setLoading(false)
            }
        }
    }


    fun signUp(firstName: String, lastName: String, email: String, password: String) {
        viewModelScope.launch {
            setLoading(true)
            try {
                val request = SignUpRequest(firstName, lastName, email, password)
                val response = authRepository.signUp(request)

                if (response.isSuccessful) {
                    val authResponse = response.body()
                    _signUpResult.value = Result.success("Sign up successful")
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "Sign up failed"
                    _signUpResult.value = Result.failure(Exception(errorMsg))
                }
            } catch (e: Exception) {
                _signUpResult.value = Result.failure(e)
            } finally {
                setLoading(false)
            }
        }
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            setLoading(true)
            try {
                val request = GoogleTokenRequest(idToken)
                val response = authRepository.googleSignIn(request)

                if (response.isSuccessful) {
                    val authResponse = response.body()
                    _googleSignInResult.value = Result.success("Google sign in successful")
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "Google sign in failed"
                    _googleSignInResult.value = Result.failure(Exception(errorMsg))
                }
            } catch (e: Exception) {
                _googleSignInResult.value = Result.failure(e)
            } finally {
                setLoading(false)
            }
        }
    }


    fun resetPassword(email: String) {
        viewModelScope.launch {
            setLoading(true);
            try {
                val request = ForgotPasswordRequest(email);
                val response = authRepository.forgotPassword(request)
                if (response.isSuccessful) {
                    _resetPasswordResult.value = true
                } else {
                    _resetPasswordResult.value = false
                }

            } catch (e: Exception) {
                _resetPasswordResult.value =false
            }finally {
                setLoading(false)
            }
        }
    }
}