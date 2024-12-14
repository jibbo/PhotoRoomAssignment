package com.photoroom.assignment

import android.graphics.Bitmap

interface SegmentationDataSource {
    suspend fun getSegmentedImage(bitmap: Bitmap): Bitmap
}
