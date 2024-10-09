package com.example.rest.interfaceAdaptersLayer.controllers

import com.example.rest.businessLayer.adapter.UserRequestModel
import com.example.rest.businessLayer.boundaries.UserInputBoundary
import com.example.rest.businessLayer.exception.PasswordToShortException
import com.example.rest.businessLayer.exception.UserAlreadyPresentException
import com.example.rest.interfaceAdaptersLayer.controllers.dto.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
class GreetingController(val userInput: UserInputBoundary) {

    @Operation(summary = "Create user",
        description = "Create user",
        requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = [Content(mediaType = "application/json",
                schema = Schema(implementation = UserRequestModel::class))],
            required = true),
        responses = [
            ApiResponse(responseCode = "201", description = "Created user",
                content = [Content(mediaType = "application/json",
                    schema = Schema(implementation = UserResponseDto::class))]),
            ApiResponse(responseCode = "400", description = "Invalid user",
                content = [Content()]),
            ApiResponse(responseCode = "409", description = "User already exists",
                content = [Content()]),
            ApiResponse(responseCode = "500", description = "Internal server error",
                content = [Content()])])
    @PostMapping("/user")
    fun create(@RequestBody requestModel: UserRequestDto): HttpEntity<UserResponseDto> {
        val result = userInput.createUser(requestModel.toModel())
        return if (result.isSuccess) {
            ResponseEntity(result.getOrNull()!!.toDto(requestModel), HttpStatus.CREATED)
        } else {
            when (result.exceptionOrNull()) {
                is UserAlreadyPresentException -> ResponseEntity.status(HttpStatus.CONFLICT).build()
                is PasswordToShortException -> ResponseEntity.badRequest().build()
                else -> ResponseEntity.internalServerError().build()
            }
        }
    }

    @GetMapping("/greeting")
    fun greeting(
        @RequestParam(value = "name", defaultValue = "World") name: String?
    ): HttpEntity<Greeting> {
        val greeting = Greeting(String.format(TEMPLATE, name))
        greeting.add(
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GreetingController::class.java).greeting(name))
                .withSelfRel()
            //Link.of("http://localhost:4000/greeting?name=$name").withSelfRel()
        )

        return ResponseEntity(greeting, HttpStatus.OK)
    }

    companion object {
        private const val TEMPLATE = "Hello, %s!"
    }
}