package com.navi.dogbreedapp.main

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.navi.dogbreedapp.LABEL_PATH
import com.navi.dogbreedapp.MODEL_PATH
import com.navi.dogbreedapp.R
import com.navi.dogbreedapp.api.responses.ApiResponseStatus
import com.navi.dogbreedapp.databinding.ActivityMainBinding
import com.navi.dogbreedapp.dogdetail.DogDetailActivity
import com.navi.dogbreedapp.dogdetail.DogDetailActivity.Companion.DOG_KEY
import com.navi.dogbreedapp.doglist.DogListActivity
import com.navi.dogbreedapp.machinelearning.Classifier
import com.navi.dogbreedapp.machinelearning.DogRecognition
import com.navi.dogbreedapp.utils.Utils
import org.tensorflow.lite.support.common.FileUtil
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var imageCapture: ImageCapture
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var classifier: Classifier
    private var isCameraReady = false
    private val mainViewModel: MainViewModel by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                setupImageCapture()
            } else Toast.makeText(
                this,
                getString(R.string.you_need_camera_permission),
                Toast.LENGTH_SHORT
            ).show()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeLiveData()
        manageClicks()
        requestCameraPermission()
    }

    override fun onStart() {
        super.onStart()
        classifier = Classifier(
            FileUtil.loadMappedFile(this@MainActivity, MODEL_PATH),
            FileUtil.loadLabels(this@MainActivity, LABEL_PATH)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::cameraExecutor.isInitialized) cameraExecutor.shutdown()
    }

    private fun observeLiveData() {
        mainViewModel.status.observe(this) { status ->
            when (status) {
                is ApiResponseStatus.Error -> {
                    binding.mainLoader.visibility = View.GONE
                    Toast.makeText(this, status.messageId, Toast.LENGTH_SHORT).show()
                }
                is ApiResponseStatus.Loading -> binding.mainLoader.visibility = View.VISIBLE
                is ApiResponseStatus.Success -> binding.mainLoader.visibility = View.GONE
            }
        }

        mainViewModel.dog.observe(this) { dog ->
            if (dog != null) {
                val extra = Bundle()
                extra.putParcelable(DOG_KEY, dog)
                goToActivity(DogDetailActivity(), extra)
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture =
            ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(
            {
                // Used to bind the lifecycle of cameras to the lifecycle owner
                val cameraProvider = cameraProviderFuture.get()
                //preview
                val preview = Preview.Builder().build()
                preview.setSurfaceProvider(binding.cameraPreview.surfaceProvider)

                // Select back camera as a default
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                val imageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                imageAnalysis.setAnalyzer(cameraExecutor) { imageProxy ->
                    val bitmapPhoto = Utils.convertImageProxyToBitmap(imageProxy)
                    if (bitmapPhoto != null) {
                        val dogRecognized = classifier.recognizeImage(bitmapPhoto).first()
                        enableTakePhotoButton(dogRecognized)
                    }
                    imageProxy.close()
                }

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture, imageAnalysis)
            }, ContextCompat.getMainExecutor(this)
        )
    }

    private fun enableTakePhotoButton(dogRecognized: DogRecognition) {
        binding.apply {
            if (dogRecognized.confidence > 60.0) {
                cameraButton.alpha = 1f
                cameraButton.setOnClickListener {
                    mainViewModel.getDogByMLId(dogRecognized.id)
                }
            } else {
                cameraButton.alpha = 0.2f
                cameraButton.setOnClickListener(null)
            }
        }
    }

    private fun setupImageCapture() {
        binding.cameraPreview.post {
            imageCapture = ImageCapture.Builder()
                .setTargetRotation(binding.cameraPreview.display.rotation)
                .build()
            cameraExecutor = Executors.newSingleThreadExecutor()
            startCamera()
            isCameraReady = true
        }
    }

    private fun manageClicks() {
        binding.apply {
            dogListButton.setOnClickListener {
                goToActivity(DogListActivity())
            }
        }
    }

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                setupImageCapture()
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA) -> {
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.title_dialog_camera_permission))
                    .setMessage(getString(R.string.message_dialog_camera_permission))
                    .setPositiveButton(android.R.string.ok) { _, _ ->
                        requestPermissionLauncher.launch(
                            android.Manifest.permission.CAMERA
                        )
                    }
                    .setNegativeButton(android.R.string.cancel) { _, _ -> }
                    .show()
            }
            else -> {
                requestPermissionLauncher.launch(
                    android.Manifest.permission.CAMERA
                )
            }
        }
    }

    private fun goToActivity(activity: Activity, extra: Bundle? = null) {
        val intent = Intent(this, activity::class.java)
        if (extra != null) intent.putExtras(extra)
        startActivity(intent)
    }
}