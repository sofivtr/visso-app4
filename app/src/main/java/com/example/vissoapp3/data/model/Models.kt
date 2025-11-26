package com.example.vissoapp3.data.model

data class Usuario(
    val id: Long? = null,
    val nombre: String,
    val apellido: String,
    val rut: String,
    val email: String,
    val passwordHash: String? = null, // Se usa para enviar la password en registro
    val rol: String? = "USER"
)

data class LoginRequest(val email: String, val password: String)

data class Producto(
    val id: Long? = null,
    val codigoProducto: String,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val stock: Int,
    val tipo: String, // 'O', 'S', 'A', 'C'
    val imagenUrl: String?,
    val fechaCreacion: String = "2024-11-20",
    val categoria: CategoriaId,
    val marca: MarcaId
)

data class CategoriaId(val id: Long)
data class MarcaId(val id: Long)

data class SolicitudCarrito(
    val usuarioId: Long,
    val productoId: Long,
    val cantidad: Int,
    val cotizacionId: Long? = null
)

data class Carrito(
    val id: Long,
    val total: Double,
    val estado: String,
    val detalles: List<DetalleCarrito> = emptyList()
)

data class DetalleCarrito(
    val id: Long,
    val producto: Producto,
    val cantidad: Int,
    val precioUnitario: Double,
    val cotizacion: Cotizacion?
)

data class Cotizacion(
    val id: Long? = null,
    val usuario: UsuarioId, // Wrapper solo con ID
    val producto: ProductoId, // Wrapper solo con ID
    val nombrePaciente: String,
    val fechaReceta: String,
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