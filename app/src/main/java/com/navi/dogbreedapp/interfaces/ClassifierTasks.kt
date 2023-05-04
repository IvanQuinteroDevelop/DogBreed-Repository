package com.navi.dogbreedapp.interfaces

import androidx.camera.core.ImageProxy
import com.navi.dogbreedapp.machinelearning.DogRecognition

interface ClassifierTasks {
    suspend fun recognizeImage(imageProxy: ImageProxy): DogRecognition
}