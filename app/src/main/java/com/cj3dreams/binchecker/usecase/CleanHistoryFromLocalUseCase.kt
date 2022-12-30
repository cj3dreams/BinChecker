package com.cj3dreams.binchecker.usecase

import com.cj3dreams.binchecker.repo.DataRepository

class CleanHistoryFromLocalUseCase (private val dataRepository: DataRepository) {
    suspend operator fun invoke() = dataRepository.cleanHistoryFromLocal()
}