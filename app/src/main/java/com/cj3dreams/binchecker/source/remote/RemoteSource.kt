package com.cj3dreams.binchecker.source.remote

import com.cj3dreams.binchecker.model.response.BinResponseModel

interface RemoteSource {
    suspend fun checkBin(bin: Int): RequestResult<BinResponseModel>
}