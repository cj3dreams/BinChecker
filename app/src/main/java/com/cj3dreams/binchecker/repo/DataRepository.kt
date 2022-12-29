package com.cj3dreams.binchecker.repo

import com.cj3dreams.binchecker.model.response.BinResponseModel
import com.cj3dreams.binchecker.source.remote.RequestResult

interface DataRepository {
    suspend fun checkBin(bin: Int): RequestResult<BinResponseModel>
    suspend fun getAllHistoryFromLocal()
}