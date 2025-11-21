package com.example.vissoapp3.data.model

import com.example.vissoapp3.data.User

// Lo que enviamos al backend para registrarnos
data class RegisterRequest(
    val nombre: String,
    val email: String,
    val password: String,
    val rol: String // "admin" o "usuario" según la rúbrica
)

// Lo que enviamos para iniciar sesión
data class LoginRequest(
    val email: String,
    val password: String
)

// Lo que el backend nos responde (puede variar según tu backend, esto es estándar)
data class AuthResponse(
    val token: String?,
    val mensaje: String,
    val usuario: User?
)