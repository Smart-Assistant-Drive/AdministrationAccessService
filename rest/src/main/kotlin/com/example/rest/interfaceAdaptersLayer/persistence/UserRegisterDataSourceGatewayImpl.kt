package com.example.rest.interfaceAdaptersLayer.persistence

import com.example.rest.businessLayer.adapter.UserDataSourceRequestModel
import com.example.rest.businessLayer.boundaries.UserRegisterDataSourceGateway
import org.bson.Document
import org.springframework.data.mongodb.core.MongoTemplate

class UserRegisterDataSourceGatewayImpl(private val mongoTemplate: MongoTemplate) : UserRegisterDataSourceGateway {
    override fun existsByName(name: String): Boolean {
        return mongoTemplate.getCollection("users").find(Document("name", name)).first() != null
    }

    override fun save(requestModel: UserDataSourceRequestModel) {
        mongoTemplate.getCollection("users").insertOne(
            Document("name", requestModel.name).append("password", requestModel.password)
        )
    }
}