package com.navi.dogbreedapp.api.responses

import com.squareup.moshi.Json

class DogApiResponse(
    @field:Json(name = "message")
    val message: String,
    @field:Json(name = "is_success")
    val isSuccess: Boolean,
    @field:Json(name = "data")
    val data: DogListResponse,
)