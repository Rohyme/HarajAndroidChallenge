package com.example.harajtask.data.mainScreen

import com.example.harajtask.data.localStore.AdsDao
import com.example.harajtask.di.AssetReader
import com.example.harajtask.utils.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DataListRepository @Inject constructor(
    private val assetReader: AssetReader,
    private val adsDao: AdsDao
) {
    suspend fun getDataList(): Flow<DataResult> = flow {
        emit(DataResult.Loading)
        try {
            adsDao.getAllAds().collect { savedList ->
                when {
                    savedList.isNullOrEmpty() -> {
                        val ads = assetReader.readJson("data.json")
                        when {
                            ads.isNullOrEmpty() -> emit(DataResult.Empty)
                            else -> {
                                adsDao.saveAllAds(ads)
                                adsDao.getAllAds().collect {
                                    emit(DataResult.Success(it))
                                }
                            }
                        }
                    }
                    else -> emit(DataResult.Success(savedList))
                }
            }
        } catch (e: Throwable) {
            emit(DataResult.Error(e))
        }
    }.flowOn(Dispatchers.IO)
}