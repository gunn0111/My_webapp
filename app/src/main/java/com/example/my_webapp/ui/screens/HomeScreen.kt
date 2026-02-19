package com.example.my_webapp.ui.screens

import androidx.compose.ui.platform.testTag
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.my_webapp.R
import com.example.my_webapp.ui.navigation.NavRoutes
import com.example.my_webapp.ui.theme.AccentButton

@Composable
fun HomeScreen(navController: NavController) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // ROUND ICON
        Surface(
            modifier = Modifier.size(120.dp),
            shape = CircleShape,
            color = Color.DarkGray
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_weather),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.padding(30.dp)
            )
        }

        Spacer(Modifier.height(40.dp))

        Text(
            text = "Welcome to",
            fontSize = 26.sp,
            color = Color.White
        )

        Text(
            text = "AccuWeather",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(Modifier.height(40.dp))

        Button(
            onClick = { navController.navigate(NavRoutes.MyCities.route) },
            colors = ButtonDefaults.buttonColors(containerColor = AccentButton),
            modifier = Modifier.testTag("getStartedButton")
                .width(220.dp)
                .height(55.dp)
        ) {
            Text("Get Started", color = Color.White, fontSize = 18.sp)
        }
    }
}
