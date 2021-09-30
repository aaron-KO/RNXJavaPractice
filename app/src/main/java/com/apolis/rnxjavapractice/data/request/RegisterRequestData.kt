package com.apolis.twowaydatabindingpractice.data.request

data class RegisterRequestData(
    val email: String,
    val password: String,
    val firstName: String,
    val mobile: String
)