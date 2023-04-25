package com.navi.dogbreedapp.api.responses

import com.navi.dogbreedapp.DogModel

sealed class ApiResponseStatus {
    object Loading : ApiResponseStatus()
    class Success(val dogList: List<DogModel>): ApiResponseStatus()
    class Error(val messageId: Int): ApiResponseStatus()
}