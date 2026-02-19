package com.example.my_webapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SettingBlock(
    title: String,
    option1: String,
    option2: String,
    selected: String?,      // <-- nullable now
    key1: String,
    key2: String,
    onSelect: (String) -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface // darker background
        )
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text(title, style = MaterialTheme.typography.titleLarge)

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                OptionButton(
                    text = option1,
                    selected = selected == key1,
                    modifier = Modifier.weight(1f),
                    onClick = { onSelect(key1) }
                )

                OptionButton(
                    text = option2,
                    selected = selected == key2,
                    modifier = Modifier.weight(1f),
                    onClick = { onSelect(key2) }
                )
            }
        }
    }
}

@Composable
fun OptionButton(
    text: String,
    selected: Boolean,
    modifier: Modifier,
    onClick: () -> Unit
) {

    val selectedColor = MaterialTheme.colorScheme.primary
    val unselectedColor = Color(0xFF2A2A2A) // custom grey (different from card)

    Box(
        modifier = modifier
            .height(60.dp)
            .background(
                color = if (selected) selectedColor else unselectedColor,
                shape = MaterialTheme.shapes.medium
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (selected)
                MaterialTheme.colorScheme.onPrimary
            else
                MaterialTheme.colorScheme.onSurface
        )
    }
}
