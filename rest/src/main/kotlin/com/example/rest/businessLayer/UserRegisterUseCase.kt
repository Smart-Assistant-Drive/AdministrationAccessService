package com.example.rest.businessLayer

import com.example.rest.businessLayer.adapter.UserDataSourceRequestModel
import com.example.rest.businessLayer.adapter.UserRequestModel
import com.example.rest.businessLayer.adapter.UserResponseModel
import com.example.rest.businessLayer.boundaries.UserInputBoundary
import com.example.rest.businessLayer.boundaries.UserRegisterDataSourceGateway
import com.example.rest.businessLayer.exception.PasswordToShortException
import com.example.rest.businessLayer.exception.UserAlreadyPresentException
import com.example.rest.domainLayer.User
import java.time.LocalDateTime

class UserRegisterUseCase(
    private var userDataSourceGateway: UserRegisterDataSourceGateway,
) : UserInputBoundary {

    // Constructor
    override fun createUser(requestModel: UserRequestModel): Result<UserResponseModel> {
        if (userDataSourceGateway.existsByName(requestModel.name)) {
            return Result.failure(UserAlreadyPresentException())
        }
        val user: User = User.create(requestModel.name, requestModel.password)
        if (!user.passwordIsValid()) {
            return Result.failure(PasswordToShortException())
        }
        val now = LocalDateTime.now()
        val userDataSourceModel = UserDataSourceRequestModel(user.name, user.password, now)

        userDataSourceGateway.save(userDataSourceModel)

        val accountResponseModel = UserResponseModel(user.name, now.toString())
        return Result.success(accountResponseModel)
    }
}