package com.jaak.demofacedetector.ui.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.jaak.demofacedetector.databinding.ActivityMenuMainBinding
import com.jaak.jaakfacedetectorsdk.ui.adapter.FaceDetectorListener
import com.jaak.jaakfacedetectorsdk.ui.view.FaceDetectorSDK
import com.jaak.jaakfacedetectorsdk.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

/**
 * Clase principal que contiene la lógica de los fragments.
 * Esta actividad es la pantalla principal de la aplicación y gestiona la navegación y
 * la interacción del usuario con los fragmentos.
 */
@AndroidEntryPoint
class MenuMainActivity : AppCompatActivity(), FaceDetectorListener {

    private lateinit var binding: ActivityMenuMainBinding
    private lateinit var faceDetectorSDK: FaceDetectorSDK


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMenuMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initComponents()
        configFaceDetectorSDK()
    }
    /**
     * Inicializa los componentes de la actividad.
     * Configura el comportamiento de los botones y registra el lanzador de resultados
     * para obtener datos de otras actividades.
     */
    private fun initComponents(){
        binding.ivBtnCamara.setOnClickListener{
            faceDetectorSDK.startFaceDetector(2)
        }
        binding.ivBtnFileVideo.setOnClickListener{
            faceDetectorSDK.startFaceDetector(4)
        }
    }
    /**
     * Configura el SDK de Rostros con las opciones habilitadas.
     */
    private fun configFaceDetectorSDK(){
        faceDetectorSDK = FaceDetectorSDK(this,this)
        faceDetectorSDK.setEnableCamera(true)
        faceDetectorSDK.setEnableDisk(false)
        faceDetectorSDK.setEnableCameraPhoto(false)
        faceDetectorSDK.setEnableCameraVideo(true)
        faceDetectorSDK.setEnableDiskPhoto(false)
        faceDetectorSDK.setEnableDiskVideo(false)
        faceDetectorSDK.setEnableDiskImageVideo(false)
        faceDetectorSDK.setFacesOrDocuments(true)
        faceDetectorSDK.setEnableModelFaces(true)
        faceDetectorSDK.setImageFormat("image/*")
        faceDetectorSDK.setVideoFormat("video/*")
        faceDetectorSDK.setImageSize(3)
        faceDetectorSDK.setVideoSize(10)
    }

    /**
     * Método de devolución de llamada cuando el proceso de Rostros tiene éxito.
     * @param typeProcess El tipo de proceso de Rostros.
     * @param uri La URI del archivo o recurso procesado.
     */
    override fun onSuccessFaceDetector(typeProcess: Int, uri: Uri?) {
        val resultIntent = Intent(this, SuccessLivenessActivity::class.java)
        resultIntent.putExtra(Constants.URI_FACES,uri)
        resultIntent.putExtra(Constants.TYPE_PROCCESS_BASE64,typeProcess)
        startActivity(resultIntent)
    }
    /**
     * Método de devolución de llamada cuando se produce un error en el proceso de Rostros.
     * @param text El texto del mensaje de error.
     */
    override fun onErrorFaceDetector(text: String) {
        Toast.makeText(this,text, Toast.LENGTH_SHORT).show()
    }
}