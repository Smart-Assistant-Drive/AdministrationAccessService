package com.example.restHateoas

import com.example.restHateoas.businessLayer.UserRegisterUseCase
import com.example.restHateoas.businessLayer.boundaries.UserInputBoundary
import com.example.restHateoas.interfaceAdaptersLayer.persistence.UserRegisterDataSourceGatewayImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {

    @Bean
    fun userInput(): UserInputBoundary {
        val userRegisterDataSourceGateway = UserRegisterDataSourceGatewayImpl()
        val userRegisterUseCase = UserRegisterUseCase(userRegisterDataSourceGateway)
        return userRegisterUseCase
    }
}