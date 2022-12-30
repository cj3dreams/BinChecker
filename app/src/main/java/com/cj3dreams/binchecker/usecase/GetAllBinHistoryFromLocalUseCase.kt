package com.cj3dreams.binchecker.usecase

import com.cj3dreams.binchecker.repo.DataRepository

class GetAllBinHistoryFromLocalUseCase(private val dataRepository: DataRepository) {
    suspend operator fun invoke() = dataRepository.getAllBinHistoryFromLocal()
}