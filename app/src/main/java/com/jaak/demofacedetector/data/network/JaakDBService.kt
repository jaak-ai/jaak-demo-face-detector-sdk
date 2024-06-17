package com.jaak.demofacedetector.data.network

import com.jaak.demofacedetector.data.model.ErrorModel
import com.jaak.demofacedetector.data.model.VerifyRequest
import com.jaak.demofacedetector.data.model.VerifySuccess
import com.jaak.demofacedetector.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class JaakDBService @Inject constructor(private val api: JaakDBApiClient) {
    private suspend fun <T> callService(serviceCall: suspend () -> Response<T>): Response<T> {
        try {
            val response = serviceCall.invoke()
            if (!response.isSuccessful) {
                val errorModel = ErrorModel("Error en la llamada al servicio: ${response.message()}",false, response.code())
                val errorBody = Utils.resultsModelToResponseBody(errorModel)
                return Response.error(response.code(), errorBody)
            }
            return if (response.body() != null){
                Response.success(response.code(),response.body())
            }else{
                val errorModel = ErrorModel("El cuerpo de la respuesta está vacío", false,response.code())
                val errorBody = Utils.resultsModelToResponseBody(errorModel)
                Response.error(response.code(), errorBody)
            }
        } catch (e: IOException) {
            val errorModel = ErrorModel("Error de conexión: ${e.message}", false,500)
            val errorBody = Utils.resultsModelToResponseBody(errorModel)
            return Response.error(500, errorBody)
        } catch (e: Exception) {
            val errorModel = ErrorModel("Error inesperado: ${e.message}", false,500)
            val errorBody = Utils.resultsModelToResponseBody(errorModel)
            return Response.error(500, errorBody)
        }
    }

    suspend fun livenessVerifyApi(apiKey: String, verifyRequest: VerifyRequest): Response<VerifySuccess> {
        return withContext(Dispatchers.IO) {
            callService{ api.livenessVerifyApi(apiKey, verifyRequest) }
        }
    }

}