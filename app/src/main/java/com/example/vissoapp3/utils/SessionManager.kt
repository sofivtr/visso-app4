package com.example.vissoapp3.utils

import com.example.vissoapp3.data.model.Usuario

object SessionManager {
    var currentUser: Usuario? = null

    fun isLoggedIn(): Boolean = currentUser != null
    fun isAdmin(): Boolean = currentUser?.rol == "ADMIN"

    fun logout() {
        currentUser = null
    }
}