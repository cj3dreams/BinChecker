package com.cj3dreams.binchecker.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cj3dreams.binchecker.model.response.BinResponseModel
import com.cj3dreams.binchecker.repo.DataRepositoryImpl
import com.cj3dreams.binchecker.source.remote.RequestResult
import com.cj3dreams.binchecker.usecase.CheckBinUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BinViewModel(private val dataRepository: DataRepositoryImpl): ViewModel() {
    val checkBinResponse = MutableLiveData<BinResponseModel?>()

    private val checkBinUseCase get() = CheckBinUseCase(dataRepository)

    fun checkBin(bin: Long) =
        viewModelScope.launch(Dispatchers.IO) {
            when (val dataResult = checkBinUseCase.invoke(bin)) {
                is RequestResult.Success ->
                    checkBinResponse.postValue(dataResult.data)
                is RequestResult.Error ->
                    checkBinResponse.postValue(null)
            }
        }
}