package com.example.vissoapp3.data
object UserRepository {
    private val users = listOf(
        User("admin@gmail.com", "7110eda4d09e062aa5e4a390b0a572ac0d2c0220", rol = "admin"),
        User("cliente@gmail.com", "7110eda4d09e062aa5e4a390b0a572ac0d2c0220", rol = "usuario")
    )

    fun validateUser(email: String, password: String): Boolean {
        return users.any { it.email == email && it.password == password }
    }
}
