package com.kirabium.relayance

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.kirabium.relayance.ui.activity.AddCustomerActivity
import com.kirabium.relayance.util.ToastMatcher
import io.cucumber.junit.CucumberOptions
import io.cucumber.junit.Cucumber
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
@CucumberOptions(
    features = ["classpath:features"],  // Path to your .feature files
    glue = ["com.kirabium.relayance.steps"],  // Path to your step definition files
    plugin = ["pretty"]
)
class AddCustomerActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(AddCustomerActivity::class.java)

    @Test
    fun runCucumberTests() {
        // No need to manually write Espresso actions here. For, they are handled by the Cucumber steps
    }
}
