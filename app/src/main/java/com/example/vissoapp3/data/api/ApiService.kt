package com.example.vissoapp3.data.api

import com.example.vissoapp3.data.model.Producto
import retrofit2.http.GET

interface ApiService {

    @GET("/api/productos")
    suspend fun getProductos(): List<Producto>
}
