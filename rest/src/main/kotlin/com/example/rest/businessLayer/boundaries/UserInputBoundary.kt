package com.example.rest.businessLayer.boundaries

import com.example.rest.businessLayer.adapter.*

interface UserInputBoundary {
    fun createUser(requestModel: UserRequestModel): Result<UserResponseModel>

    fun login(requestModel: LoginRequestModel): Result<LoginResponseModel>

    fun checkUserToken(token: String): Result<TokenResponseModel>

    /*
    fun changePassword(requestModel: UserRequestModel): Result<UserResponseModel>

    fun deleteUser(requestModel: UserRequestModel): Result<UserResponseModel>

    fun changeRole(requestModel: UserRequestModel): Result<UserResponseModel>

     */
}