package com.jaak.demofacedetector.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaak.demofacedetector.data.model.ErrorModel
import com.jaak.demofacedetector.data.model.VerifyRequest
import com.jaak.demofacedetector.data.model.VerifySuccess
import com.jaak.demofacedetector.domain.LivenessVerifyUseCase
import com.jaak.demofacedetector.utils.Constants
import com.jaak.demofacedetector.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
/**
 * Viewmodel de episodios
 */
@HiltViewModel
class ValidationBase64Model @Inject constructor(
    private val livenessVerifyUseCase: LivenessVerifyUseCase) : ViewModel() {

    val verifySuccess = MutableLiveData<VerifySuccess>()
    val errorModel = MutableLiveData<ErrorModel>()
    val isLoading = MutableLiveData<Boolean>()
    /**
     * metodo para llamar al servicio de obtemer episodios
     */
    fun livenessVerify(request: VerifyRequest) {

        viewModelScope.launch {
            isLoading.postValue(true)
            val livenessVerify = livenessVerifyUseCase(Constants.TOKEN, request)
            if(livenessVerify.code() != 200){
                errorModel.value = Utils.responseBodyToResultsModel(livenessVerify.errorBody()!!)
            }else{
                verifySuccess.value = livenessVerify.body()
            }
            isLoading.postValue(false)
        }
    }
}