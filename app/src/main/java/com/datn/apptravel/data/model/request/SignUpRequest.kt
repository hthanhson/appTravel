package com.datn.apptravel.data.model.request

data class SignUpRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)