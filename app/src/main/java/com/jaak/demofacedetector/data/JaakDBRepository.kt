package com.jaak.demofacedetector.data

import com.jaak.demofacedetector.data.model.VerifyRequest
import com.jaak.demofacedetector.data.model.VerifySuccess
import com.jaak.demofacedetector.data.network.JaakDBService
import retrofit2.Response
import javax.inject.Inject

class JaakDBRepository @Inject constructor(
    private val api: JaakDBService
) {

    suspend fun livenessVerifyApi(apiKey: String, verifyRequest: VerifyRequest): Response<VerifySuccess> {
        return api.livenessVerifyApi(apiKey, verifyRequest)
    }

}