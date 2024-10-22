package com.example.rest.domainLayer.policy

object PasswordPolicy {
    private const val MIN_LENGTH = 8
    private const val MAX_LENGTH = 20

    fun isValid(password: String): Boolean = password.length in MIN_LENGTH..MAX_LENGTH && password.isNotBlank()
}
