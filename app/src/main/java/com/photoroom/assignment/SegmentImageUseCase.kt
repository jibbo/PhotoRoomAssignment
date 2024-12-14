package com.photoroom.assignment.domain

import android.graphics.Bitmap

class SegmentImageUseCase constructor(
    private val repository: PhotoroomRepository
) {
    suspend operator fun invoke(bitmap: Bitmap): Bitmap = repository.getSegmentedImage(bitmap)
}
