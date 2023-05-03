package com.navi.dogbreedapp.machinelearning

import androidx.camera.core.ImageProxy
import com.navi.dogbreedapp.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ClassifierRepository(private val classifier: Classifier) {

    suspend fun recognizeImage(imageProxy: ImageProxy): DogRecognition =
        withContext(Dispatchers.IO) {
            val bitmapPhoto = Utils.convertImageProxyToBitmap(imageProxy)
            if (bitmapPhoto == null) {
                DogRecognition("", 0f)
            } else {
                classifier.recognizeImage(bitmapPhoto).first()
            }
        }
}