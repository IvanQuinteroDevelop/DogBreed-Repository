package com.navi.dogbreedapp.api

import com.navi.dogbreedapp.R
import com.navi.dogbreedapp.api.responses.ApiResponseStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

suspend fun <T> makeNetworkCall(call: suspend () -> T): ApiResponseStatus<T> {

    return withContext(Dispatchers.IO) {
        try {
            ApiResponseStatus.Success(call())
        } catch (e: Exception) {
            ApiResponseStatus.Error(R.string.unknown_error)
        } catch (e: UnknownHostException) {
            ApiResponseStatus.Error(R.string.error_connection)
        }
    }
}