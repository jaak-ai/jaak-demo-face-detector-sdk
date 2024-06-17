package com.jaak.demofacedetector.ui.view

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.jaak.demofacedetector.R
import com.jaak.demofacedetector.data.model.VerifyRequest
import com.jaak.demofacedetector.databinding.ActivitySuccessLivenessBinding
import com.jaak.demofacedetector.ui.viewmodel.ValidationBase64Model
import com.jaak.demofacedetector.utils.Utils
import com.jaak.jaakfacedetectorsdk.utils.Constants
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SuccessLivenessActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuccessLivenessBinding
    private val validationBase64ViewModel: ValidationBase64Model by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuccessLivenessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        initComponents()
        validationBase64ViewModel.isLoading.value = true
    }

    private fun initComponents(){
        binding.tvBtnFinish.setOnClickListener{
            finish()
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        })
        val uriRes = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constants.URI_FACES, Uri::class.java)
        } else {
            intent.getParcelableExtra(Constants.URI_FACES) as? Uri
        }
        val typeProcess = intent.extras?.getInt(Constants.TYPE_PROCCESS_BASE64, 0)!!
        proccessBase64(typeProcess, uriRes!!)
    }

    private fun proccessBase64(typeProcessBase64 : Int,uri : Uri){
        var base64Proccess = ""
        when (typeProcessBase64) {
            1 -> {
                base64Proccess = Utils.uriToBase64(contentResolver, uri)!!
            }
            2 -> {
                base64Proccess = Utils.videoCameraUriToBase64(uri)!!
            }
            3 -> {
                base64Proccess = Utils.uriToBase64(contentResolver, uri)!!
            }
            4 -> {
                base64Proccess = Utils.videoFileUriToBase64(contentResolver, uri)!!
            }
            else -> {
                Toast.makeText(this,getString(R.string.error_base64_empty), Toast.LENGTH_SHORT).show()
            }
        }
        val verifyRequest = VerifyRequest(base64Proccess)
        validationBase64ViewModel.livenessVerify(verifyRequest)
    }

    private fun initViewModel(){
        validationBase64ViewModel.verifySuccess.observe(this){
            if(it.score!! > 0.7){
                binding.tvCongratulation.text = getString(R.string.congratulations)
                binding.tvDescription.text = getString(R.string.content_success_liveness)
            }else{
                binding.ivContent.setImageResource(R.drawable.warning_error)
                binding.tvCongratulation.text = getString(R.string.sorry_error)
                binding.tvDescription.text = getString(R.string.error_human)

            }
        }
        validationBase64ViewModel.errorModel.observe(this){
            binding.ivContent.setImageResource(R.drawable.warning_error)
            binding.tvCongratulation.text = getString(R.string.sorry_error)
            binding.tvDescription.text = it.message
        }
        validationBase64ViewModel.isLoading.observe(this) {
            if(it){
                binding.clProgress.visibility = View.VISIBLE
                binding.clSuccess.visibility = View.GONE
            }else{
                binding.clProgress.visibility = View.GONE
                binding.clSuccess.visibility = View.VISIBLE
            }
        }
    }
}