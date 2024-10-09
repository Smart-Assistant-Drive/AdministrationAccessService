package com.example.rest.businessLayer.boundaries

import com.example.rest.businessLayer.adapter.UserRequestModel
import com.example.rest.businessLayer.adapter.UserResponseModel

interface UserInputBoundary {
    fun createUser(requestModel: UserRequestModel): Result<UserResponseModel>
}