package com.navi.dogbreedapp.api.dto

import com.navi.dogbreedapp.DogModel

class DogDTOMapper {

    fun fromDogDTOToDogDomain(dogDTO: DogDTO): DogModel {

        return DogModel(
            dogDTO.id,
            dogDTO.index,
            dogDTO.name,
            dogDTO.type,
            dogDTO.heightFemale,
            dogDTO.heightMale,
            dogDTO.imageUrl,
            dogDTO.lifeExpectancy,
            dogDTO.temperament,
            dogDTO.weightFemale,
            dogDTO.weightMale
        )
    }

    fun fromDogDTOListToDogDomainList(dogDTOList: List<DogDTO>): List<DogModel> {
        return dogDTOList.map { fromDogDTOToDogDomain(it) }
    }
}