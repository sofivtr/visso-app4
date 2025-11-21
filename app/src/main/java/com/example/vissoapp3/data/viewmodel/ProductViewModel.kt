package com.example.vissoapp3.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vissoapp3.data.model.Producto
import com.example.vissoapp3.data.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class LentesUiState {
    object Loading : LentesUiState()
    data class Success(val productos: List<Producto>) : LentesUiState()
    data class Error(val message: String) : LentesUiState()
}

class LentesOpticosViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<LentesUiState>(LentesUiState.Loading)
    val uiState: StateFlow<LentesUiState> = _uiState.asStateFlow()

    init {
        cargarProductos()
    }

    fun cargarProductos() {
        viewModelScope.launch {
            _uiState.value = LentesUiState.Loading
            try {
                val lista = ProductoRepository.obtenerLentesOpticos()
                if (lista.isNotEmpty()) {
                    _uiState.value = LentesUiState.Success(lista)
                } else {
                    _uiState.value = LentesUiState.Error("No se encontraron productos")
                }
            } catch (e: Exception) {
                _uiState.value = LentesUiState.Error("Error de conexión: ${e.message}")
            }
        }
    }
}