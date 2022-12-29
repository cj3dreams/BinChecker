package com.cj3dreams.binchecker.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cj3dreams.binchecker.model.response.BinResponseModel
import com.cj3dreams.binchecker.repo.DataRepositoryImpl
import com.cj3dreams.binchecker.source.remote.RequestResult
import com.cj3dreams.binchecker.usecase.CheckBinUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BinViewModel(private val dataRepository: DataRepositoryImpl): ViewModel() {

    private val checkBinUseCase get() = CheckBinUseCase(dataRepository)

    fun checkBin(bin: Int, onComplete: (bin: BinResponseModel) -> Unit) = viewModelScope.launch(Dispatchers.IO) {
         when (val dataResult = checkBinUseCase.invoke(bin)) {
            is RequestResult.Success ->
                onComplete(dataResult.data)
            is RequestResult.Error -> {

            }
        }
    }
}