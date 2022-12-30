package com.cj3dreams.binchecker.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cj3dreams.binchecker.model.entity.BinHistoryEntity
import com.cj3dreams.binchecker.model.response.BinResponseModel
import com.cj3dreams.binchecker.repo.DataRepositoryImpl
import com.cj3dreams.binchecker.source.remote.RequestResult
import com.cj3dreams.binchecker.usecase.CheckBinUseCase
import com.cj3dreams.binchecker.usecase.SetBinToLocalHistoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BinViewModel(private val dataRepository: DataRepositoryImpl): ViewModel() {
    val checkBinResponse = MutableLiveData<BinResponseModel?>()

    private val checkBinUseCase get() = CheckBinUseCase(dataRepository)
    private val setBinToLocalHistoryUseCase get() = SetBinToLocalHistoryUseCase(dataRepository)

    fun checkBinAndSave(bin: Long) =
        viewModelScope.launch(Dispatchers.IO) {
            when (val dataResult = checkBinUseCase.invoke(bin)) {
                is RequestResult.Success -> {
                    val response = dataResult.data
                    checkBinResponse.postValue(response)

                    setBinToLocalHistoryUseCase.invoke(
                        BinHistoryEntity(0, bin, response.brand,
                            when (response.prepaid) { true -> 1 false -> 0 else -> -1 },
                            response.scheme, response.type, response.bank?.city, response.bank?.name,
                            response.bank?.phone, response.bank?.url, response.country?.currency,
                            response.country?.emoji, response.country?.latitude,
                            response.country?.longitude, response.country?.name, response.number?.length,
                            when (response.number?.luhn) { true -> 1 false -> 0 else -> -1 }
                            )
                    )
                }

                is RequestResult.Error ->
                    checkBinResponse.postValue(null)
            }
        }
}