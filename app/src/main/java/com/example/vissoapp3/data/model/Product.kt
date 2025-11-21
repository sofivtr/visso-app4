package com.example.vissoapp3.data.model

data class Producto(
    val id: Long,
    val codigoProducto: String,
    val nombre: String,
    val descripcion: String?,
    val precio: Double,
    val stock: Int,
    val tipo: String,            // 'O', 'S', 'C', 'A'
    val fechaCreacion: String,
    val imagenUrl: String?,      // puede ser null
    val marca: Marca?,           // opcional
    val categoria: Categoria?    // opcional
)

data class Marca(
    val id: Long,
    val nombre: String
)

data class Categoria(
    val id: Long,
    val nombre: String
)