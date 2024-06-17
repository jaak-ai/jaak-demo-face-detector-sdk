package com.jaak.demofacedetector.data.network

import com.jaak.demofacedetector.data.model.VerifyRequest
import com.jaak.demofacedetector.data.model.VerifySuccess
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface JaakDBApiClient {

    @POST("url-example")
    suspend fun livenessVerifyApi(@Header("Authorization") auth: String,
                                  @Body request: VerifyRequest): Response<VerifySuccess>

}