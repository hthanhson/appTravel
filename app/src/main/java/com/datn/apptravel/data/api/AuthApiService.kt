package com.datn.apptravel.data.api

import com.datn.apptravel.data.model.*
import com.datn.apptravel.data.model.request.ForgotPasswordRequest
import com.datn.apptravel.data.model.request.GoogleTokenRequest
import com.datn.apptravel.data.model.request.LoginRequest
import com.datn.apptravel.data.model.request.SignUpRequest
import com.datn.apptravel.data.model.response.ApiResponse
import com.datn.apptravel.data.model.response.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiService {
    
    @POST("api/auth/signup")
    suspend fun signUp(@Body request: SignUpRequest): Response<AuthResponse>
    
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>
    
    @POST("api/auth/google")
    suspend fun googleSignIn(@Body request: GoogleTokenRequest): Response<AuthResponse>

    @POST("api/auth/forgot-password")
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest):Response<AuthResponse>
    
    @GET("api/auth/test")
    suspend fun test(): Response<ApiResponse>
}
