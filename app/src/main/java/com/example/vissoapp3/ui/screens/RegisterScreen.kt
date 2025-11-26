package com.example.vissoapp3.ui.screens

package com.example.vissoapp3.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vissoapp3.data.api.RetrofitClient
import com.example.vissoapp3.data.model.Usuario
import com.example.vissoapp3.utils.validarEmail
import com.example.vissoapp3.utils.validarRut
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController) {
    // Datos del formulario (Agregué Apellido y RUT que faltaban)
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var rut by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Roles
    var expanded by remember { mutableStateOf(false) }
    val roles = listOf("Usuario", "Administrador")
    var selectedRole by remember { mutableStateOf(roles[0]) }

    // Estado
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()), // Scroll por si el teclado tapa campos
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Crear Cuenta",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1565C0)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Nombre
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            leadingIcon = { Icon(Icons.Rounded.Person, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Apellido (NUEVO - Requerido por Backend)
        OutlinedTextField(
            value = apellido,
            onValueChange = { apellido = it },
            label = { Text("Apellido") },
            leadingIcon = { Icon(Icons.Rounded.Person, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // RUT (NUEVO - Requerido por Profe)
        val isRutError = rut.isNotEmpty() && !validarRut(rut)
        OutlinedTextField(
            value = rut,
            onValueChange = { rut = it },
            label = { Text("RUT (ej: 12345678-9)") },
            leadingIcon = { Icon(Icons.Rounded.Badge, contentDescription = null) }, // Icono de credencial
            isError = isRutError,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        if(isRutError) Text("RUT inválido", color = Color.Red, fontSize = 12.sp)

        Spacer(modifier = Modifier.height(8.dp))

        // Email
        val isEmailError = email.isNotEmpty() && !validarEmail(email)
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico") },
            leadingIcon = { Icon(Icons.Rounded.Email, contentDescription = null) },
            isError = isEmailError,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        if(isEmailError) Text("Formato inválido (@duocuc.cl, etc)", color = Color.Red, fontSize = 12.sp)

        Spacer(modifier = Modifier.height(8.dp))

        // Password
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            leadingIcon = { Icon(Icons.Rounded.Lock, contentDescription = null) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown Rol
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = selectedRole,
                onValueChange = {},
                readOnly = true,
                label = { Text("Rol") },
                trailingIcon = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                roles.forEach { role ->
                    DropdownMenuItem(
                        text = { Text(role) },
                        onClick = {
                            selectedRole = role
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botón Registrarse
        Button(
            onClick = {
                // Validaciones antes de enviar
                if (validarRut(rut) && validarEmail(email) && nombre.isNotEmpty() && password.isNotEmpty()) {
                    isLoading = true
                    val rolEnvio = if (selectedRole == "Administrador") "ADMIN" else "USER"

                    scope.launch {
                        try {
                            val usuario = Usuario(
                                nombre = nombre,
                                apellido = apellido,
                                rut = rut,
                                email = email,
                                passwordHash = password, // El backend la hasheará
                                rol = rolEnvio
                            )

                            // CONEXIÓN BACKEND
                            val res = RetrofitClient.apiService.registrar(usuario)

                            if (res.isSuccessful) {
                                Toast.makeText(context, "Registro exitoso", Toast.LENGTH_LONG).show()
                                navController.navigate("login") // Volver al login
                            } else {
                                Toast.makeText(context, "Error: El usuario ya existe", Toast.LENGTH_LONG).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "Error de conexión", Toast.LENGTH_LONG).show()
                        } finally {
                            isLoading = false
                        }
                    }
                } else {
                    Toast.makeText(context, "Por favor corrige los campos en rojo", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0)),
            shape = RoundedCornerShape(12.dp),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White)
            } else {
                Text("REGISTRARSE", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { navController.popBackStack() }) {
            Text("¿Ya tienes cuenta? Inicia sesión", color = Color(0xFF1565C0))
        }
    }
}