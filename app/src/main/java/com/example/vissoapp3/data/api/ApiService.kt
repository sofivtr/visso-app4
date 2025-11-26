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
    suspend fun obtenerProductos(): Response<List<Producto>>

    // Cotizaciones
    @POST("cotizaciones")
    suspend fun crearCotizacion(@Body cotizacion: Cotizacion): Response<Cotizacion>

    // Carrito
    @POST("carrito/agregar")
    suspend fun agregarAlCarrito(@Body solicitud: SolicitudCarrito): Response<Void>

    @GET("carrito/{idUsuario}")
    suspend fun obtenerCarrito(@Path("idUsuario") idUsuario: Long): Response<Carrito>

    @POST("carrito/cerrar/{idUsuario}")
    suspend fun cerrarCarrito(@Path("idUsuario") idUsuario: Long): Response<Carrito>

    // Admin
    @GET("carrito/ventas")
    suspend fun obtenerVentas(): Response<List<Carrito>>

    // Perfil
    @PUT("usuarios/{id}")
    suspend fun actualizarPerfil(@Path("id") id: Long, @Body usuario: Usuario): Response<Usuario>
}