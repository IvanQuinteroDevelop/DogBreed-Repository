package com.navi.dogbreedapp.doglist

import com.navi.dogbreedapp.api.ApiService
import com.navi.dogbreedapp.model.DogModel
import com.navi.dogbreedapp.api.dto.DogDTOMapper
import com.navi.dogbreedapp.api.makeNetworkCall
import com.navi.dogbreedapp.api.responses.ApiResponseStatus
import com.navi.dogbreedapp.interfaces.DogTasks
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DogRepository @Inject constructor(private val apiService: ApiService): DogTasks {
    override suspend fun downloadDogs(): ApiResponseStatus<List<DogModel>> {
        return makeNetworkCall {
            val dogListApiResponse = apiService.getAllDogs()
            val dogDTOList = dogListApiResponse.data.dogs
            val dogDTOMapper = DogDTOMapper()
            dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
        }
    }

    override suspend fun getDogByMLId(mlDogId: String): ApiResponseStatus<DogModel> {
        return makeNetworkCall {
            val response = apiService.getDogByMLId(mlDogId)

            if (!response.isSuccess) {
                throw Exception(response.message)
            }

            val dogDtoMapper = DogDTOMapper()
            dogDtoMapper.fromDogDTOToDogDomain(response.data.dog)
        }
    }
}