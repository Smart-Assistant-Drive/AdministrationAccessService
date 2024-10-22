package com.example.rest.interfaceAdaptersLayer.controllers.dto.createUser

import com.example.rest.businessLayer.adapter.user.UserResponseModel
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.Link
import org.springframework.hateoas.RepresentationModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class UserResponseDto
    @JsonCreator
    constructor(
        @param:JsonProperty("name") val name: String,
        @param:JsonProperty("token") val token: String,
        @param:JsonProperty("time") val time: String,
    ) : RepresentationModel<UserResponseDto>()

fun UserResponseModel.toDto(links: List<Link>): UserResponseDto {
    val responseTime = LocalDateTime.parse(time)
    val jsonTime = responseTime.format(DateTimeFormatter.ofPattern("hh:mm:ss"))
    return UserResponseDto(name, token, jsonTime).add(links)
}
