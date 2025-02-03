package com.kirabium.relayance

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["classpath:features"], // Assuming features are in resources
    glue = ["com.kirabium.relayance"], // Where your step definitions are
    plugin = ["pretty"]
)
class CucumberTestRunner