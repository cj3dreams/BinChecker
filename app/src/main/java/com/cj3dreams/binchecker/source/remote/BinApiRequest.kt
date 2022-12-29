package com.cj3dreams.binchecker.source.remote

import com.cj3dreams.binchecker.model.response.BinResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface BinApiRequest {

    @Headers("Accept-Version: 3")
    @GET("{bin}")
    suspend fun checkBin(@Path("bin") bin: Int): Response<BinResponseModel>
}