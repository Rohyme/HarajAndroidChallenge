package com.example.harajtask.data.dataDetails

import com.example.harajtask.data.localStore.AdsDao
import com.example.harajtask.utils.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AdDetailsRepository @Inject constructor(private val adsDao: AdsDao) {

    fun loadAdDetails(adID: Int): Flow<DataResult> = flow {
        emit(DataResult.Loading)
        try {
            emit(DataResult.Success(adsDao.getAdWithId(adID)))
        } catch (e: Throwable) {
            emit(DataResult.Error(e))
        }
    }
}