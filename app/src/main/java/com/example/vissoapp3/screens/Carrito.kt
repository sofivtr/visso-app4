package com.example.vissoapp3.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vissoapp3.R
import com.example.vissoapp3.data.ProductItem
import com.example.vissoapp3.ui.components.Navbar
import com.example.vissoapp3.ui.components.AppBottomBar

val sampleProducts = listOf(
    ProductItem("Lentes de Sol", "Ray-Ban", 150, R.drawable.lente1),
    ProductItem("Lentes Graduados", "Oakley", 200, R.drawable.lente2)
)

@Composable
fun CarritoScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {

        // Navbar
        Navbar(
            logoResId = R.drawable.logo,
            onLogoClick = { /* Acciones al hacer clic en el logo */ },
            showBackButton = true,
            onBackClick = { navController.popBackStack() }
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            items(sampleProducts) { product ->
                CarritoItem(product)
                Divider(thickness = 1.dp)
            }
        }

        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Total: $350",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { /* Acción al hacer clic en "Finalizar compra" */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Finalizar compra")
            }
        }

        // Bottom Bar
        AppBottomBar(navController)
    }
}

@Composable
fun CarritoItem(product: ProductItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagen del producto
        Image(
            painter = painterResource(id = product.imageRes),
            contentDescription = product.name,
            modifier = Modifier
                .size(90.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Información del producto
        Column(modifier = Modifier.weight(1f)) {
            Text(product.name, style = MaterialTheme.typography.titleMedium)
            Text(product.brand, color = Color.Gray)
            Text("$${product.price}", style = MaterialTheme.typography.bodyLarge)
        }

        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Eliminar"
            )
        }
    }
}

@Composable
fun TotalAndCheckout(totalPrice: Int) {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(
            text = "Total: $${totalPrice}",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { /* Acción al hacer clic en "Finalizar compra" */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Finalizar compra")
        }
    }
}
