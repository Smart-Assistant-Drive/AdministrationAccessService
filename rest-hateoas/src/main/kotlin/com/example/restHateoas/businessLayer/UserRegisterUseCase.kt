package com.example.restHateoas.businessLayer

import com.example.restHateoas.businessLayer.adapter.UserDataSourceRequestModel
import com.example.restHateoas.businessLayer.adapter.UserRequestModel
import com.example.restHateoas.businessLayer.adapter.UserResponseModel
import com.example.restHateoas.businessLayer.boundaries.UserInputBoundary
import com.example.restHateoas.businessLayer.boundaries.UserRegisterDataSourceGateway
import com.example.restHateoas.businessLayer.exception.PasswordToShortException
import com.example.restHateoas.businessLayer.exception.UserAlreadyPresentException
import com.example.restHateoas.domainLayer.User
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