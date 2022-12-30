package com.cj3dreams.binchecker.source.local

import androidx.lifecycle.LiveData
import com.cj3dreams.binchecker.model.entity.BinHistoryEntity
import kotlinx.coroutines.flow.Flow

interface LocalSource {
    suspend fun getAllBinHistoryFromLocal(): Flow<List<BinHistoryEntity>>
    suspend fun setBinToLocalHistory(binHistoryEntity: BinHistoryEntity)
    suspend fun cleanHistoryFromLocal()
}