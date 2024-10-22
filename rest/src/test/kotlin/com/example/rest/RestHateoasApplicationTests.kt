package com.example.rest

import com.example.rest.businessLayer.boundaries.UserInputBoundary
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
class RestHateoasApplicationTests {
    @MockBean
    private var userInputBoundary: UserInputBoundary? = null

    @Test
    fun contextLoads() {
        userInputBoundary = mock<UserInputBoundary>()
    }
}
