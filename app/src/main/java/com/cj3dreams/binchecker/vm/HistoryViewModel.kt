package com.cj3dreams.binchecker.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cj3dreams.binchecker.model.entity.BinHistoryEntity
import com.cj3dreams.binchecker.repo.DataRepositoryImpl
import com.cj3dreams.binchecker.usecase.CleanHistoryFromLocalUseCase
import com.cj3dreams.binchecker.usecase.GetAllBinHistoryFromLocalUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(private val dataRepository: DataRepositoryImpl): ViewModel() {

    val binHistoryLiveData: MutableLiveData<List<BinHistoryEntity>> = MutableLiveData()

    private val getAllBinHistoryFromLocalUseCase get() = GetAllBinHistoryFromLocalUseCase(dataRepository)
    private val cleanHistoryFromLocalUseCase get() = CleanHistoryFromLocalUseCase(dataRepository)

    fun getAllBinHistoryFromLocal() =
        viewModelScope.launch(Dispatchers.IO) {
            getAllBinHistoryFromLocalUseCase.invoke().collect { values ->
                binHistoryLiveData.postValue(values)
            }
        }
    fun cleanHistoryFromLocal() = viewModelScope.launch(Dispatchers.IO) {
        cleanHistoryFromLocalUseCase.invoke()
        getAllBinHistoryFromLocal()
    }
}