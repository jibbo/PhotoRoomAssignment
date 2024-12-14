package com.photoroom.assignment.data

import android.graphics.Bitmap
import com.photoroom.assignment.domain.PhotoroomRepository

class PhotoRoomApiRepository constructor(
    private val segmentationDataSource: PhotoRoomSegmentationDataSource
) : PhotoroomRepository {
    override suspend fun getSegmentedImage(bitmap: Bitmap): Bitmap =
        segmentationDataSource.getSegmentedImage(bitmap)
}
