package com.example.vissoapp3.utils

fun validarRut(rut: String): Boolean {
    // Implementación simple módulo 11
    // (Puedes pegar tu lógica completa aquí si la tienes, esta es simplificada)
    return rut.length >= 8 && rut.contains("-")
}

fun validarEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}