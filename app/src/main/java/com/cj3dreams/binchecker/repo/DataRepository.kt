package com.cj3dreams.binchecker.repo

import androidx.lifecycle.LiveData
import com.cj3dreams.binchecker.model.entity.BinHistoryEntity
import com.cj3dreams.binchecker.model.response.BinResponseModel
import com.cj3dreams.binchecker.source.remote.RequestResult
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    suspend fun checkBin(bin: Long): RequestResult<BinResponseModel>

    suspend fun getAllBinHistoryFromLocal(): Flow<List<BinHistoryEntity>>
    suspend fun setBinToLocalHistory(binHistoryEntity: BinHistoryEntity)
    suspend fun cleanHistoryFromLocal()
}