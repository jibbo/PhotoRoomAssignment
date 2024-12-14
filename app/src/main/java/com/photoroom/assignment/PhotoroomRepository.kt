package com.photoroom.assignment.domain

import android.graphics.Bitmap

interface PhotoroomRepository {
    suspend fun getSegmentedImage(bitmap: Bitmap): Bitmap
}
