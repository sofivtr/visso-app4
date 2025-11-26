package com.example.vissoapp3.utils
import com.example.vissoapp3.data.model.Usuario

object SessionManager {
    var currentUser: Usuario? = null

    fun isLoggedIn() = currentUser != null
    fun isAdmin() = currentUser?.rol == "ADMIN"
    fun logout() { currentUser = null }
}