package com.example.vissoapp3.data.api

import com.example.vissoapp3.data.model.Producto
import retrofit2.http.GET

interface ApiService {

    @GET("/api/productos")
    suspend fun getProductos(): List<Producto>

    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("/api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>
}
