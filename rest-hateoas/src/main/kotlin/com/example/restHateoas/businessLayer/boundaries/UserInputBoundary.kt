package com.example.restHateoas.businessLayer.boundaries

import com.example.restHateoas.businessLayer.adapter.UserRequestModel
import com.example.restHateoas.businessLayer.adapter.UserResponseModel

interface UserInputBoundary {
    fun createUser(requestModel: UserRequestModel): Result<UserResponseModel>
}