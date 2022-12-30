package com.cj3dreams.binchecker.usecase

import com.cj3dreams.binchecker.model.entity.BinHistoryEntity
import com.cj3dreams.binchecker.repo.DataRepository

class SetBinToLocalHistoryUseCase (private val dataRepository: DataRepository) {
    suspend operator fun invoke(binHistoryEntity: BinHistoryEntity) =
        dataRepository.setBinToLocalHistory(binHistoryEntity)
}