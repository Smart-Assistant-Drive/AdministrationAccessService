package com.example.restHateoas.domainLayer

interface User {
    fun passwordIsValid(): Boolean

    val name: String

    val password: String

    companion object {
        fun create(name: String, password: String): User {
            return object : User {
                override val name: String = name
                override val password: String = password

                override fun passwordIsValid(): Boolean {
                    return password.isNotBlank() && password.length > 5
                }
            }
        }
    }
}

