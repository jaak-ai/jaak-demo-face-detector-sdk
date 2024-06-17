package com.jaak.demofacedetector.data.model

import com.google.gson.annotations.SerializedName

data class VerifyUserStatus(
    @SerializedName("companies") val companies: List<String>?,
    @SerializedName("createdAt") val createdAt: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("externalId") val externalId: String?,
    @SerializedName("fullName") val fullName: String?,
    @SerializedName("id") val id: String?,
    @SerializedName("updatedAt") val updatedAt: String?,
    @SerializedName("images") val images: ImageStatus?
)
