package com.example.vissoapp3.data.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vissoapp3.data.api.RetrofitClient
import com.example.vissoapp3.data.model.LoginRequest
import com.example.vissoapp3.data.model.RegisterRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun register(nombre: String, email: String, pass: String, rol: String) {
        if (!isValidEmail(email)) {
            _authState.value = AuthState.Error("El correo no es válido")
            return
        }
        if (pass.length < 4) {
            _authState.value = AuthState.Error("La contraseña es muy corta")
            return
        }

        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val request = RegisterRequest(nombre, email, pass, rol)
                val response = RetrofitClient.api.register(request)

                if (response.isSuccessful) {
                    _authState.value = AuthState.Success
                } else {
                    val errorBody = response.errorBody()?.string()
                    _authState.value = AuthState.Error("Error: ${response.code()} - ${errorBody ?: response.message()}")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Error de conexión: ${e.localizedMessage}")
            }
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}