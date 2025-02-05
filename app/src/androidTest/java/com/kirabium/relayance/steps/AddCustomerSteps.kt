package com.kirabium.relayance.steps

import android.util.Log
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.kirabium.relayance.R
import com.kirabium.relayance.ui.activity.AddCustomerActivity
import com.kirabium.relayance.ui.activity.MainActivity
import com.kirabium.relayance.util.ToastMatcher
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When

class AddCustomerSteps {

    @Given("I am on the Add Customer screen")
    fun iAmOnAddCustomerScreen() {
        ActivityScenario.launch(AddCustomerActivity::class.java)
    }

    @When("I enter valid customer details")
    fun enterValidCustomerDetails(data: List<Map<String, String>>) {
        val name = data[0]["Name"]
        val email = data[0]["Email"]
        Espresso.onView(ViewMatchers.withId(R.id.nameEditText)).perform(ViewActions.typeText(name), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.emailEditText)).perform(ViewActions.typeText(email), ViewActions.closeSoftKeyboard())
    }

    @When("I click the save button")
    fun clickSaveButton() {
        Espresso.onView(ViewMatchers.withId(R.id.saveFab)).perform(ViewActions.click())
    }

    @Then("I should see a success message")
    fun shouldSeeSuccessMessage() {
        Espresso.onView(ViewMatchers.withText("Customer added successfully!")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Then("I should be navigated to the main activity")
    fun shouldNavigateToMainActivity() {
        // Here you would need to check if MainActivity is now at the top of the activity stack.
        // This might involve more complex testing with ActivityScenario or directly checking the intent.
    }
}
