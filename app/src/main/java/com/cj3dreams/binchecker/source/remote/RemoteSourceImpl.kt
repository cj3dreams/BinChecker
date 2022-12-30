package com.cj3dreams.binchecker.source.remote

import com.cj3dreams.binchecker.model.response.BinResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteSourceImpl(private val binApiRequest: BinApiRequest): RemoteSource {
    override suspend fun checkBin(bin: Long): RequestResult<BinResponseModel> =
        withContext(Dispatchers.IO) {
            try {
                val response = binApiRequest.checkBin(bin)
                if (response.isSuccessful) return@withContext RequestResult.Success(response.body()!!)
                else return@withContext RequestResult.Error(Exception(response.message()))
            }catch (e: Exception) {
                return@withContext RequestResult.Error(e)
            }
        }
}