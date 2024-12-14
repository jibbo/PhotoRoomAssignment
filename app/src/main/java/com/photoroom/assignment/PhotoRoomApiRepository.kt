package com.photoroom.assignment.data

import android.graphics.Bitmap
import com.photoroom.assignment.PhotoRoomSegmentationDataSource
import com.photoroom.assignment.SegmentationDataSource
import com.photoroom.assignment.domain.PhotoroomRepository

class PhotoRoomApiRepository constructor(
    private val segmentationDataSource: SegmentationDataSource
) : PhotoroomRepository {
    override suspend fun getSegmentedImage(bitmap: Bitmap): Bitmap =
        segmentationDataSource.getSegmentedImage(bitmap)
}
