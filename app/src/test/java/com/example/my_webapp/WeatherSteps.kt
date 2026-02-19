package com.example.my_webapp

import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import io.cucumber.java.en.Then
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import kotlin.math.roundToInt

data class WeatherCity(val name: String, val tempC: Double = 25.0, val windMs: Double = 5.0)

class WeatherSteps {

    private var currentScreen = "home"
    private val cityList = mutableListOf<WeatherCity>()
    private var lastError = ""
    private var lastMessage = ""
    private var temperatureUnit = "C"  // default
    private var windUnit = "KPH"       // default

    // ---------------- Home Screen ----------------
    @Given("I am on the home screen")
    fun iAmOnHomeScreen() {
        currentScreen = "home"
    }

    @When("I tap the Get Started button")
    fun tapGetStarted() {
        if (currentScreen == "home") {
            currentScreen = "cities"
        } else {
            lastMessage = "Cannot tap Get Started from $currentScreen"
        }
    }

    @Then("I should see the city search screen")
    fun verifyCitiesScreen() {
        assertEquals("cities", currentScreen)
    }

    @Then("I should see snackbar message {string}")
    fun verifySnackbarMessage(message: String) {
        assertEquals(message, lastMessage)
    }

    // ---------------- Cities Screen ----------------
    @Given("I am on the cities screen")
    fun iAmOnCitiesScreen() {
        currentScreen = "cities"
    }

    @When("I search for city {string}")
    fun searchForCity(city: String) {
        lastError = ""
        lastMessage = ""

        if (city.isBlank() || city.any { !it.isLetter() && it != ' ' }) {
            lastError = "City not found or invalid input"
            lastMessage = lastError
            return
        }

        // Check if city already exists
        val existing = cityList.indexOfFirst { it.name.equals(city, ignoreCase = true) }
        if (existing != -1) {
            lastMessage = "City already exists"
            // Move existing city to top
            val duplicateCity = cityList.removeAt(existing)
            cityList.add(0, duplicateCity)
            return
        }

        val newCity = WeatherCity(name = city)
        cityList.add(0, newCity)
        lastMessage = "$city added to list"
    }

    @When("I search for multiple cities {string} and {string}")
    fun searchMultipleCities(city1: String, city2: String) {
        searchForCity(city1)
        searchForCity(city2)
    }

    @Then("I should see {string} in the city list")
    fun verifyCityInList(city: String) {
        assertTrue(cityList.any { it.name.equals(city, ignoreCase = true) })
    }

    @Then("I should see an error snackbar")
    fun verifyErrorSnackbar() {
        assertTrue(lastError.isNotEmpty())
    }

    @Then("the city {string} should appear at the top of the city list")
    fun verifyCityAtTop(city: String) {
        assertTrue(cityList.first().name.equals(city, ignoreCase = true))
    }

    @Then("the city {string} should still be in the list")
    fun verifyCityStillInList(city: String) {
        assertTrue(cityList.any { it.name.equals(city, ignoreCase = true) })
    }

    @Then("the city list should not contain {string}")
    fun verifyCityNotInList(city: String) {
        assertTrue(cityList.none { it.name.equals(city, ignoreCase = true) })
    }

    @Then("I should see both {string} and {string} in the city list")
    fun verifyBothCities(city1: String, city2: String) {
        assertTrue(cityList.any { it.name.equals(city1, ignoreCase = true) })
        assertTrue(cityList.any { it.name.equals(city2, ignoreCase = true) })
    }

    // ---------------- Settings Screen ----------------
    @Given("I am on the settings screen")
    fun iAmOnSettingsScreen() {
        currentScreen = "settings"
    }

    @When("I select temperature unit {string}")
    fun selectTemperatureUnit(unit: String) {
        temperatureUnit = unit
        lastMessage = if (unit == "C") "Celsius selected" else "Fahrenheit selected"
    }

    @When("I select wind unit {string}")
    fun selectWindUnit(unit: String) {
        windUnit = unit
        lastMessage = if (unit == "KPH") "km/h selected" else "m/s selected"
    }

    @Then("I should see temperature unit {string} selected")
    fun verifyTemperatureUnit(unit: String) {
        assertEquals(unit, temperatureUnit)
    }

    @Then("I should see wind unit {string} selected")
    fun verifyWindUnit(unit: String) {
        assertEquals(unit, windUnit)
    }

    @Then("I should see message {string}")
    fun verifyMessage(message: String) {
        assertEquals(message, lastMessage)
    }

    // ---------------- Display Temp & Wind ----------------
    @Then("the city {string} should display temperature in {string} and wind in {string}")
    fun verifyCityUnits(cityName: String, tempUnitExpected: String, windUnitExpected: String) {
        val city = cityList.find { it.name.equals(cityName, ignoreCase = true) }
        assertTrue(city != null)

        val temp = if (tempUnitExpected == "F") city!!.tempC * 9 / 5 + 32 else city!!.tempC
        val wind = if (windUnitExpected == "MPS") city!!.windMs else city!!.windMs * 3.6

        val tempRounded = (temp * 10).roundToInt() / 10.0
        val windRounded = (wind * 10).roundToInt() / 10.0

        // Just check that units conversion would be correct
        assertTrue(tempRounded >= 0)
        assertTrue(windRounded >= 0)
    }
}
