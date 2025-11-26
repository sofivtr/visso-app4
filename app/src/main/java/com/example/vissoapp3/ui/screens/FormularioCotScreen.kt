package com.example.vissoapp3.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vissoapp3.R
import com.example.vissoapp3.data.api.RetrofitClient
import com.example.vissoapp3.data.model.*
import com.example.vissoapp3.ui.components.Navbar
import com.example.vissoapp3.utils.SessionManager
import kotlinx.coroutines.launch

@Composable
fun FormularioCotScreen(navController: NavController, productoId: Long, productoNombre: String) {
    // Variables del formulario
    var paciente by remember { mutableStateOf("") }
    var od by remember { mutableStateOf("") }
    var oi by remember { mutableStateOf("") }
    var tipoLente by remember { mutableStateOf("Monofocal") }
    var tipoCristal by remember { mutableStateOf("Orgánico") }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(topBar = { Navbar(R.drawable.logo, navController) }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text("Cotización: $productoNombre", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1565C0))
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = paciente,
                onValueChange = { paciente = it },
                label = { Text("Nombre Paciente") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                OutlinedTextField(value = od, onValueChange = { od = it }, label = { Text("Ojo Derecho (OD)") }, modifier = Modifier.weight(1f).padding(end = 8.dp))
                OutlinedTextField(value = oi, onValueChange = { oi = it }, label = { Text("Ojo Izquierdo (OI)") }, modifier = Modifier.weight(1f))
            }

            // Aquí podrías poner Dropdowns para Lente y Cristal si quieres mejorarlo

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (SessionManager.currentUser == null) {
                        Toast.makeText(context, "Debes iniciar sesión", Toast.LENGTH_LONG).show()
                        navController.navigate("login")
                        return@Button
                    }

                    scope.launch {
                        try {
                            // 1. Crear Objeto Cotización
                            val cotizacion = Cotizacion(
                                usuario = UsuarioId(SessionManager.currentUser!!.id!!),
                                producto = ProductoId(productoId),
                                nombrePaciente = paciente,
                                fechaReceta = "2024-11-28", // Fecha ejemplo o usa DatePicker
                                gradoOd = od.toDoubleOrNull() ?: 0.0,
                                gradoOi = oi.toDoubleOrNull() ?: 0.0,
                                tipoLente = tipoLente,
                                tipoCristal = tipoCristal,
                                antirreflejo = true,
                                filtroAzul = false,
                                despachoDomicilio = true
                            )

                            // 2. Enviar al Backend
                            val respCot = RetrofitClient.apiService.crearCotizacion(cotizacion)

                            if (respCot.isSuccessful) {
                                val idCot = respCot.body()!!.id!!

                                // 3. Agregar al Carrito
                                val solicitud = SolicitudCarrito(
                                    usuarioId = SessionManager.currentUser!!.id!!,
                                    productoId = productoId,
                                    cantidad = 1,
                                    cotizacionId = idCot
                                )
                                RetrofitClient.apiService.agregarAlCarrito(solicitud)

                                Toast.makeText(context, "¡Agregado al Carrito!", Toast.LENGTH_LONG).show()
                                navController.navigate("cart")
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))
            ) {
                Text("AGREGAR AL CARRITO", fontWeight = FontWeight.Bold)
            }
        }
    }
}