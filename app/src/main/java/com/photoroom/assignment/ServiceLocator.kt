package com.photoroom.assignment

import com.photoroom.assignment.data.PhotoRoomApiRepository
import com.photoroom.assignment.domain.PhotoroomRepository
import com.photoroom.assignment.domain.SegmentImageUseCase

// TODO replace by hilt
object ServiceLocator {
    val segmentationService: SegmentationService = SegmentationClient.service
    val segmentationDataSource: SegmentationDataSource =
        PhotoRoomSegmentationDataSource(segmentationService)
    val repository: PhotoroomRepository = PhotoRoomApiRepository(segmentationDataSource)
    val segmentImageUseCase = SegmentImageUseCase(repository)
}
