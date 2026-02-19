package com.example.my_webapp.settings

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object SettingsManager {

    // null means user never selected
    private val _temperatureUnit = MutableStateFlow<String?>(null)
    val temperatureUnit: StateFlow<String?> = _temperatureUnit.asStateFlow()

    private val _windUnit = MutableStateFlow<String?>(null)
    val windUnit: StateFlow<String?> = _windUnit.asStateFlow()

    fun setTemperatureUnit(unit: String) {
        _temperatureUnit.value = unit
    }

    fun setWindUnit(unit: String) {
        _windUnit.value = unit
    }

    // defaults used by app logic
    fun effectiveTemp(unit: String?): String = unit ?: "C"
    fun effectiveWind(unit: String?): String = unit ?: "MPS"
}
