package com.cj3dreams.binchecker.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cj3dreams.binchecker.model.entity.BinHistoryEntity

@Dao
interface BinHistoryDao {

    @Query("SELECT * FROM binHistory ORDER by id DESC")
    fun getAllBinHistory(): LiveData<List<BinHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setBinToHistory(binHistoryEntity: BinHistoryEntity)

    @Query("DELETE FROM binHistory")
    suspend fun cleanHistory()
}