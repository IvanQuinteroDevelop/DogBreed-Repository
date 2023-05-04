package com.navi.dogbreedapp.interfaces

import com.navi.dogbreedapp.api.responses.ApiResponseStatus
import com.navi.dogbreedapp.model.DogModel

interface DogTasks {
    suspend fun downloadDogs(): ApiResponseStatus<List<DogModel>>
    suspend fun getDogByMLId(mlDogId: String): ApiResponseStatus<DogModel>
}