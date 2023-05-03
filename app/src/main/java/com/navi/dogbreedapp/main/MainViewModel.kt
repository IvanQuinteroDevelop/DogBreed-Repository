package com.navi.dogbreedapp.main

import androidx.camera.core.ImageProxy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.navi.dogbreedapp.model.DogModel
import com.navi.dogbreedapp.api.responses.ApiResponseStatus
import com.navi.dogbreedapp.doglist.DogRepository
import com.navi.dogbreedapp.machinelearning.Classifier
import com.navi.dogbreedapp.machinelearning.ClassifierRepository
import com.navi.dogbreedapp.machinelearning.DogRecognition
import kotlinx.coroutines.launch
import java.nio.MappedByteBuffer

class MainViewModel: ViewModel() {

    private val _dog = MutableLiveData<DogModel>()
    val dog: LiveData<DogModel> get() = _dog

    private val _status = MutableLiveData<ApiResponseStatus<DogModel>>()
    val status: LiveData<ApiResponseStatus<DogModel>> get() = _status

    private val _dogRecognition = MutableLiveData<DogRecognition>()
    val dogRecognition: LiveData<DogRecognition> get() = _dogRecognition

    private val dogRepository = DogRepository()
    private lateinit var classifierRepository: ClassifierRepository

    fun setupClassifier(tfLiteModel: MappedByteBuffer, labels: List<String>) {
        val classifier = Classifier(tfLiteModel, labels)
        classifierRepository = ClassifierRepository(classifier)
    }

    fun recognizeImage(imageProxy: ImageProxy) {
        viewModelScope.launch {
            _dogRecognition.value = classifierRepository.recognizeImage(imageProxy)
            imageProxy.close()
        }
    }

    fun getDogByMLId(mlDogId: String) {
        viewModelScope.launch {
            handleResponseStatus(dogRepository.getDogByMLId(mlDogId))
        }
    }

    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<DogModel>) {
        if (apiResponseStatus is ApiResponseStatus.Success) {
            _dog.value = apiResponseStatus.data
        }

        _status.value = apiResponseStatus
    }
}