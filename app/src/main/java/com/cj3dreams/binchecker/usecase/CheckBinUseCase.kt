package com.cj3dreams.binchecker.usecase

import com.cj3dreams.binchecker.repo.DataRepository

class CheckBinUseCase (private val dataRepository: DataRepository) {
    suspend operator fun invoke(bin: Int) = dataRepository.checkBin(bin)
}