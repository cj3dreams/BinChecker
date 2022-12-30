package com.cj3dreams.binchecker.repo

import com.cj3dreams.binchecker.model.response.BinResponseModel
import com.cj3dreams.binchecker.source.remote.RemoteSource
import com.cj3dreams.binchecker.source.remote.RequestResult

class DataRepositoryImpl(private val remoteSource: RemoteSource): DataRepository {
    override suspend fun checkBin(bin: Long): RequestResult<BinResponseModel> =
        remoteSource.checkBin(bin)

    override suspend fun getAllHistoryFromLocal() {

    }
}