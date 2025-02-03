package com.kirabium.relayance

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.extension.DateExt.Companion.toHumanDate
import com.kirabium.relayance.ui.activity.DetailActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Calendar

@RunWith(AndroidJUnit4::class)
class DetailScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<DetailActivity>()

    @Test
    fun detailScreen_displaysCorrectCustomerInformation() {
        // Given a customer
        val testCustomer = Customer(
            id = 1,
            name = "Alice Wonderland",
            email = "alice@example.com",
            createdAt = Calendar.getInstance().apply { add(Calendar.MONTH, -12) }.time
        )

        // Setup the intent with customer id before activity is created
        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.intent.putExtra(DetailActivity.EXTRA_CUSTOMER_ID, 1)
        }

        // Wait for the ViewModel to load the customer (simulating the actual app behavior)
        composeTestRule.waitForIdle()

        // Then check if the correct information is displayed using semantics assertions

        // Check for name
        composeTestRule.onNode(hasText(testCustomer.name)).assertIsDisplayed()

        // Check for email
        composeTestRule.onNode(hasText(testCustomer.email)).assertIsDisplayed()

        // Check for formatted date
        val formattedDate = "Created at: ${testCustomer.createdAt.toHumanDate()}"
        composeTestRule.onNode(hasText(formattedDate)).assertIsDisplayed()

        // Check for 'New' ribbon if the customer is new
        if (testCustomer.isNewCustomer()) {
            composeTestRule.onNode(hasText("New")).assertIsDisplayed() // Assuming "New" is your string for new_ribbon
        } else {
            composeTestRule.onNode(hasText("New")).assertDoesNotExist()
        }
    }
}