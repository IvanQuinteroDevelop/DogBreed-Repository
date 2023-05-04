package com.navi.dogbreedapp.api

import com.navi.dogbreedapp.api.responses.DogApiResponse
import com.navi.dogbreedapp.api.responses.DogListApiResponse
import com.navi.dogbreedapp.utils.GET_ALL_DOGS_END_POINT
import com.navi.dogbreedapp.utils.GET_DOG_BY_ML_ID
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(GET_ALL_DOGS_END_POINT)
    suspend fun getAllDogs(): DogListApiResponse

    @GET(GET_DOG_BY_ML_ID)
    suspend fun getDogByMLId(@Query("ml_id") mlId: String): DogApiResponse
}