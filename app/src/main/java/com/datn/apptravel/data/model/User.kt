package com.datn.apptravel.data.model

data class User(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val profilePicture: String? = null,
    val provider: String? = null
)
