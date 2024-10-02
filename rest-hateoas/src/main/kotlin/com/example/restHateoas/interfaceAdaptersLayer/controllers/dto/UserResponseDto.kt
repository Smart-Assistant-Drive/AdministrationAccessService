package com.example.restHateoas.interfaceAdaptersLayer.controllers.dto

import com.example.restHateoas.businessLayer.adapter.UserRequestModel
import com.example.restHateoas.businessLayer.adapter.UserResponseModel
import com.example.restHateoas.interfaceAdaptersLayer.controllers.GreetingController
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class UserResponseDto @JsonCreator constructor(
    @param:JsonProperty(
        "name"
    ) val name: String,
    @param:JsonProperty(
        "time"
    ) val time: String
) : RepresentationModel<UserResponseDto?>()

fun UserResponseModel.toDto(userRequestModel: UserRequestModel): UserResponseDto {
    val responseTime = LocalDateTime.parse(time)
    val jsonTime = responseTime.format(DateTimeFormatter.ofPattern("hh:mm:ss"))
    return UserResponseDto(name, jsonTime).add(
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GreetingController::class.java).create(userRequestModel))
            .withSelfRel()
    )
}
