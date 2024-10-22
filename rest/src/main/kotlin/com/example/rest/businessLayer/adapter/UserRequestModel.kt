package com.example.rest.businessLayer.adapter

import com.example.rest.domainLayer.Role

data class UserRequestModel(val name: String, val password: String, val role: Role)
