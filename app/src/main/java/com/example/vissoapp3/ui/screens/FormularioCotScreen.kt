package com.example.vissoapp3.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vissoapp3.data.api.RetrofitClient
import com.example.vissoapp3.data.model.*
import com.example.vissoapp3.utils.SessionManager
import kotlinx.coroutines.launch

@Composable
fun FormularioCotScreen(navController: NavController, productoId: Long, nombreProducto: String) {
    var paciente by remember { mutableStateOf("") }
    var od by remember { mutableStateOf("") }
    var oi by remember { mutableStateOf("") }

    // UI simplificada para el ejemplo
    val scope = rememberCoroutineScope()

    Column(Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
        Text("Cotizar: $nombreProducto", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(value = paciente, onValueChange = { paciente = it }, label = { Text("Nombre Paciente") }, modifier = Modifier.fillMaxWidth())
        Row {
            OutlinedTextField(value = od, onValueChange = { od = it }, label = { Text("OD") }, modifier = Modifier.weight(1f))
            Spacer(Modifier.width(8.dp))
            OutlinedTextField(value = oi, onValueChange = { oi = it }, label = { Text("OI") }, modifier = Modifier.weight(1f))
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                val userId = SessionManager.currentUser?.id
                if (userId != null) {
                    scope.launch {
                        try {
                            // 1. Crear Cotización
                            val cot = Cotizacion(
                                usuario = UsuarioId(userId),
                                producto = ProductoId(productoId),
                                nombrePaciente = paciente,
                                fechaReceta = "2024-11-28",
                                gradoOd = od.toDoubleOrNull() ?: 0.0,
                                gradoOi = oi.toDoubleOrNull() ?: 0.0,
                                tipoLente = "Monofocal",
                                tipoCristal = "Blanco",
                                antirreflejo = true,
                                filtroAzul = false,
                                despachoDomicilio = true
                            )
                            val resCot = RetrofitClient.apiService.crearCotizacion(cot)

                            // 2. Si OK, agregar al carrito
                            if (resCot.isSuccessful) {
                                val cotId = resCot.body()!!.id!!
                                RetrofitClient.apiService.agregarAlCarrito(SolicitudCarrito(userId, productoId, 1, cotId))
                                navController.navigate("cart")
                            }
                        } catch(e: Exception) {}
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))
        ) { Text("Confirmar Receta") }
    }
}