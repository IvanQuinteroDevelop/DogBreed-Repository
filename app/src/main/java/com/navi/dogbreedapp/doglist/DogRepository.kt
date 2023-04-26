package com.navi.dogbreedapp.doglist

import com.navi.dogbreedapp.DogModel
import com.navi.dogbreedapp.api.DogsApi.retrofitService
import com.navi.dogbreedapp.api.dto.DogDTOMapper
import com.navi.dogbreedapp.api.makeNetworkCall
import com.navi.dogbreedapp.api.responses.ApiResponseStatus

class DogRepository {
    suspend fun downloadDogs(): ApiResponseStatus<List<DogModel>> {
        return makeNetworkCall {
            val dogListApiResponse = retrofitService.getAllDogs()
            val dogDTOList = dogListApiResponse.data.dogs
            val dogDTOMapper = DogDTOMapper()
            dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
        }
    }
}