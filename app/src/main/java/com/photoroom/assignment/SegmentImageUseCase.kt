package com.photoroom.assignment.domain

import android.graphics.Bitmap

class SegmentImageUseCase(
    private val repository: PhotoroomRepository
) {
    suspend operator fun invoke(bitmap: Bitmap): Bitmap = repository.getSegmentedImage(bitmap)
}
