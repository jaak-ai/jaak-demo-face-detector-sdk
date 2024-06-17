package com.jaak.demofacedetector.domain

import com.jaak.demofacedetector.data.JaakDBRepository
import com.jaak.demofacedetector.data.model.VerifyRequest
import com.jaak.demofacedetector.data.model.VerifySuccess
import retrofit2.Response
import javax.inject.Inject

class LivenessVerifyUseCase @Inject constructor(private val repository: JaakDBRepository) {
    suspend operator fun invoke(apiKey: String, verifyRequest: VerifyRequest): Response<VerifySuccess> {
        return repository.livenessVerifyApi(apiKey, verifyRequest)
    }
}