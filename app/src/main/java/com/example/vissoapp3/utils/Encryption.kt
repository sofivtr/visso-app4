package com.example.vissoapp3.utils

import java.security.MessageDigest

fun sha1(input: String): String {
    val bytes = MessageDigest.getInstance("SHA-1")
        .digest(input.toByteArray(Charsets.UTF_8))
    return bytes.joinToString("") { "%02x".format(it) }
}
