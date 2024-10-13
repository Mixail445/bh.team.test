package com.example.common_ui.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BaseButton(text: String = "emptyText", onClick: () -> Unit, modifier: Modifier) {
    Box(modifier = Modifier) {
        Button(onClick = { onClick.invoke() }, modifier = modifier, content = { Text(text = text) })
    }
}

@Preview
@Composable
fun BaseTextField(
    onValueChange: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    placeholderText: String = "Placeholder"
) {
    var value by remember { mutableStateOf("") }

    TextField(
        value = value,
        onValueChange = { newValue ->
            value = newValue
            onValueChange(newValue)
        },
        placeholder = { Text(text = placeholderText) },
        modifier = modifier
    )
}