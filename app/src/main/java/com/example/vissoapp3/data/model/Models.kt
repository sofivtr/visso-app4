package com.example.vissoapp3.data.model

import java.util.Date

// Usuario (Login/Registro)
data class Usuario(
    val id: Long? = null,
    val nombre: String,
    val apellido: String,
    val rut: String? = null,
    val email: String,
    val passwordHash: String? = null, // Se envía al registrar, no se recibe
    val rol: String? = "USER"
)

// Login Request
data class LoginRequest(val email: String, val password: String)

// Producto
data class Producto(
    val id: Long,
    val nombre: String,
    val descripcion: String?,
    val precio: Int,
    val stock: Int,
    val tipo: String, // 'O'ptico, 'S'ol, 'A'ccesorio
    val imagenUrl: String?
)

// Solicitud para agregar al carrito (Tu DTO del backend)
data class SolicitudCarrito(
    val usuarioId: Long,
    val productoId: Long,
    val cantidad: Int,
    val cotizacionId: Long? = null
)

// Cotización (Receta)
data class Cotizacion(
    val id: Long? = null,
    val usuario: UsuarioId,
    val producto: ProductoId,
    val nombrePaciente: String,
    val fechaReceta: String, // Formato YYYY-MM-DD
    val gradoOd: Double,
    val gradoOi: Double,
    val tipoLente: String,
    val tipoCristal: String,
    val antirreflejo: Boolean,
    val filtroAzul: Boolean,
    val despachoDomicilio: Boolean
)
data class UsuarioId(val id: Long)
data class ProductoId(val id: Long)

// Carrito (Respuesta del GET)
data class Carrito(
    val id: Long,
    val total: Int,
    val estado: String,
    val detalles: List<DetalleCarrito> = emptyList()
)

data class DetalleCarrito(
    val id: Long,
    val producto: Producto,
    val cantidad: Int,
    val precioUnitario: Int,
    val cotizacion: Cotizacion? // Puede ser null si es accesorio
)