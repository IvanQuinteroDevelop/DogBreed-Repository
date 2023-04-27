package com.navi.dogbreedapp.api

import com.navi.dogbreedapp.BASE_URL
import com.navi.dogbreedapp.GET_ALL_DOGS_END_POINT
import com.navi.dogbreedapp.api.responses.DogApiResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

interface ApiService {
    @GET(GET_ALL_DOGS_END_POINT)
    suspend fun getAllDogs(): DogApiResponse
}

object DogsApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}