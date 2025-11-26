package com.example.vissoapp3.ui.viewmodel

import android.net.Uri
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
    var historialVentas by mutableStateOf<List<Carrito>>(emptyList()) // Admin

    var isLoading by mutableStateOf(false)
    var mensaje by mutableStateOf<String?>(null)

    // --- Productos ---
    fun cargarProductos() {
        viewModelScope.launch {
            isLoading = true
            try {
                val res = RetrofitClient.apiService.listarProductos()
                if (res.isSuccessful) productos = res.body() ?: emptyList()
            } catch (e: Exception) { mensaje = "Error conexión" }
            finally { isLoading = false }
        }
    }

    // --- Carrito ---
    fun cargarCarrito() {
        val uid = SessionManager.currentUser?.id ?: return
                viewModelScope.launch {
            try {
                val res = RetrofitClient.apiService.obtenerCarrito(uid)
                if (res.isSuccessful) carrito = res.body()
            } catch (e: Exception) { mensaje = "Error al cargar carrito" }
        }
    }

    fun agregarAlCarrito(productoId: Long, cantidad: Int, cotizacionId: Long? = null) {
        val uid = SessionManager.currentUser?.id ?: return
                viewModelScope.launch {
            isLoading = true
            try {
                val sol = SolicitudCarrito(uid, productoId, cantidad, cotizacionId)
                val res = RetrofitClient.apiService.agregarAlCarrito(sol)
                if (res.isSuccessful) {
                    mensaje = "Producto agregado"
                    cargarCarrito() // Refrescar
                } else mensaje = "Error al agregar"
            } catch (e: Exception) { mensaje = "Error de red" }
            finally { isLoading = false }
        }
    }

    fun pagarCarrito() {
        val uid = SessionManager.currentUser?.id ?: return
                viewModelScope.launch {
            isLoading = true
            try {
                val res = RetrofitClient.apiService.cerrarCarrito(uid)
                if (res.isSuccessful) {
                    mensaje = "Compra realizada con éxito"
                    carrito = null
                }
            } catch (e: Exception) { mensaje = "Error al pagar" }
            finally { isLoading = false }
        }
    }

    // --- Admin: Crear Producto con Imagen ---
    fun crearProductoAdmin(producto: Producto, imageUri: Uri?) {
        viewModelScope.launch {
            isLoading = true
            try {
                // NOTA: El backend actual guarda un String URL, no sube archivos.
                // Simularemos la subida asignando una ruta ficticia o la URI local convertida a string.
                val productoFinal = if (imageUri != null) {
                    producto.copy(imagenUrl = imageUri.toString()) // Guardamos la URI local para la demo
                } else producto

                val res = RetrofitClient.apiService.crearProducto(productoFinal)
                if (res.isSuccessful) {
                    mensaje = "Producto creado"
                    cargarProductos()
                } else mensaje = "Error al crear"
            } catch (e: Exception) { mensaje = "Error: ${e.message}" }
            finally { isLoading = false }
        }
    }

    fun cargarVentasAdmin() {
        viewModelScope.launch {
            val res = RetrofitClient.apiService.obtenerVentas()
            if (res.isSuccessful) historialVentas = res.body() ?: emptyList()
        }
    }
}