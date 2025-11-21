package com.example.vissoapp3.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vissoapp3.R
import com.example.vissoapp3.Routes
import androidx.navigation.NavController
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navbar(
    @DrawableRes logoResId: Int,
    onLogoClick: () -> Unit,
    showBackButton: Boolean = false,
    onBackClick: (() -> Unit)? = null,
    menuItems: List<String> = emptyList(),
    onMenuItemClick: ((String) -> Unit)? = null,
    selectedItem: String? = null
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onLogoClick() }
                ) {
                    Image(
                        painter = painterResource(id = logoResId),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(40.dp)
                            .padding(end = 8.dp)
                    )
                    Text(
                        text = "Visso",
                        color = Color(0xFF2196F3),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            navigationIcon = {
                if (showBackButton && onBackClick != null) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color(0xFF2196F3)
                        )
                    }
                }
            },
            actions = {
                if (menuItems.isNotEmpty()) {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menú",
                            tint = Color(0xFF2196F3)
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        menuItems.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    if (selectedItem != null && selectedItem == item) {
                                        Text(item, color = Color(0xFF2196F3))
                                    } else {
                                        Text(item)
                                    }
                                },
                                onClick = {
                                    expanded = false
                                    onMenuItemClick?.invoke(item)
                                }
                            )
                        }
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(64.dp)
        )

        Divider(
            color = Color.Black,
            thickness = 1.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NavbarPreview() {
    MaterialTheme {
        Navbar(
            logoResId = R.drawable.logo,
            onLogoClick = { println("Logo clickeado") },
            showBackButton = true,
            menuItems = listOf("Configuración", "Perfil", "Cerrar sesión"),
            onMenuItemClick = { println("Acción: $it") }
        )
    }
}
