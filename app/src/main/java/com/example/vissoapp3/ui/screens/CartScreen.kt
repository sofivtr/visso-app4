package com.example.vissoapp3.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vissoapp3.viewmodel.AppViewModel

@Composable
fun CartScreen(viewModel: AppViewModel) {
    LaunchedEffect(Unit) { viewModel.cargarCarrito() }
    val carrito = viewModel.carrito

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Tu Carrito", style = MaterialTheme.typography.headlineSmall)

        if (carrito != null && carrito.detalles.isNotEmpty()) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(carrito.detalles) { detalle ->
                    Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("${detalle.producto.nombre} x${detalle.cantidad}")
                        Text("$${detalle.precioUnitario * detalle.cantidad}")
                    }
                    Divider()
                }
            }
            Text("Total: $${carrito.total}", style = MaterialTheme.typography.titleLarge)
            Button(onClick = { viewModel.pagarCarrito() }, modifier = Modifier.fillMaxWidth()) {
                Text("Pagar Ahora")
            }
        } else {
            Text("El carrito está vacío")
        }

        if (viewModel.mensajeExito != null) {
            Text(viewModel.mensajeExito!!, color = MaterialTheme.colorScheme.primary)
        }
    }
}