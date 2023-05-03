package com.navi.dogbreedapp.doglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.navi.dogbreedapp.model.DogModel
import com.navi.dogbreedapp.api.responses.ApiResponseStatus
import kotlinx.coroutines.launch

class DogListViewModel: ViewModel() {

    private val _dogList = MutableLiveData<List<DogModel>>()
    val dogList: LiveData<List<DogModel>> get() = _dogList

    private val _status = MutableLiveData<ApiResponseStatus<List<DogModel>>>()
    val status: LiveData<ApiResponseStatus<List<DogModel>>> get() = _status

    private val dogRepository = DogRepository()

    init {
        downloadDogs()
    }

    private fun downloadDogs() {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            handleResponseStatus(dogRepository.downloadDogs())
        }
    }

    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<List<DogModel>>) {
        if (apiResponseStatus is ApiResponseStatus.Success) {
            _dogList.value = apiResponseStatus.data
        }
        _status.value = apiResponseStatus
    }
}