package com.kirabium.relayance.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.kirabium.relayance.R
import com.kirabium.relayance.ui.activity.AddCustomerActivity
import com.kirabium.relayance.util.ToastMatcher
import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When

import org.junit.Assert.assertTrue

/*class AddCustomerActivitySteps {

    @Given("the user is on the AddCustomerActivity screen")
    fun givenTheUserIsOnTheAddCustomerScreen() {
        // No specific action needed since the rule opens the Activity for us
    }

    @When("the user enters a valid name and email")
    fun whenTheUserEntersAValidNameAndEmail() {
        // Enter a valid name
        onView(withId(R.id.nameEditText))
            .perform(typeText("John Doe"), closeSoftKeyboard())

        // Enter a valid email
        onView(withId(R.id.emailEditText))
            .perform(typeText("john@example.com"), closeSoftKeyboard())
    }

    @And("clicks on the save button")
    fun andClicksOnTheSaveButton() {
        onView(withId(R.id.saveFab)).perform(click())
    }

    @Then("a toast message should appear with the text {string}")
    fun thenToastMessageShouldAppear(text: String) {
        onView(withText(text))
            .inRoot(ToastMatcher())  // Custom Toast Matcher to ensure correct root
            .check(matches(isDisplayed()))
    }

    @And("the user should be navigated to the main screen")
    fun andUserShouldBeNavigatedToMainScreen() {
        onView(withId(R.id.addCustomerFab)).check(matches(isDisplayed()))
    }
}*/
