package com.example.restHateoas.interfaceAdaptersLayer.controllers.dto

import com.example.restHateoas.businessLayer.adapter.UserRequestModel
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class UserRequestDto @JsonCreator constructor(
    @param:JsonProperty(
        "name"
    )
    val name: String,
    @param:JsonProperty(
        "password"
    )
    val password: String)

fun UserRequestDto.toModel() = UserRequestModel(name, password)
