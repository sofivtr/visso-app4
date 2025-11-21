package com.example.vissoapp3.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalContext
import com.example.vissoapp3.Routes
import com.example.vissoapp3.data.UserRepository
import com.example.vissoapp3.utils.sha1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    paddingValues: PaddingValues = PaddingValues()
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var usernameError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold() { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(paddingValues)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White,
                            Color(0xFFE3F2FD)
                        )
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))

            Text(
                text = "Inicia Sesión",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1976D2),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            TextField(
                value = username,
                onValueChange = {
                    if (it.length <= 30) username = it
                },
                label = { Text("Usuario") },
                leadingIcon = {
                    Icon(Icons.Rounded.AccountCircle, contentDescription = "Usuario")
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color(0xFFF3F1F1)
                ),
                supportingText = {
                    if (usernameError.isNotEmpty()) {
                        Text(
                            text = usernameError,
                            color = Color.Red,
                            fontSize = 13.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            var passwordVisible by remember { mutableStateOf(false) }

            TextField(
                value = password,
                onValueChange = {
                    if (it.length <= 40) password = it
                },
                label = { Text("Contraseña") },
                leadingIcon = {
                    Icon(Icons.Rounded.Lock, contentDescription = "Contraseña")
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color(0xFFF3F1F1)
                ),
                supportingText = {
                    if (passwordError.isNotEmpty()) {
                        Text(
                            text = passwordError,
                            color = Color.Red,
                            fontSize = 13.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(50.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF64B5F6),
                                Color(0xFF1976D2)
                            )
                        )
                    )
                    .clickable {
                        usernameError =
                            if (username.isBlank()) "El usuario no puede estar vacío" else ""
                        passwordError =
                            if (password.isBlank()) "La contraseña no puede estar vacía" else ""

                        if (usernameError.isEmpty() && passwordError.isEmpty()) {

                            val encryptedPassword = sha1(password)

                            val isValid = UserRepository.validateUser(username, encryptedPassword)

                            if (isValid) {
                                navController.navigate(Routes.Home)
                            } else {
                                android.widget.Toast.makeText(
                                    context,
                                    "Credenciales incorrectas, la contraseña tuvo que ser $encryptedPassword",
                                    android.widget.Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "INGRESAR",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "¿No tienes cuenta? Regístrate aquí",
                color = Color(0xFF1976D2),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    navController.navigate(Routes.Register)
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen(navController = rememberNavController())
}
