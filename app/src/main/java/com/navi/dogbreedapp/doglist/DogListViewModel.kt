package com.navi.dogbreedapp.doglist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.navi.dogbreedapp.model.DogModel
import com.navi.dogbreedapp.api.responses.ApiResponseStatus
import com.navi.dogbreedapp.interfaces.DogTasks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogListViewModel @Inject constructor(private val dogTasks: DogTasks): ViewModel() {

    private val _dogList = MutableLiveData<List<DogModel>>()
    val dogList: LiveData<List<DogModel>> get() = _dogList

    var status = mutableStateOf<ApiResponseStatus<Any>?>(null)
    private set

    init {
        downloadDogs()
    }

    private fun downloadDogs() {
        viewModelScope.launch {
            status.value = ApiResponseStatus.Loading()
            handleResponseStatus(dogTasks.downloadDogs() as ApiResponseStatus<Any>)
        }
    }

    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<Any>) {
        if (apiResponseStatus is ApiResponseStatus.Success) {
            _dogList.value = apiResponseStatus.data as List<DogModel>
        }
        status.value = apiResponseStatus
    }
}