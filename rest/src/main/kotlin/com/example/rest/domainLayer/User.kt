package com.example.rest.domainLayer

interface User {
    val name: String

    val password: String

    val role: Role

    companion object {
        fun create(
            name: String,
            password: String,
            role: Role,
        ): User =
            object : User {
                override val name: String = name
                override val password: String = password
                override val role: Role = role
            }
    }
}
