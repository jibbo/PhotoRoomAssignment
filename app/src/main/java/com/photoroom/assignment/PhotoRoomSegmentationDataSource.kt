package com.photoroom.assignment

import android.graphics.Bitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

class PhotoRoomSegmentationDataSource(
    private val segmentationService: SegmentationService
) : SegmentationDataSource {

    override suspend fun getSegmentedImage(bitmap: Bitmap): Bitmap = withContext(Dispatchers.IO) {
        val apiKey = ""
        if (apiKey.isEmpty()) throw IllegalStateException("Please add the provided API key 🙂")

        return@withContext segmentationService.getSegmentedImage(
            apiKey = apiKey,
            imageFile = bitmap.toMultipartBodyPart()
        )
    }

    private fun Bitmap.toMultipartBodyPart(
        name: String = "image_file",
        filename: String = "image.jpg"
    ): MultipartBody.Part {
        val byteArrayOutputStream = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val requestBody = byteArray.toRequestBody(
            contentType = "image/jpeg".toMediaTypeOrNull(),
            offset = 0,
            byteCount = byteArray.size
        )
        return MultipartBody.Part.createFormData(name, filename, requestBody)
    }

}
