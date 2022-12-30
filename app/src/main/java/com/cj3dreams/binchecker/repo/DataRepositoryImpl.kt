package com.cj3dreams.binchecker.repo

import androidx.lifecycle.LiveData
import com.cj3dreams.binchecker.model.entity.BinHistoryEntity
import com.cj3dreams.binchecker.model.response.BinResponseModel
import com.cj3dreams.binchecker.source.local.LocalSource
import com.cj3dreams.binchecker.source.remote.RemoteSource
import com.cj3dreams.binchecker.source.remote.RequestResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataRepositoryImpl(
    private val remoteSource: RemoteSource,
    private val localSource: LocalSource): DataRepository {

    override suspend fun checkBin(bin: Long): RequestResult<BinResponseModel> =
        remoteSource.checkBin(bin)

    override suspend fun getAllBinHistoryFromLocal(): LiveData<List<BinHistoryEntity>> =
        withContext(Dispatchers.IO) { localSource.getAllBinHistoryFromLocal() }

    override suspend fun setBinToLocalHistory(binHistoryEntity: BinHistoryEntity) =
        withContext(Dispatchers.IO) { localSource.setBinToLocalHistory(binHistoryEntity) }

    override suspend fun cleanHistoryFromLocal() =
        withContext(Dispatchers.IO) { localSource.cleanHistoryFromLocal() }
}