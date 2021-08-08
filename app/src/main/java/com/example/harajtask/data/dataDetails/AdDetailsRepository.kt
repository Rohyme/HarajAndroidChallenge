package com.example.harajtask.data.dataDetails

import com.example.harajtask.data.localStore.AdsDao
import com.example.harajtask.utils.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AdDetailsRepository @Inject constructor(private val adsDao: AdsDao) {

    fun loadAdDetails(adID: Int): Flow<DataResult> = flow {
        emit(DataResult.Loading)
        try {
            adsDao.getAdWithId(adID).collect {
                emit(DataResult.Success(it))
            }
        } catch (e: Throwable) {
            emit(DataResult.Error(e))
        }
    }.flowOn(Dispatchers.IO)
}