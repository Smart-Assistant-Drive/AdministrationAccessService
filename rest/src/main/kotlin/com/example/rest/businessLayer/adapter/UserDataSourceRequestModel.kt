package com.example.rest.businessLayer.adapter

import java.time.LocalDateTime

data class UserDataSourceRequestModel(val name: String, val password: String, val now: LocalDateTime)
