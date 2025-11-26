package com.example.vissoapp3.utils
import android.util.Patterns

fun validarRut(rut: String): Boolean {
    val rutLimpio = rut.replace(".", "").replace("-", "")
    if (rutLimpio.length < 2) return false
    val cuerpo = rutLimpio.substring(0, rutLimpio.length - 1)
    val dv = rutLimpio.last()

    var suma = 0
    var multiplicador = 2
    for (i in cuerpo.reversed()) {
        suma += (i.toString().toInt() * multiplicador)
        multiplicador++
        if (multiplicador > 7) multiplicador = 2
    }

    val esperado = 11 - (suma % 11)
    val dvEsperado = when (esperado) {
        11 -> '0'
        10 -> 'K'
        else -> esperado.toString()[0]
    }

    return dv.uppercaseChar() == dvEsperado
}

fun validarEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}