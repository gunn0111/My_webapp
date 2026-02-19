package com.example.my_webapp

import io.cucumber.java.Before
import io.cucumber.java.After

class TestHooks {

    @Before
    fun beforeScenario() {
        println("Starting scenario...")
    }

    @After
    fun afterScenario() {
        println("Scenario finished.")
    }
}
