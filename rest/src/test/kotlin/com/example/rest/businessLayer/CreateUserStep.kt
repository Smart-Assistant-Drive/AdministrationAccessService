package com.example.rest.businessLayer

import com.example.rest.businessLayer.adapter.UserRequestModel
import com.example.rest.businessLayer.boundaries.UserRegisterDataSourceGateway
import com.example.rest.businessLayer.boundaries.UserSecurity
import com.example.rest.domainLayer.Role
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@RunWith(Cucumber::class)
@CucumberOptions(
    plugin = ["pretty", "html:target/cucumber-report.html"],
    features = ["src/test/resources"],
)
class CreateUserStep {
    private var name: String? = null
    private var password: String? = null
    private var role: Role? = null
    private val interaction: UserRegisterUseCase
    private val userRegisterDataSourceGateway =
        mock<UserRegisterDataSourceGateway> {
            on { save(any()) } doAnswer {}
            on { existsByName(anyString()) } doReturn false
        }
    private val userSecurity =
        mock<UserSecurity> {
            on { getHash(anyString()) } doReturn "hashed"
        }

    init {
        interaction =
            UserRegisterUseCase(
                userRegisterDataSourceGateway,
                userSecurity,
            )
    }

    @Given("the user to create is {string}")
    fun the_user_inserts_a_name(name: String) {
        this.name = name
    }

    @Given("its password is {string}")
    fun the_user_inserts_a_password(password: String) {
        this.password = password
    }

    @Given("its role is {string}")
    fun the_user_inserts_a_role(role: String) {
        this.role = Role.valueOf(role)
    }

    @When("the user is created")
    @Throws(Throwable::class)
    fun password_validated() {
        val userRequestModel = UserRequestModel(name!!, password!!, role!!)
        interaction.createUser(userRequestModel)
    }

    @Then("the user is created successfully")
    fun check_creation() {
        verify(userRegisterDataSourceGateway, times(1)).save(any())
    }
}
