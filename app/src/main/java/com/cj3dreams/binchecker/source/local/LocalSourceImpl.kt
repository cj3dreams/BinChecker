package com.cj3dreams.binchecker.source.local

import androidx.lifecycle.LiveData
import com.cj3dreams.binchecker.model.entity.BinHistoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class LocalSourceImpl(private val dao: BinHistoryDao): LocalSource {
    override suspend fun getAllBinHistoryFromLocal(): Flow<List<BinHistoryEntity>> =
        withContext(Dispatchers.IO) { dao.getAllBinHistory() }

    override suspend fun setBinToLocalHistory(binHistoryEntity: BinHistoryEntity) =
        withContext(Dispatchers.IO) { dao.setBinToHistory(binHistoryEntity) }

    override suspend fun cleanHistoryFromLocal() = withContext(Dispatchers.IO) { dao.cleanHistory() }
}