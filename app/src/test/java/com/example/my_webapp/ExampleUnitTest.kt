package com.example.my_webapp

import com.example.my_webapp.settings.SettingsManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


// Unit tests for SettingsManager (StateFlow-based). These are pure JVM tests and do not use Android APIs.
class ExampleUnitTest {

    @Before
    fun setup() {
        // Reset SettingsManager internal MutableStateFlow values to null via reflection so tests are isolated.
        try {
            val cls = SettingsManager::class.java
            val tempField = cls.getDeclaredField("_temperatureUnit")
            tempField.isAccessible = true
            val tempFlow = tempField.get(null) as MutableStateFlow<String?>
            tempFlow.value = null

            val windField = cls.getDeclaredField("_windUnit")
            windField.isAccessible = true
            val windFlow = windField.get(null) as MutableStateFlow<String?>
            windFlow.value = null
        } catch (e: Exception) {
            // If reflection fails, let tests proceed — they may still be valid in a fresh JVM.
            e.printStackTrace()
        }
    }

    @Test
    fun defaultValues_areNull() = runBlocking {
        val temp = SettingsManager.temperatureUnit.first()
        val wind = SettingsManager.windUnit.first()

        assertNull("Default temperatureUnit should be null", temp)
        assertNull("Default windUnit should be null", wind)
    }

    @Test
    fun setTemperatureUnit_toC_updatesTemperatureUnit() = runBlocking {
        SettingsManager.setTemperatureUnit("C")
        val temp = SettingsManager.temperatureUnit.first()
        assertEquals("C", temp)
    }

    @Test
    fun setTemperatureUnit_toF_updatesTemperatureUnit() = runBlocking {
        SettingsManager.setTemperatureUnit("F")
        val temp = SettingsManager.temperatureUnit.first()
        assertEquals("F", temp)
    }

    @Test
    fun setWindUnit_toKPH_updatesWindUnit() = runBlocking {
        SettingsManager.setWindUnit("KPH")
        val wind = SettingsManager.windUnit.first()
        assertEquals("KPH", wind)
    }

    @Test
    fun setWindUnit_toMPS_updatesWindUnit() = runBlocking {
        SettingsManager.setWindUnit("MPS")
        val wind = SettingsManager.windUnit.first()
        assertEquals("MPS", wind)
    }
}
