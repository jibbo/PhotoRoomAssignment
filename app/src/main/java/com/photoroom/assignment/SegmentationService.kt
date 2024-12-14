package com.photoroom.assignment

import android.graphics.Bitmap
import okhttp3.MultipartBody
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SegmentationService {

    @Multipart
    @POST("/v1/segment")
    suspend fun getSegmentedImage(
        @Header("x-api-key")
        apiKey: String,
        @Part
        imageFile: MultipartBody.Part,
    ): Bitmap

}
