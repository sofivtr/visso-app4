package com.example.vissoapp3.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.vissoapp3.R
import com.example.vissoapp3.data.CategoryItem
import com.example.vissoapp3.ui.components.Navbar
import com.example.vissoapp3.Routes
import androidx.compose.material3.Scaffold



@Composable
fun CategoriesScreen(navController: NavController) {
    val categories = listOf(
        CategoryItem("Ofertas", R.drawable.icon_ofertas),
        CategoryItem("Lentes Ópticos", R.drawable.icon_optical),
        CategoryItem("Monturas", R.drawable.icon_frames),
        CategoryItem("Lentes de Sol", R.drawable.icon_sunglasses),
        CategoryItem("Lentes de Contacto", R.drawable.icon_contacts),
        CategoryItem("Accesorios", R.drawable.icon_accessories)
    )

    Scaffold(
        topBar = {
            Navbar(
                logoResId = R.drawable.logo,
                onLogoClick = { /* acción logo */ },
                showBackButton = false,
                menuItems = listOf("Inicio", "Categorías", "Cerrar sesión"),
                selectedItem = "Categorías",
                onMenuItemClick = { item ->
                    when (item) {
                        "Inicio" -> navController.navigate(Routes.Home)
                        "Categorías" -> {}
                        "Cerrar sesión" -> navController.navigate(Routes.Login)
                    }
                }
            )
        },
        bottomBar = { com.example.vissoapp3.ui.components.AppBottomBar(navController) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF5F5F5))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                for (i in categories.indices step 2) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        CategoryBox(item = categories[i], navController = navController)

                        if (i + 1 < categories.size) {
                            CategoryBox(item = categories[i + 1], navController = navController)
                        } else {
                            Spacer(modifier = Modifier.width(150.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
fun CategoryBox(item: CategoryItem, navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable {
                    if (item.name == "Lentes Ópticos") {
                        navController.navigate(Routes.LentesOpticos)
                    } else {
                        android.widget.Toast.makeText(context, "Sin productos", android.widget.Toast.LENGTH_SHORT).show()
                    }
                }
        ) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = item.name,
            color = Color(0xFF4DAEEF),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCategoriesScreen() {
    CategoriesScreen(navController = rememberNavController())
}
