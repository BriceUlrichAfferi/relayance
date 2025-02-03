package com.kirabium.relayance

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import com.kirabium.relayance.ui.activity.AddCustomerActivity
import com.kirabium.relayance.ui.activity.MainActivity
import com.kirabium.relayance.util.ToastMatcher
import io.cucumber.java.PendingException
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When

class AddCustomerTest {

    private lateinit var activityScenario: ActivityScenario<AddCustomerActivity>

    // Step definition for "Given"
    @Given("the user is on the adding page")
    fun theUserIsOnTheAddingPage() {
        activityScenario = ActivityScenario.launch(AddCustomerActivity::class.java)
        throw PendingException()
    }

    // Step definition for "When"
    @When("the user enters a name {string} and a valid email {string} and presses the save button")
    fun theUserEntersNameAndEmailAndPressesSave(name: String, email: String) {
        // Enter name
        Espresso.onView(ViewMatchers.withId(R.id.nameEditText))
            .perform(ViewActions.typeText(name), ViewActions.closeSoftKeyboard())

        // Enter email
        Espresso.onView(ViewMatchers.withId(R.id.emailEditText))
            .perform(ViewActions.typeText(email), ViewActions.closeSoftKeyboard())

        // Click save button
        Espresso.onView(ViewMatchers.withId(R.id.saveFab)).perform(ViewActions.click())
        throw PendingException()

    }

    // Step definition for "Then"
    @Then("a confirmation toast is displayed")
    fun aConfirmationToastIsDisplayed() {
        Espresso.onView(ViewMatchers.withText("Customer added successfully!")).inRoot(ToastMatcher())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        throw PendingException()
    }

    // Step definition for "Then"
    @Then("the user is redirected to the home page")
    fun theUserIsRedirectedToTheHomePage() {
        Intents.init()  // Initialize intent capturing
        intended(IntentMatchers.hasComponent(MainActivity::class.java.name))
        Intents.release()  // Release after checking
        throw PendingException()
    }
}
