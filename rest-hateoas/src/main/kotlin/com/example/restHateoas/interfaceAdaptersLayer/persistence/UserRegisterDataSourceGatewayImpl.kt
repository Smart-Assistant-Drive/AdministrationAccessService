package com.example.restHateoas.interfaceAdaptersLayer.persistence

import com.example.restHateoas.businessLayer.adapter.UserDataSourceRequestModel
import com.example.restHateoas.businessLayer.boundaries.UserRegisterDataSourceGateway

class UserRegisterDataSourceGatewayImpl : UserRegisterDataSourceGateway {
    override fun existsByName(name: String): Boolean {
        return false
    }

    override fun save(requestModel: UserDataSourceRequestModel) {
        // Save user
    }
}