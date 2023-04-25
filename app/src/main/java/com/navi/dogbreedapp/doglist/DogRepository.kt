package com.navi.dogbreedapp.doglist

import com.navi.dogbreedapp.R
import com.navi.dogbreedapp.api.DogsApi.retrofitService
import com.navi.dogbreedapp.api.dto.DogDTOMapper
import com.navi.dogbreedapp.api.responses.ApiResponseStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

class DogRepository {
    suspend fun downloadDogs(): ApiResponseStatus {
        return withContext(Dispatchers.IO) {
            try {
                val dogListApiResponse = retrofitService.getAllDogs()
                val dogDTOList = dogListApiResponse.data.dogs
                val dogDTOMapper = DogDTOMapper()
                ApiResponseStatus.Success(dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList))
            } catch (e: Exception) {
                ApiResponseStatus.Error(R.string.unknown_error)
            } catch (e: UnknownHostException) {
                ApiResponseStatus.Error(R.string.error_connection)
            }
        }
    }
}