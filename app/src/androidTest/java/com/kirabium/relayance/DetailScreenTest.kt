package com.kirabium.relayance

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.extension.DateExt.Companion.toHumanDate
import com.kirabium.relayance.ui.activity.DetailActivity
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.fail
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
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
        runBlocking {
            // Given a customer
            val testCustomer = Customer(
                id = 1,
                name = "Alice Wonderland",
                email = "alice@example.com",
                createdAt = Calendar.getInstance().apply { add(Calendar.MONTH, -12) }.time
            )

            // Directly access the activity, but check if it's valid
            val activity = composeTestRule.activity
            if (!activity.isFinishing && !activity.isDestroyed) {
                // Set up intent directly on the activity
                activity.intent.putExtra(DetailActivity.EXTRA_CUSTOMER_ID, testCustomer.id)
                activity.detailsViewModel.loadCustomer(testCustomer.id)

                // Wait for the customer data to be loaded
                composeTestRule.waitForIdle()

                // Collect the first emission from the flow
                val loadedCustomer = activity.detailsViewModel.customer.first()
                assertNotNull(loadedCustomer.toString(), "Customer should be loaded by now")

                // Now proceed with your assertions
                // Check for name
                if (loadedCustomer != null) {
                    composeTestRule.onNode(hasText(loadedCustomer.name)).assertIsDisplayed()
                }

                // Check for email
                if (loadedCustomer != null) {
                    composeTestRule.onNode(hasText(loadedCustomer.email)).assertIsDisplayed()
                }

                // Check for formatted date
                val formattedDate = "Created at: ${loadedCustomer?.createdAt?.toHumanDate()}"
                composeTestRule.onNode(hasText(formattedDate)).assertIsDisplayed()

                // Check for 'New' ribbon if the customer is new
                if (loadedCustomer?.isNewCustomer() == true) {
                    composeTestRule.onNode(hasText("New")).assertIsDisplayed()
                } else {
                    composeTestRule.onNode(hasText("New")).assertDoesNotExist()
                }
            } else {
                fail("Activity was destroyed before test could complete")
            }
        }
    }
}