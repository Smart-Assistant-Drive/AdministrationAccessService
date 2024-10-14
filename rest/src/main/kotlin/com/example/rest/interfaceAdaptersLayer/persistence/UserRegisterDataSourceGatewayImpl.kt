package com.example.rest.interfaceAdaptersLayer.persistence

import com.example.rest.businessLayer.adapter.LoginDataSourceResponseModel
import com.example.rest.businessLayer.adapter.UserDataSourceRequestModel
import com.example.rest.businessLayer.boundaries.UserRegisterDataSourceGateway
import com.example.rest.domainLayer.Role
import org.bson.Document
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.index.Index

class UserRegisterDataSourceGatewayImpl(private val mongoTemplate: MongoTemplate) : UserRegisterDataSourceGateway {

    init {
        if (!mongoTemplate.collectionExists("users")) {
            mongoTemplate.createCollection("users")
        }
        mongoTemplate.indexOps("users").ensureIndex(Index().on("name", Sort.Direction.ASC).unique())
    }

    override fun existsByName(name: String): Boolean {
        return mongoTemplate.getCollection("users").find(Document("name", name)).first() != null
    }

    override fun save(requestModel: UserDataSourceRequestModel) {
        mongoTemplate.getCollection("users").insertOne(
            Document("name", requestModel.name)
                .append("password", requestModel.password)
                .append("role", requestModel.role.name)
                .append("created", requestModel.now)
                .append("oldPasswords", requestModel.oldPasswords.mapIndexed { index, password ->
                    Document("order", index).append("password", password)
                })
        )
    }

    override fun findUser(name: String): LoginDataSourceResponseModel? {
        val user = mongoTemplate.getCollection("users").find(Document("name", name)).first()
        return user?.let {
            LoginDataSourceResponseModel(
                it.getString("name"),
                it.getString("password"),
                Role.valueOf(it.getString("role"))
            )
        }
    }
}