package com.example.my_webapp.viewmodel

import com.example.my_webapp.data.model.Main
import com.example.my_webapp.data.model.Wind
import com.example.my_webapp.data.model.WeatherResponse
import com.example.my_webapp.data.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repository: WeatherRepository
    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock()
        viewModel = WeatherViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // Helper function to create fake weather data
    private fun fakeWeather(name: String) = WeatherResponse(
        name = name,
        main = Main(temp = 25.0),
        wind = Wind(speed = 10.0)
    )

    @Test
    fun `searchCity success updates cities list`() = runTest {

        val fakeResponse = fakeWeather("London")

        whenever(repository.getWeather("London"))
            .thenReturn(fakeResponse)

        var callbackResult = false

        viewModel.searchCity("London") {
            callbackResult = it
        }

        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(callbackResult)
        assertEquals(1, viewModel.cities.value.size)
        assertEquals("London", viewModel.cities.value[0].name)

        verify(repository).getWeather("London")
    }

    @Test
    fun `searchCity failure triggers false callback`() = runTest {

        whenever(repository.getWeather("Paris"))
            .thenThrow(RuntimeException("API Error"))

        var callbackResult = true

        viewModel.searchCity("Paris") {
            callbackResult = it
        }

        testDispatcher.scheduler.advanceUntilIdle()

        assertFalse(callbackResult)
        assertTrue(viewModel.cities.value.isEmpty())
    }

    @Test
    fun `loadDefaultCities loads multiple cities`() = runTest {

        val city1 = fakeWeather("Rohtak")
        val city2 = fakeWeather("Moga")

        whenever(repository.getWeather("Rohtak")).thenReturn(city1)
        whenever(repository.getWeather("Moga")).thenReturn(city2)

        viewModel.loadDefaultCities(listOf("Rohtak", "Moga"))

        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(2, viewModel.cities.value.size)

        verify(repository).getWeather("Rohtak")
        verify(repository).getWeather("Moga")
    }

    @Test
    fun `searchCity replaces existing city instead of duplicating`() = runTest {

        val weather = fakeWeather("London")

        whenever(repository.getWeather("London"))
            .thenReturn(weather)

        // First call
        viewModel.searchCity("London") {}
        testDispatcher.scheduler.advanceUntilIdle()

        // Second call
        viewModel.searchCity("London") {}
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(1, viewModel.cities.value.size)
    }


    @Test
    fun `loadDefaultCities does not reload if already filled`() = runTest {

        whenever(repository.getWeather(any()))
            .thenReturn(fakeWeather("Delhi"))

        viewModel.loadDefaultCities(listOf("Delhi"))
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.loadDefaultCities(listOf("Delhi"))
        testDispatcher.scheduler.advanceUntilIdle()

        verify(repository, times(1)).getWeather("Delhi")
    }

    @Test
    fun `multiple cities added correctly`() = runTest {

        whenever(repository.getWeather(any()))
            .thenAnswer {
                fakeWeather(it.arguments[0] as String)
            }

        val cities = listOf("Delhi", "Mumbai", "London")

        cities.forEach {
            viewModel.searchCity(it) {}
        }

        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(3, viewModel.cities.value.size)
    }

}
