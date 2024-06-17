package com.jaak.demofacedetector.data.model

import com.google.gson.annotations.SerializedName

data class ImageStatus(
    @SerializedName("original") val original: String?,
    @SerializedName("thumbnail") val thumbnail: String?
)