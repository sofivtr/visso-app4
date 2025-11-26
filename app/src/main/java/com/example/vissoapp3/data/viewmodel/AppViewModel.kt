package com.example.vissoapp3.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vissoapp3.data.api.RetrofitClient
import com.example.vissoapp3.data.model.*
import com.example.vissoapp3.utils.SessionManager
import kotlinx.coroutines.launch

class AppViewModel : ViewModel() {
    var productos by mutableStateOf<List<Producto>>(emptyList())
    var carrito by mutableStateOf<Carrito?>(null)
    var mensajeError by mutableStateOf<String?>(null)
    var mensajeExito by mutableStateOf<String?>(null)

    // Cargar productos al iniciar
    fun cargarProductos() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.obtenerProductos()
                if (response.isSuccessful) {
                    productos = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
                mensajeError = "Error al conectar: ${e.message}"
            }
        }
    }

    fun agregarProductoSimple(producto: Producto) {
        val userId = SessionManager.currentUser?.id ?: return
        viewModelScope.launch {
            val solicitud = SolicitudCarrito(userId, producto.id, 1, null)
            val response = RetrofitClient.apiService.agregarAlCarrito(solicitud)
            if (response.isSuccessful) {
                mensajeExito = "Agregado al carrito"
                cargarCarrito() // Refrescar
            }
        }
    }

    fun agregarLenteOptico(producto: Producto, cotizacion: Cotizacion) {
        viewModelScope.launch {
            // 1. Crear Cotización
            val respCot = RetrofitClient.apiService.crearCotizacion(cotizacion)
            if (respCot.isSuccessful) {
                val idCot = respCot.body()?.id
                val userId = SessionManager.currentUser?.id!!

                // 2. Agregar al carrito con la cotización
                val solicitud = SolicitudCarrito(userId, producto.id, 1, idCot)
                val respCar = RetrofitClient.apiService.agregarAlCarrito(solicitud)

                if(respCar.isSuccessful) {
                    mensajeExito = "Lente y receta guardados"
                    cargarCarrito()
                }
            }
        }
    }

    fun cargarCarrito() {
        val userId = SessionManager.currentUser?.id ?: return
        viewModelScope.launch {
            val response = RetrofitClient.apiService.obtenerCarrito(userId)
            if (response.isSuccessful) {
                carrito = response.body()
            }
        }
    }

    fun pagarCarrito() {
        val userId = SessionManager.currentUser?.id ?: return
        viewModelScope.launch {
            val response = RetrofitClient.apiService.cerrarCarrito(userId)
            if (response.isSuccessful) {
                mensajeExito = "¡Compra Exitosa!"
                carrito = null // Limpiar vista local
            }
        }
    }
}