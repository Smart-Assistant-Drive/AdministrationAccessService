package com.example.restHateoas.businessLayer

import com.example.restHateoas.businessLayer.adapter.UserRequestModel
import com.example.restHateoas.businessLayer.adapter.UserResponseModel
import com.example.restHateoas.businessLayer.boundaries.UserRegisterDataSourceGateway
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.*


@RunWith(Cucumber::class)
@CucumberOptions(
    plugin = ["pretty", "html:target/cucumber-report.html"],
    features = ["src/test/resources"]
)
class CreateUserStep() {

    private var name: String? = null
    private var password: String? = null
    private val interaction: UserRegisterUseCase
    private val userRegisterDataSourceGateway = mock<UserRegisterDataSourceGateway> {
        on { save(any()) } doAnswer {}
        on { existsByName(anyString()) } doReturn false
    }


    init {
        interaction = UserRegisterUseCase(
            userRegisterDataSourceGateway,
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

    @When("the user is created")
    @Throws(Throwable::class)
    fun password_validated() {
        val userRequestModel = UserRequestModel(name!!, password!!)
        interaction.createUser(userRequestModel)
    }

    @Then("the user is created successfully")
    fun check_creation() {
        verify(userRegisterDataSourceGateway, times(1)).save(any())
    }
}