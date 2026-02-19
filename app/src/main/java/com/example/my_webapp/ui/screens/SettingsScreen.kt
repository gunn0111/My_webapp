package com.example.my_webapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.my_webapp.settings.SettingsManager
import com.example.my_webapp.ui.components.SettingBlock

@Composable
fun SettingsScreen(
    showMessage: (String) -> Unit
) {

    val tempUnit by SettingsManager.temperatureUnit.collectAsState()
    val windUnit by SettingsManager.windUnit.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        SettingBlock(
            title = "Temperature",
            option1 = "Celsius",
            option2 = "Fahrenheit",
            selected = tempUnit,
            key1 = "C",
            key2 = "F",
            onSelect = {
                SettingsManager.setTemperatureUnit(it)
                showMessage(if (it == "C") "Celsius selected" else "Fahrenheit selected")
            }
        )

        SettingBlock(
            title = "Wind Speed",
            option1 = "km/h",
            option2 = "m/s",
            selected = windUnit,
            key1 = "KPH",
            key2 = "MPS",
            onSelect = {
                SettingsManager.setWindUnit(it)
                showMessage(if (it == "KPH") "km/h selected" else "m/s selected")
            }
        )
    }
}
