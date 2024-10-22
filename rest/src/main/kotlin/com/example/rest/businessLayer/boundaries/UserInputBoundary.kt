package com.example.rest.businessLayer.boundaries

import com.example.rest.businessLayer.adapter.LoginRequestModel
import com.example.rest.businessLayer.adapter.LoginResponseModel
import com.example.rest.businessLayer.adapter.TokenResponseModel
import com.example.rest.businessLayer.adapter.UserRequestModel
import com.example.rest.businessLayer.adapter.UserResponseModel

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
