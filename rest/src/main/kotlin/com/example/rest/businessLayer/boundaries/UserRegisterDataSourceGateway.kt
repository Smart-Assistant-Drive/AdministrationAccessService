package com.example.rest.businessLayer.boundaries

import com.example.rest.businessLayer.adapter.login.LoginDataSourceResponseModel
import com.example.rest.businessLayer.adapter.user.UserDataSourceRequestModel

interface UserRegisterDataSourceGateway {
    fun existsByName(name: String): Boolean

    fun save(requestModel: UserDataSourceRequestModel)

    fun findUser(name: String): LoginDataSourceResponseModel?
}
