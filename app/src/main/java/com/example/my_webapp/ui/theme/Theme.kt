//package com.example.my_webapp.ui.theme
//
//import android.app.Activity
//import android.os.Build
//import androidx.compose.foundation.isSystemInDarkTheme
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.darkColorScheme
//import androidx.compose.material3.dynamicDarkColorScheme
//import androidx.compose.material3.dynamicLightColorScheme
//import androidx.compose.material3.lightColorScheme
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.graphics.Color
//
//
//private val DarkColorScheme = darkColorScheme(
//    primary = Color(0xFF8A7CFF),      // bluish-purple buttons
//    secondary = Color(0xFF03DAC5),
//    background = Color.Black,
//    surface = Color.Black,
//    onPrimary = Color.White,
//    onBackground = Color.White,
//    onSurface = Color.White
//)
//
//
//
//    /* Other default colors to override
//        surface = Color(0xFFFFFBFE),
//    onPrimary = Color.White,
//    onSecondary = Color.White,
//    onTertiary = Color.White,
//    onBackground = Color(0xFF1C1B1F),
//    onSurface = Color(0xFF1C1B1F),
//    */
//
//
//@Composable
//fun My_webappTheme(
//    content: @Composable () -> Unit
//) {
//
//    MaterialTheme(
//        colorScheme = DarkColorScheme,
//        typography = Typography,
//        content = content
//    )
//}

package com.example.my_webapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColors = darkColorScheme(
    background = Color.Black,
    surface = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    primary = Color(0xFF7B8CFF), // bluish purple
    onPrimary = Color.White
)

@Composable
fun My_webappTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColors,
        typography = Typography(),
        content = content
    )
}
