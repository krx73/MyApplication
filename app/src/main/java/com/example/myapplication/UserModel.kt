package com.example.myapplication

import kotlinx.serialization.Serializable

@Serializable
data class UserModel (
    val id: Int = 0,
    val firstname: String = "",
    val lastname: String = "",
    val userImage: String = "",
    val email: String = "",
    val password: String = "",
    val score: Int = 0
)
