package com.example.harajtask.di

import android.content.Context
import androidx.room.Room
import com.example.harajtask.utils.Constants
import com.example.harajtask.data.localStore.AdsDao
import com.example.harajtask.data.localStore.AdsDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataBaseModule {
    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext applicationCtx : Context): AdsDataBase {
        return Room.databaseBuilder(applicationCtx, AdsDataBase::class.java, Constants.ADS_DATA_BASE).build()
    }

    @Provides
    fun provideCountriesDao(adsDataBase: AdsDataBase): AdsDao {
        return adsDataBase.adsDao()
    }


}