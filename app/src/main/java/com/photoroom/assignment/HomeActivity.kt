package com.photoroom.assignment

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickMultipleVisualMedia
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.photoroom.assignment.presentation.HomeViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: HomeViewModel = HomeViewModel(ServiceLocator.segmentImageUseCase)

    val pickMultipleMedia =
        registerForActivityResult(PickMultipleVisualMedia(5)) { uris ->
            // Callback is invoked after the user selects media items or closes the
            // photo picker.
            if (uris.isNotEmpty()) {
                Log.d("PhotoPicker", "Number of items selected: ${uris.size}")
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Theme {
                HomeScreen(
                    onOpenGalleryClick = {
                        viewModel.dispatch(HomeViewModel.Action.OpenGallery)
                    }
                )
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvents.collect {
                    when (it) {
                        HomeViewModel.UiEvent.OpenGallery -> {
                            pickMultipleMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
                        }
                    }
                }
            }
        }
    }

}
