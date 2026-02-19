package com.example.my_webapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.my_webapp.settings.SettingsManager
import com.example.my_webapp.viewmodel.WeatherViewModel
import kotlinx.coroutines.launch

//@Composable
//fun CitiesScreen(viewModel: WeatherViewModel = viewModel()) {
//
//    var currentCity by remember { mutableStateOf("Bangalore") }
//    val cities by viewModel.cities.collectAsState()
//    var text by remember { mutableStateOf("") }
//
//    val tempUnit by SettingsManager.temperatureUnit.collectAsState()
//    val windUnit by SettingsManager.windUnit.collectAsState()
//
//    val snackbarHostState = remember { SnackbarHostState() }
//    val scope = rememberCoroutineScope()
//
//    // Load default cities if empty
//    LaunchedEffect(Unit) {
//        viewModel.loadDefaultCities()
//        viewModel.searchCity(currentCity)
//    }
//
//    Scaffold(
//        snackbarHost = {
//            SnackbarHost(hostState = snackbarHostState) { data ->
//                Snackbar(
//                    action = {
//                        IconButton(onClick = { data.dismiss() }, modifier = Modifier.testTag("closeSnackbar")) {
//                            Icon(Icons.Default.Close, contentDescription = "Close")
//                        }
//                    }
//                ) {
//                    Text(data.visuals.message, modifier = Modifier.testTag("snackbarMessage"))
//                }
//            }
//        }
//    ) { padding ->
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(padding)
//                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(12.dp)
//        ) {
//
//            // Search Field with testTag
//            OutlinedTextField(
//                value = text,
//                onValueChange = { text = it },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .testTag("citySearchField"),
//                label = { Text("Search City") },
//                trailingIcon = {
//                    if (text.isNotEmpty()) {
//                        Icon(
//                            Icons.Default.Close,
//                            contentDescription = "Clear",
//                            modifier = Modifier
//                                .clickable { text = "" }
//                                .testTag("clearSearch")
//                        )
//                    }
//                },
//                keyboardOptions = KeyboardOptions(
//                    keyboardType = KeyboardType.Text,
//                    imeAction = ImeAction.Search
//                ),
//                singleLine = true
//            )
//
//            // Search Button with testTag
//            Button(
//                onClick = {
//                    val input = text.trim()
//                    if (input.isBlank() || input.any { it.isDigit() }) {
//                        scope.launch {
//                            snackbarHostState.showSnackbar(
//                                message = "City not found or invalid input",
//                                duration = SnackbarDuration.Indefinite
//                            )
//                        }
//                    } else {
//                        viewModel.searchCity(input) { success ->
//                            scope.launch {
//                                if (success) {
//                                    currentCity = input
//                                    snackbarHostState.showSnackbar(
//                                        message = "$input added to list",
//                                        duration = SnackbarDuration.Short
//                                    )
//                                    text = ""
//                                } else {
//                                    snackbarHostState.showSnackbar(
//                                        message = "City not found",
//                                        duration = SnackbarDuration.Indefinite
//                                    )
//                                }
//                            }
//                        }
//                    }
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .testTag("searchButton")
//            ) {
//                Text("Search")
//            }
//
//            Spacer(Modifier.height(8.dp))
//
//            // City list
//            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
//                items(cities) { weather ->
//                    Card(modifier = Modifier.fillMaxWidth()) {
//                        Column(modifier = Modifier.padding(12.dp)) {
//                            Text(
//                                weather.location.name,
//                                style = MaterialTheme.typography.titleMedium,
//                                modifier = Modifier.testTag("cityItem_${weather.location.name}")
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}

@Composable
fun CitiesScreen(viewModel: WeatherViewModel = viewModel()) {

    var currentCity by remember { mutableStateOf("Bangalore") }
    val cities by viewModel.cities.collectAsState()
    var text by remember { mutableStateOf("") }

    val tempUnit by SettingsManager.temperatureUnit.collectAsState()
    val windUnit by SettingsManager.windUnit.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.loadDefaultCities()
        viewModel.searchCity(currentCity)
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    action = {
                        IconButton(
                            onClick = { data.dismiss() },
                            modifier = Modifier.testTag("closeSnackbar")
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Close")
                        }
                    }
                ) {
                    Text(data.visuals.message, modifier = Modifier.testTag("snackbarMessage"))
                }
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // SEARCH FIELD
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("citySearchField"),
                label = { Text("Search City") },
                singleLine = true
            )

            // SEARCH BUTTON
            Button(
                onClick = {
                    val input = text.trim()

                    if (input.isBlank() || input.any { it.isDigit() }) {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                "City not found or invalid input",
//                                SnackbarDuration.Indefinite
                            )
                        }
                        return@Button
                    }

                    viewModel.searchCity(input) { success ->
                        scope.launch {
                            if (success) {
                                currentCity = input
                                snackbarHostState.showSnackbar("$input added to list")
                                text = ""
                            } else {
                                snackbarHostState.showSnackbar("City not found")
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("searchButton")
            ) {
                Text("Search")
            }

            Spacer(Modifier.height(12.dp))

            // CITY LIST WITH TEMPERATURE + WIND
            LazyColumn(modifier = Modifier.testTag("cityList"),verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(cities) { weather ->

                    val tempC = weather.main.temp
                    val tempText =
                        if (tempUnit == "F")
                            "%.1f °F".format(tempC * 9 / 5 + 32)
                        else
                            "%.1f °C".format(tempC)

                    val speedMs = weather.wind.speed
                    val windText =
                        if (windUnit == "KPH")
                            "%.1f km/h".format(speedMs * 3.6)
                        else
                            "%.1f m/s".format(speedMs)

                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(12.dp)) {

                            Text(
                                weather.name,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.testTag("cityItem_${weather.name}")
                            )

                            Spacer(Modifier.height(6.dp))

                            Text("Temperature: $tempText")
                            Text("Wind: $windText")
                        }
                    }
                }
            }
        }
    }
}
