package com.photoroom.assignment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onOpenGalleryClick: () -> Unit
) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = "Welcome 👋",
            style = MaterialTheme.typography.displaySmall
        )

        Spacer(modifier = Modifier.size(16.dp))

        Button(
            onClick = {
                onOpenGalleryClick.invoke()
            }
        ) {
            Text("Open gallery picker")
        }
    }

}

@Preview
@Composable
fun HomeScreenPreview() {
    Theme {
        HomeScreen(
            onOpenGalleryClick = {}
        )
    }
}
