package com.navi.dogbreedapp

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.navi.dogbreedapp.databinding.ActivityMainBinding
import com.navi.dogbreedapp.doglist.DogListActivity
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var imageCapture: ImageCapture
    private lateinit var cameraExecutor: ExecutorService
    private var isCameraReady = false

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
        manageClicks()
        requestCameraPermission()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::cameraExecutor.isInitialized) cameraExecutor.shutdown()
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
                // Bind use cases to camera
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            }, ContextCompat.getMainExecutor(this)
        )
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
            cameraButton.setOnClickListener {
                if (isCameraReady) onTakePhoto()
            }
        }
    }

    private fun onTakePhoto() {
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(getOutputPhotoFile()).build()
        imageCapture.takePicture(outputFileOptions, cameraExecutor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(error: ImageCaptureException) {
                    Toast.makeText(this@MainActivity, "Error taking photo: code ${error.message}", Toast.LENGTH_SHORT).show()
                }

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) { }
            })
    }

    private fun getOutputPhotoFile(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name) + ".jpg").apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
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

    private fun goToActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
    }
}