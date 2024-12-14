package com.photoroom.assignment

import android.os.Bundle
import android.provider.MediaStore
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
        registerForActivityResult(PickMultipleVisualMedia(MAX_PICKABLE_ITEMS)) { uris ->
            if (uris.isNotEmpty()) {
                // TODO verify if this blocks ui
                uris.forEach {
                    viewModel.dispatch(
                        HomeViewModel.Action.ImageSelected(
                            MediaStore.Images.Media.getBitmap(
                                this.getContentResolver(),
                                it
                            )
                        )
                    )
                }
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Theme {
                HomeScreen(
                    uiState = viewModel.uiState,
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

    companion object {
        val MAX_PICKABLE_ITEMS = 100
    }

}
