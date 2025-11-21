package com.example.vissoapp3.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.vissoapp3.R
import com.example.vissoapp3.Routes
import com.example.vissoapp3.ui.components.Navbar

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            Navbar(
                logoResId = R.drawable.logo,
                onLogoClick = {},
                showBackButton = false,
                menuItems = listOf("Inicio", "Categorías", "Cerrar sesión"),
                selectedItem = "Inicio",
                onMenuItemClick = { item ->
                    when (item) {
                        "Inicio" -> {
                            navController.navigate(Routes.Home)
                        }
                        "Categorías" -> {
                            navController.navigate(Routes.Categories)
                        }
                        "Cerrar sesión" -> {
                            navController.navigate(Routes.Login)
                        }
                    }
                }
            )
        },
        bottomBar = { com.example.vissoapp3.ui.components.AppBottomBar(navController) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF2196F3))
                        .statusBarsPadding()
                        .padding(vertical = 32.dp, horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.home_image),
                            contentDescription = "Imagen principal",
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .aspectRatio(16f / 9f)
                                .clip(RoundedCornerShape(24.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Tu visión, nuestra pasión",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Sobre Visso Óptica",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFF2196F3),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Atención personalizada y soluciones visuales",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "En Visso Óptica, creemos que la visión es una parte fundamental de tu bienestar. " +
                            "Por eso, trabajamos con tecnología de punta y un trato humano excepcional para que cada visita sea una experiencia única.",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StatBox(
                        iconRes = R.drawable.icon_clients,
                        title = "+5000",
                        subtitle = "clientes satisfechos"
                    )
                    StatBox(
                        iconRes = R.drawable.icon_rating,
                        title = "4.9/5",
                        subtitle = "calificación"
                    )
                }

                Spacer(modifier = Modifier.height(64.dp))
            }
        }
    }
}

@Composable
fun StatBox(iconRes: Int, title: String, subtitle: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = subtitle,
            modifier = Modifier
                .size(60.dp)
                .padding(bottom = 8.dp)
        )
        Text(
            text = title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2196F3)
        )
        Text(
            text = subtitle,
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}
