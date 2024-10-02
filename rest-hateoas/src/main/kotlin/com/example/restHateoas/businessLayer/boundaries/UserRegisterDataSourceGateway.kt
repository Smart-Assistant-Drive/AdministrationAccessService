package com.example.restHateoas.businessLayer.boundaries

import com.example.restHateoas.businessLayer.adapter.UserDataSourceRequestModel

interface UserRegisterDataSourceGateway {
    fun existsByName(name: String): Boolean

    fun save(requestModel: UserDataSourceRequestModel)
}