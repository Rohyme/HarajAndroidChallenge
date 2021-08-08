package com.example.harajtask.data.localStore

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.harajtask.data.entities.AdInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface AdsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllAds(adsList: List<AdInfo>)

    @Query("select * from adTable")
    fun getAllAds(): Flow<List<AdInfo>>

    @Query("select * from adTable where id == :id")
    fun getAdWithId(id: Int): Flow<AdInfo>
}