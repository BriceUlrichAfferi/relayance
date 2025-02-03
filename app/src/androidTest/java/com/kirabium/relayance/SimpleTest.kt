package com.kirabium.relayance

import io.cucumber.java.en.Given

class SimpleTest {

    @Given("I have a simple test case")
    fun iHaveASimpleTestCase() {
        println("Simple test case executed")
    }
}