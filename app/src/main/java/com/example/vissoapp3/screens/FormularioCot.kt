package com.example.vissoapp3.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.vissoapp3.R
import com.example.vissoapp3.ui.components.Navbar
import com.example.vissoapp3.ui.components.AppBottomBar
import androidx.compose.material3.Scaffold
import java.lang.StringBuilder

private fun sanitizeGradInput(input: String): String {
    val sb = StringBuilder()
    var hasDot = false
    for ((i, ch) in input.withIndex()) {
        when {
            ch == '-' && i == 0 -> sb.append(ch)
            ch == '.' && !hasDot -> { sb.append(ch); hasDot = true }
            ch.isDigit() -> sb.append(ch)
        }
    }
    return sb.toString()
}

private fun isGraduationValid(value: String): Boolean {
    if (value.isBlank()) return false
    val v = value.toDoubleOrNull() ?: return false
    return v >= -10.0 && v <= 10.0
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioCotScreen(navController: NavController, nombreLente: String = "Lente") {
    var tipoMarco by remember { mutableStateOf("") }
    var tipoCristal by remember { mutableStateOf("") }
    var gradIzq by remember { mutableStateOf("") }
    var gradDer by remember { mutableStateOf("") }
    var gradIzqError by remember { mutableStateOf("") }
    var gradDerError by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("1") }
    val context = androidx.compose.ui.platform.LocalContext.current

    Scaffold(
        topBar = {
            Navbar(
                logoResId = R.drawable.logo,
                onLogoClick = { /* acción logo */ },
                showBackButton = true,
                onBackClick = { navController.popBackStack() }
            )
        },
        bottomBar = { AppBottomBar(navController) }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF2F6FF))
                .padding(innerPadding)
        ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color(0xFF7FE1F5), Color(0xFF1A73E8))
                            )
                        ),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = nombreLente.ifBlank { "Lente Óptico" },
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {

                Text(
                    text = "Personaliza tu diseño!",
                    color = Color(0xFF1A73E8),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 4.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, Color(0xFF1A73E8), RoundedCornerShape(12.dp))
                        .background(Color(0xFFF9FBFF))
                        .padding(12.dp)
                ) {
                    Column {
                        val marcoOptions = listOf("Metálico", "Plástico", "Mixto")
                        val cristalOptions = listOf("Monofocal", "Bifocal", "Progresivo")

                        var expandedMarco by remember { mutableStateOf(false) }
                        var expandedCristal by remember { mutableStateOf(false) }

                        Text("Tipo de Marco:", color = Color(0xFF1A73E8), fontSize = 17.sp, fontWeight = FontWeight.Bold)
                        ExposedDropdownMenuBox(
                            expanded = expandedMarco,
                            onExpandedChange = { expandedMarco = !expandedMarco }
                        ) {
                            TextField(
                                value = tipoMarco,
                                onValueChange = {},
                                readOnly = true,
                                placeholder = { Text("Seleccione") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedMarco) },
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedContainerColor = Color(0xFFF9FBFF),
                                    unfocusedContainerColor = Color(0xFFF9FBFF)
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor()
                            )

                            ExposedDropdownMenu(
                                expanded = expandedMarco,
                                onDismissRequest = { expandedMarco = false }
                            ) {
                                marcoOptions.forEach { selection ->
                                    DropdownMenuItem(
                                        text = { Text(selection) },
                                        onClick = {
                                            tipoMarco = selection
                                            expandedMarco = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Tipo de Cristal:", color = Color(0xFF1A73E8), fontSize = 17.sp, fontWeight = FontWeight.Bold)
                        ExposedDropdownMenuBox(
                            expanded = expandedCristal,
                            onExpandedChange = { expandedCristal = !expandedCristal }
                        ) {
                            TextField(
                                value = tipoCristal,
                                onValueChange = {},
                                readOnly = true,
                                placeholder = { Text("Seleccione") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCristal) },
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedContainerColor = Color(0xFFF9FBFF),
                                    unfocusedContainerColor = Color(0xFFF9FBFF)
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor()
                            )

                            ExposedDropdownMenu(
                                expanded = expandedCristal,
                                onDismissRequest = { expandedCristal = false }
                            ) {
                                cristalOptions.forEach { selection ->
                                    DropdownMenuItem(
                                        text = { Text(selection) },
                                        onClick = {
                                            tipoCristal = selection
                                            expandedCristal = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Ingrese su graduación:",
                            color = Color(0xFF1A73E8),
                            fontSize = 17.sp
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CustomTextField(
                                value = gradIzq,
                                placeholder = "Izquierdo",
                                onValueChange = { gradIzq = sanitizeGradInput(it); if (gradIzqError.isNotEmpty()) gradIzqError = "" },
                                modifier = Modifier.weight(1f).height(50.dp)
                            )
                            CustomTextField(
                                value = gradDer,
                                placeholder = "Derecho",
                                onValueChange = { gradDer = sanitizeGradInput(it); if (gradDerError.isNotEmpty()) gradDerError = "" },
                                modifier = Modifier.weight(1f).height(50.dp)
                            )
                        }

                        if (gradIzqError.isNotEmpty()) {
                            Text(text = gradIzqError, color = Color.Red, fontSize = 13.sp, modifier = Modifier.padding(top = 4.dp))
                        }
                        if (gradDerError.isNotEmpty()) {
                            Text(text = gradDerError, color = Color.Red, fontSize = 13.sp, modifier = Modifier.padding(top = 4.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .border(1.dp, Color(0xFF1A73E8), RoundedCornerShape(10.dp))
                        .background(Color(0xFFF9FBFF))
                        .padding(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Cantidad:",
                            color = Color(0xFF1A73E8),
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .border(1.dp, Color(0xFF1A73E8), RoundedCornerShape(50))
                                    .clickable {
                                        val cant = cantidad.toIntOrNull() ?: 1
                                        if (cant > 1) cantidad = (cant - 1).toString()
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text("-", color = Color(0xFF1A73E8), fontSize = 20.sp)
                            }

                            CustomTextField(
                                value = cantidad,
                                placeholder = "",
                                onValueChange = { cantidad = it },
                                modifier = Modifier.width(70.dp).height(50.dp)
                            )

                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .border(1.dp, Color(0xFF1A73E8), RoundedCornerShape(50))
                                    .clickable {
                                        val cant = cantidad.toIntOrNull() ?: 1
                                        cantidad = (cant + 1).toString()
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text("+", color = Color(0xFF1A73E8), fontSize = 20.sp)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color(0xFF7FE1F5), Color(0xFF1A73E8))
                            )
                        )
                        .clickable {
                            val cantidadVal = cantidad.toIntOrNull()

                            var ok = true
                            if (!isGraduationValid(gradIzq)) {
                                gradIzqError = "Ingrese una graduación válida entre -10.00 y +10.00"
                                ok = false
                            }
                            if (!isGraduationValid(gradDer)) {
                                gradDerError = "Ingrese una graduación válida entre -10.00 y +10.00"
                                ok = false
                            }

                            val otherFilled = tipoMarco.isNotBlank() && tipoCristal.isNotBlank() &&
                                    cantidad.isNotBlank() && cantidadVal != null && cantidadVal > 0

                            if (!ok || !otherFilled) {
                                android.widget.Toast.makeText(context, "Complete todos los campos", android.widget.Toast.LENGTH_SHORT).show()
                            } else {
                                android.widget.Toast.makeText(context, "Agregado al carrito", android.widget.Toast.LENGTH_SHORT).show()
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "AGREGAR AL CARRITO",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        modifier = modifier,
        textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color(0xFF1A73E8),
            unfocusedIndicatorColor = Color.Gray,
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCotizacionScreen() {
    FormularioCotScreen(navController = rememberNavController())
}
