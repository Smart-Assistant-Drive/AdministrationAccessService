package com.example.rest.businessLayer.adapter

import com.example.rest.domainLayer.Role
import java.time.LocalDateTime

data class UserDataSourceRequestModel(
    val name: String,
    val password: String,
    val role: Role,
    val now: LocalDateTime,
    val oldPasswords: List<String>,
)
