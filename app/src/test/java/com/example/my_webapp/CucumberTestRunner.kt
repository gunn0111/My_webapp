package com.example.my_webapp

import org.junit.runner.RunWith
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/test/resources/features"],
    glue = ["com.example.my_webapp"],
    plugin = ["pretty"]
)
class CucumberTestRunner
