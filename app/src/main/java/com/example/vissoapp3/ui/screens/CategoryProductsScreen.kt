package com.example.vissoapp3.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vissoapp3.ui.viewmodel.AppViewModel

@Composable
fun CategoryProductsScreen(navController: NavController, viewModel: AppViewModel, categoryType: String) {
    // categoryType puede ser 'O' (Ópticos), 'S' (Sol), 'A' (Accesorios)
    LaunchedEffect(Unit) { viewModel.cargarProductos() }

    val filteredProducts = viewModel.productos.filter { it.tipo == categoryType }

    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        items(filteredProducts) { producto ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(producto.nombre, style = MaterialTheme.typography.titleMedium)
                    Text("$${producto.precio}", color = Color(0xFF2E7D32), style = MaterialTheme.typography.titleLarge)

                    Button(
                        onClick = {
                            if (categoryType == "O") {
                                // Si es óptico, vamos a cotizar
                                navController.navigate("cotizacion/${producto.id}/${producto.nombre}")
                            } else {
                                // Si es accesorio/sol, directo al carrito
                                viewModel.agregarAlCarrito(producto.id!!, 1)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (categoryType == "O") "Cotizar" else "Agregar al Carrito")
                    }
                }
            }
        }
    }
}