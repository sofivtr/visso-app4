package com.example.vissoapp3.data.api

import com.example.vissoapp3.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // Auth
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<Usuario>

    @POST("auth/registro")
    suspend fun registrar(@Body usuario: Usuario): Response<Usuario>

    @POST("auth/recuperar-password")
    suspend fun recuperarPassword(@Body body: Map<String, String>): Response<Map<String, String>>

    // Productos
    @GET("productos")
    suspend fun listarProductos(): Response<List<Producto>>

    @POST("productos")
    suspend fun crearProducto(@Body producto: Producto): Response<Producto>

    @PUT("productos/{id}")
    suspend fun actualizarProducto(@Path("id") id: Long, @Body producto: Producto): Response<Producto>

    @DELETE("productos/{id}")
    suspend fun eliminarProducto(@Path("id") id: Long): Response<Void>

    // Carrito
    @GET("carrito/{usuarioId}")
    suspend fun obtenerCarrito(@Path("usuarioId") usuarioId: Long): Response<Carrito>

    @POST("carrito/agregar")
    suspend fun agregarAlCarrito(@Body solicitud: SolicitudCarrito): Response<Void> // El backend devuelve String, pero Void es más seguro si no parseamos

    @POST("carrito/cerrar/{usuarioId}")
    suspend fun cerrarCarrito(@Path("usuarioId") usuarioId: Long): Response<Void>

    @GET("carrito/ventas")
    suspend fun obtenerVentas(): Response<List<Carrito>>

    // Cotizaciones
    @POST("cotizaciones")
    suspend fun crearCotizacion(@Body cotizacion: Cotizacion): Response<Cotizacion>

    // Usuarios
    @PUT("usuarios/{id}")
    suspend fun actualizarPerfil(@Path("id") id: Long, @Body usuario: Usuario): Response<Usuario>
}