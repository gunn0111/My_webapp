package com.example.my_webapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_webapp.data.model.WeatherResponse
import com.example.my_webapp.data.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: WeatherRepository = WeatherRepository()
) : ViewModel() {

    private val _cities = MutableStateFlow<List<WeatherResponse>>(emptyList())
    val cities: StateFlow<List<WeatherResponse>> = _cities

    fun searchCity(city: String, onResult: (Boolean) -> Unit = {}) {
        viewModelScope.launch {
            try {
                val result = repository.getWeather(city)

                _cities.value = listOf(result) +
                        _cities.value.filter { it.name.lowercase() != city.lowercase() }

                onResult(true)

            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    fun loadDefaultCities(
        defaultCities: List<String> = listOf("Rohtak", "Moga", "Chandigarh", "Rajpura", "Gurgaon")
    ) {
        viewModelScope.launch {

            if (_cities.value.isNotEmpty()) return@launch

            val loadedCities = mutableListOf<WeatherResponse>()

            for (city in defaultCities) {
                try {
                    val result = repository.getWeather(city)
                    loadedCities.add(result)
                } catch (_: Exception) { }
            }

            _cities.value = loadedCities
        }
    }
}
