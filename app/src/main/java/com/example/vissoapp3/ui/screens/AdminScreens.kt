package com.example.vissoapp3.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.vissoapp3.data.model.*
import com.example.vissoapp3.ui.viewmodel.AppViewModel

@Composable
fun AdminDashboardScreen(navController: NavController, viewModel: AppViewModel) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(selected = selectedTab == 0, onClick = { selectedTab = 0 }, icon = { Text("Ventas") }, label = { Text("Historial") })
                NavigationBarItem(selected = selectedTab == 1, onClick = { selectedTab = 1 }, icon = { Icon(Icons.Default.Add, null) }, label = { Text("Productos") })
            }
        }
    ) { p ->
        Box(Modifier.padding(p)) {
            if (selectedTab == 0) AdminSalesList(viewModel)
            else AdminProductCrud(viewModel)
        }
    }
}

@Composable
fun AdminSalesList(viewModel: AppViewModel) {
    LaunchedEffect(Unit) { viewModel.cargarVentasAdmin() }
    LazyColumn {
        items(viewModel.historialVentas) { venta ->
            Card(Modifier.padding(8.dp).fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Venta #${venta.id} - Total: $${venta.total}")
                    Text("Usuario ID: ${venta.id}") // En un caso real, traer nombre
                    Text("Estado: ${venta.estado}")
                }
            }
        }
    }
}

@Composable
fun AdminProductCrud(viewModel: AppViewModel) {
    // Estado del formulario
    var nombre by remember { mutableStateOf("") }
    var codigo by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Launchers para Cámara y Galería
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imageUri = uri
    }
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        // Aquí simplificamos para el ejemplo, idealmente guardar bitmap a URI temporal
        // Para este entregable, priorizamos la lógica de UI
    }

    Column(Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
        Text("Nuevo Producto", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(value = codigo, onValueChange = { codigo = it }, label = { Text("Código (ej: OPT-01)") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = precio, onValueChange = { precio = it }, label = { Text("Precio") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = stock, onValueChange = { stock = it }, label = { Text("Stock") }, modifier = Modifier.fillMaxWidth())

        Spacer(Modifier.height(16.dp))

        // Selector de Imagen
        Row {
            Button(onClick = { galleryLauncher.launch("image/*") }) { Text("Galería") }
            Spacer(Modifier.width(8.dp))
            Button(onClick = { cameraLauncher.launch(null) }) { Text("Cámara") }
        }

        if (imageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = null,
                modifier = Modifier.size(100.dp).border(1.dp, Color.Gray)
            )
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                val prod = Producto(
                    codigoProducto = codigo,
                    nombre = nombre,
                    descripcion = "Generado por Admin",
                    precio = precio.toDoubleOrNull() ?: 0.0,
                    stock = stock.toIntOrNull() ?: 0,
                    tipo = "O", // Por defecto Óptico para el ejemplo
                    imagenUrl = null, // Se maneja en VM
                    categoria = CategoriaId(1), // Hardcoded 1 para ejemplo
                    marca = MarcaId(1) // Hardcoded 1 para ejemplo
                )
                viewModel.crearProductoAdmin(prod, imageUri)
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("GUARDAR PRODUCTO") }
    }
}