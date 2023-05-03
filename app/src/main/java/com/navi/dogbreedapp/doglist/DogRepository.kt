package com.navi.dogbreedapp.doglist

import com.navi.dogbreedapp.model.DogModel
import com.navi.dogbreedapp.api.DogsApi.retrofitService
import com.navi.dogbreedapp.api.dto.DogDTOMapper
import com.navi.dogbreedapp.api.makeNetworkCall
import com.navi.dogbreedapp.api.responses.ApiResponseStatus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DogRepository @Inject constructor() {
    suspend fun downloadDogs(): ApiResponseStatus<List<DogModel>> {
        return makeNetworkCall {
            val dogListApiResponse = retrofitService.getAllDogs()
            val dogDTOList = dogListApiResponse.data.dogs
            val dogDTOMapper = DogDTOMapper()
            dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
        }
    }

    suspend fun getDogByMLId(mlDogId: String): ApiResponseStatus<DogModel> {
        return makeNetworkCall {
            val response = retrofitService.getDogByMLId(mlDogId)

            if (!response.isSuccess) {
                throw Exception(response.message)
            }

            val dogDtoMapper = DogDTOMapper()
            dogDtoMapper.fromDogDTOToDogDomain(response.data.dog)
        }
    }
}