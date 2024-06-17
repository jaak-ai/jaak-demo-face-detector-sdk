package com.jaak.demofacedetector.data.model

import com.google.gson.annotations.SerializedName

data class VerifyRequest(
    @SerializedName("video") val video: String
)