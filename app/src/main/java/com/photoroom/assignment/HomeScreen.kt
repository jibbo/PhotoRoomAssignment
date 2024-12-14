package com.photoroom.assignment

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun HomeScreen(
    uiState: SnapshotStateList<Pair<Bitmap, Bitmap?>>,
    onOpenGalleryClick: () -> Unit
) {
    val bitmaps = remember { uiState }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        item {
            Row {
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

        items(bitmaps.size) {
            Spacer(modifier = Modifier.size(8.dp))
            ImageRow(
                left = bitmaps[it].first,
                right = bitmaps[it].second
            )
        }
    }

}

@Composable
fun ImageRow(left: Bitmap, right: Bitmap? = null) {
    Row {
        Image(
            bitmap = left.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        if (right != null) {
            Image(
                bitmap = right.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        } else {
            CircularProgressIndicator(
                modifier = Modifier.width(100.dp),
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    Theme {
        HomeScreen(
            uiState = mutableStateListOf<Pair<Bitmap, Bitmap?>>(),
            onOpenGalleryClick = {}
        )
    }
}
