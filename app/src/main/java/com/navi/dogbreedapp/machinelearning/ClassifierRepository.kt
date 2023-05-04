package com.navi.dogbreedapp.machinelearning

import androidx.camera.core.ImageProxy
import com.navi.dogbreedapp.interfaces.ClassifierTasks
import com.navi.dogbreedapp.utils.Utils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ClassifierRepository @Inject constructor(private val classifier: Classifier, private val dispatcher: CoroutineDispatcher): ClassifierTasks {

    override suspend fun recognizeImage(imageProxy: ImageProxy): DogRecognition =
        withContext(dispatcher) {
            val bitmapPhoto = Utils.convertImageProxyToBitmap(imageProxy)
            if (bitmapPhoto == null) {
                DogRecognition("", 0f)
            } else {
                classifier.recognizeImage(bitmapPhoto).first()
            }
        }
}