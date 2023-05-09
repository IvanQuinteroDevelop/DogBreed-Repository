package com.navi.dogbreedapp.main

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.navi.dogbreedapp.R
import com.navi.dogbreedapp.api.responses.ApiResponseStatus
import com.navi.dogbreedapp.machinelearning.DogRecognition
import com.navi.dogbreedapp.model.DogModel
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Composable
fun CameraView(
    executor: Executor,
    mainViewModel: MainViewModel,
    onListButton: () -> Unit,
) {
    // 1
    val lensFacing = CameraSelector.LENS_FACING_BACK
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val status: ApiResponseStatus<DogModel>? = mainViewModel.status.value
    val dogRecognized: DogRecognition? by mainViewModel.dogRecognition.observeAsState(null)
    val permissionGranted by mainViewModel.permissionGranted.observeAsState(initial = false)


    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }
    val imageCapture: ImageCapture = remember { ImageCapture.Builder().build() }
    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    if (status is ApiResponseStatus.Loading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator()
        }
    } else {
        LaunchedEffect(lensFacing) {
            val cameraProvider = context.cameraProvider()
            cameraProvider.unbindAll()
            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
            imageAnalysis.setAnalyzer(executor) { imageProxy ->
                mainViewModel.recognizeImage(imageProxy)
            }
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageCapture,
                imageAnalysis
            )

            preview.setSurfaceProvider(previewView.surfaceProvider)
        }

        Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
            if (permissionGranted) AndroidView({ previewView }, modifier = Modifier.fillMaxSize())

            Row(
                Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(bottom = 32.dp), horizontalArrangement = Arrangement.SpaceAround
            ) {
                FloatingActionButton(onClick = { onListButton() }, content = {
                    Icon(imageVector = Icons.Default.List, contentDescription = "List Button")
                })
                FloatingActionButton(
                    onClick = {
                        if (dogRecognized != null) mainViewModel.getDogByMLId(
                            dogRecognized!!.id
                        )
                    },
                    modifier = Modifier.alpha(getAlphaButton(dogRecognized?.confidence ?: 0.2f)),
                    content = {
                        Icon(
                            painter = painterResource(R.drawable.ic_camera),
                            contentDescription = "Camera Button"
                        )
                    })
            }
        }
    }
}

private fun getAlphaButton(confidence: Float): Float = if (confidence > 60.0) 1f else 0.2f

private suspend fun Context.cameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener({
                continuation.resume(cameraProvider.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }