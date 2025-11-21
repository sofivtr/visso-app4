package com.example.vissoapp3.data.repository

import com.example.vissoapp3.data.api.RetrofitClient
import com.example.vissoapp3.data.model.Producto

object ProductoRepository {

    suspend fun obtenerLentesOpticos(): List<Producto> {
        val productos = RetrofitClient.api.getProductos()

        return productos.filter { it.tipo.equals("O", ignoreCase = true) }
    }
}
