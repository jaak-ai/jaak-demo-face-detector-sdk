package com.jaak.demofacedetector.data.model

import com.google.gson.annotations.SerializedName

data class VerifySuccess(
    @SerializedName("eventId") val eventId: String?,
    @SerializedName("processTime") val processTime: String?,
    @SerializedName("user") val user: VerifyUserStatus?,
    @SerializedName("bestFrame") val bestFrame: String?,
    @SerializedName("score") val score: Double?
)