package com.navi.dogbreedapp.main

import androidx.camera.core.ImageProxy
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.navi.dogbreedapp.api.responses.ApiResponseStatus
import com.navi.dogbreedapp.interfaces.ClassifierTasks
import com.navi.dogbreedapp.interfaces.DogTasks
import com.navi.dogbreedapp.machinelearning.DogRecognition
import com.navi.dogbreedapp.model.DogModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val dogTasks: DogTasks, private val classifierTasks: ClassifierTasks): ViewModel() {

    private val _dog = MutableLiveData<DogModel>()
    val dog: LiveData<DogModel> get() = _dog

    private val _permissionGranted = MutableLiveData(false)
    val permissionGranted: LiveData<Boolean> get() = _permissionGranted

    var status = mutableStateOf<ApiResponseStatus<DogModel>?>(null)
    private set

    private val _dogRecognition = MutableLiveData<DogRecognition>()
    val dogRecognition: LiveData<DogRecognition> get() = _dogRecognition

    fun recognizeImage(imageProxy: ImageProxy) {
        viewModelScope.launch {
            _dogRecognition.value = classifierTasks.recognizeImage(imageProxy)
            imageProxy.close()
        }
    }

    fun acceptPermission() {
        _permissionGranted.value = true
    }

    fun getDogByMLId(mlDogId: String) {
        viewModelScope.launch {
            handleResponseStatus(dogTasks.getDogByMLId(mlDogId))
        }
    }

    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<DogModel>) {
        if (apiResponseStatus is ApiResponseStatus.Success) {
            _dog.value = apiResponseStatus.data
        }

        status.value = apiResponseStatus
    }
}