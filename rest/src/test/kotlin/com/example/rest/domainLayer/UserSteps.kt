package com.example.rest.domainLayer

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith


@RunWith(Cucumber::class)
@CucumberOptions(
    plugin = ["pretty", "html:target/cucumber-report.html"],
    features = ["src/test/resources"]
)
class UserSteps {

    private var user: User? = null
    private var validation: Boolean? = null

    @Given("the password is {string}")
    fun the_user_inserts_a_password(password: String) {
        user = User.create("user", password)
    }

    @When("the password is validated")
    @Throws(Throwable::class)
    fun password_validated() {
        validation = user!!.passwordIsValid()
    }

    @Then("the password is valid")
    fun password_is_valid() {
        assert(validation!!)
    }

    @Then("^the password is not valid")
    fun password_is_invalid() {
        assert(!validation!!)
    }

}