package com.navi.dogbreedapp.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.navi.dogbreedapp.DogModel
import com.navi.dogbreedapp.api.responses.ApiResponseStatus
import com.navi.dogbreedapp.doglist.DogRepository
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _dog = MutableLiveData<DogModel>()
    val dog: LiveData<DogModel> get() = _dog

    private val _status = MutableLiveData<ApiResponseStatus<DogModel>>()
    val status: LiveData<ApiResponseStatus<DogModel>> get() = _status

    private val dogRepository = DogRepository()

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