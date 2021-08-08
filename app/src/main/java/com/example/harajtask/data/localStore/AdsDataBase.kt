package com.example.harajtask.data.localStore

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.harajtask.data.entities.AdInfo

@Database(entities = [AdInfo::class], version = 2)
abstract class AdsDataBase: RoomDatabase() {
    abstract fun adsDao(): AdsDao
}