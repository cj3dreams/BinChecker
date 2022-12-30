package com.cj3dreams.binchecker.source.local

import androidx.lifecycle.LiveData
import com.cj3dreams.binchecker.model.entity.BinHistoryEntity

interface LocalSource {
    suspend fun getAllBinHistoryFromLocal(): LiveData<List<BinHistoryEntity>>
    suspend fun setBinToLocalHistory(binHistoryEntity: BinHistoryEntity)
    suspend fun cleanHistoryFromLocal()
}