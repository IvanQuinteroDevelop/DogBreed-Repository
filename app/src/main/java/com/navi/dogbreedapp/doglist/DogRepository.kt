package com.navi.dogbreedapp.doglist

import com.navi.dogbreedapp.DogModel
import com.navi.dogbreedapp.api.DogsApi.retrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DogRepository {
    suspend fun downloadDogs(): List<DogModel> {
        return withContext(Dispatchers.IO) {
            val dogListApiResponse = retrofitService.getAllDogs()
            dogListApiResponse.data.dogs
        }
    }
}