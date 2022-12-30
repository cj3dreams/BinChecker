package com.cj3dreams.binchecker.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cj3dreams.binchecker.model.entity.BinHistoryEntity

@Database(entities = [BinHistoryEntity::class], version = 1, exportSchema = false)
abstract class AppDb: RoomDatabase() {

    abstract fun binHistoryDao(): BinHistoryDao?
}